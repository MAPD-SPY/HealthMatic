package com.spy.healthmatic.Model;

import java.io.Serializable;

/**
 * Created by yatin on 22/11/16.
 */

public class Tab implements Serializable{
    private String name;
    private int position;

    public Tab(String name, int position){
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
