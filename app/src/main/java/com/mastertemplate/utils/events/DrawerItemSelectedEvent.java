package com.mastertemplate.utils.events;

public class DrawerItemSelectedEvent {
    int position;

    public DrawerItemSelectedEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

}