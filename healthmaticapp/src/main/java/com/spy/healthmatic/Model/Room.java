package com.spy.healthmatic.Model;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-12-11.
 */

public class Room {

    public final boolean AVAILABLE = false;
    public final boolean OCCUPIED = true;

    private long room;
    private boolean availability;

    public Room() {
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
