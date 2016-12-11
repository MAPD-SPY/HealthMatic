package com.spy.healthmatic.Model;

import java.util.ArrayList;

/**
 * Team Name: Team SPY
 * Created by shelalainechan on 2016-12-11.
 */

public class Hospital {
    private String name;
    private ArrayList<Room> rooms;
    private ArrayList<Laboratory> laboratories;

    public Hospital() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<Laboratory> getLaboratories() {
        return laboratories;
    }

    public void setLaboratories(ArrayList<Laboratory> laboratories) {
        this.laboratories = laboratories;
    }
}
