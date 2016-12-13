package com.spy.healthmatic.Model;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-12-11.
 */

public class Room {

    public final boolean AVAILABLE = false;
    public final boolean OCCUPIED = true;

    private String _id;
    private long room;
    private boolean availability;

    public Room() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public long getRoom() {
        return room;
    }

    public void setRoom(long room) {
        this.room = room;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
