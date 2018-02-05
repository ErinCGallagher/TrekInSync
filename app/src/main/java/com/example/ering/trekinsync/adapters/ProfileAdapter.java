package com.example.ering.trekinsync.adapters;

import android.content.Context;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.PhoneNumberRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.example.ering.trekinsync.presenters.ProfilePresenter;

import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {

    private final Context ctx;
    private final ProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;

    /**
     * Create adapter for displaying profile details
     * @param context
     * @param profilePresenter
     */
    public ProfileAdapter(Context context, ProfilePresenter profilePresenter) {
        ctx = context;
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        //General Info
        listItems.add(new SectionDividerTitleRowBinder("GENERAL INFO"));
        //TODO: create profile picture upload cell
        listItems.add(new LabelDescriptionRowBinder("Full Name", "Erin Gallagher"));
        listItems.add(new LabelDescriptionRowBinder("Birth Date", "March 24, 1994"));
        listItems.add(new LabelDescriptionRowBinder("Age", "23"));
        listItems.add(new LabelDescriptionRowBinder("Citizenship", "Canadian"));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder("HEALTH INFO"));
        //TODO: create allergy cell with severity indicator
        listItems.add(new LabelDescriptionRowBinder("Allergies", "Scented Creme, Heat, Maple Trees"));
        listItems.add(new LabelDescriptionRowBinder("Medication", "None"));

        //Emergency Contact Info
        listItems.add(new SectionDividerTitleRowBinder("EMERGENCY CONTACT INFO"));
        listItems.add(new PhoneNumberRowBinder("Mother", "416-747-3625", "Cell"));
        listItems.add(new PhoneNumberRowBinder("Father", "416-888-4836", "Work"));

        //Insurance Info
        listItems.add(new SectionDividerTitleRowBinder("INSURANCE INFO"));
        //TODO: create insurance cell with policy #, group # or cert #, call first indicator
        listItems.add(new LabelDescriptionRowBinder("Manulife", "313-293-2948"));
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
