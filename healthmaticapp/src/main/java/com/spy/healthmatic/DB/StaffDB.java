package com.spy.healthmatic.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.spy.healthmatic.Global.GlobalConst;
import com.spy.healthmatic.Global.GlobalFunctions;
import com.spy.healthmatic.Model.Staff;

public class StaffDB extends SQLiteOpenHelper implements GlobalConst {

    public static final String primaryKey = "primary_key";
    public static final String id = "id";
    public static final String staff = "staff";

    public StaffDB(Context context) {
        super(context, GlobalConst.DATABASE_NAME, null, GlobalConst.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_STAFF = "CREATE TABLE IF NOT EXISTS " + TABLE_STAFF + "("
                + primaryKey + " INTEGER PRIMARY KEY,"
                + id + " TEXT,"
                + staff + " TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_STAFF);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_STAFF);
        onCreate(sqLiteDatabase);
    }

    public boolean addStaff(Staff staffObject) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put(id, staffObject.get_id());
            values.put(staff, GlobalFunctions.getStaffJson(staffObject));

            db.insert(TABLE_STAFF, null, values);
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db!=null) {
                db.endTransaction();
                db.close();
            }
        } // Closing database connection
        return false;
    }

    public Staff getStaff() {
        Staff staff = null;
        String selectQuery = "SELECT  * FROM " + TABLE_STAFF + " ORDER BY "+primaryKey+" DESC LIMIT 1 ";
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    staff = GlobalFunctions.getStaff(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if (db!=null) {
                db.close();
            }
        }
        return staff;
    }

    public boolean updateStaff(Staff staffObject) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(staff, GlobalFunctions.getStaffJson(staffObject));
            db.update(TABLE_STAFF, values, id + " = ?", new String[]{String.valueOf(staffObject.get_id())});
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (db != null) {
                db.close();
            }
        } // Closing database connection
        return false;
    }

}
