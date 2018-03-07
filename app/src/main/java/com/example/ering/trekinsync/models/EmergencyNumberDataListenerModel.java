package com.example.ering.trekinsync.models;

public class EmergencyNumberDataListenerModel {

    private EmergencyContact contact;
    private int position;

    public EmergencyNumberDataListenerModel(EmergencyContact contact, int position) {
        this.contact = contact;
        this.position = position;
    }

    public EmergencyContact getContact() {
        return contact;
    }

    public void setContact(EmergencyContact contact) {
        this.contact = contact;
    }

    public int getPosition() {
        return position;
    }
}
