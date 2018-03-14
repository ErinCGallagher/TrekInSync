package com.example.ering.trekinsync.models;

public class EmergencyNumberDataListenerModel {

    private EmergencyContact contact;
    private int position;
    private boolean shouldDelete;

    public EmergencyNumberDataListenerModel(EmergencyContact contact, int position, boolean shouldDelete) {
        this.contact = contact;
        this.position = position;
        this.shouldDelete = shouldDelete;
    }

    public EmergencyContact getContact() {
        return contact;
    }

    public void setContact(EmergencyContact contact) {
        this.contact = contact;
    }

    public boolean isShouldDelete() {
        return shouldDelete;
    }

    public int getPosition() {
        return position;
    }
}
