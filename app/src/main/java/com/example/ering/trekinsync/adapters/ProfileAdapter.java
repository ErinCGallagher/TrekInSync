package com.example.ering.trekinsync.adapters;

import android.content.Context;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
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
        listItems.add(new LabelDescriptionRowBinder("Full Name", "Erin Gallagher"));
        listItems.add(new LabelDescriptionRowBinder("Birth Date", "March 24, 1994"));
        listItems.add(new LabelDescriptionRowBinder("Age", "23"));
        listItems.add(new LabelDescriptionRowBinder("Citizenship", "Canadian"));

        //Health info
        listItems.add(new LabelDescriptionRowBinder("Allergies", "Scented Creme, Heat"));

        //Emergency Contact Info
        listItems.add(new LabelDescriptionRowBinder("Mother", "416-383-3947"));
        listItems.add(new LabelDescriptionRowBinder("Father", "416-888-4836"));

        //Insurance Info
        listItems.add(new LabelDescriptionRowBinder("Manulife", "313-293-2948"));
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
