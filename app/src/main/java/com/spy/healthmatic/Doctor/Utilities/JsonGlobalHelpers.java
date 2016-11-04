package com.spy.healthmatic.Doctor.Utilities;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-11-03.
 */

public class JsonGlobalHelpers {

    public static String loadJSONFromAsset(Context mContext, String jsonFile) {
        String json;
        AssetManager assetManager = mContext.getAssets();
        InputStream input;
        try {
            input = assetManager.open(jsonFile);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }
}
