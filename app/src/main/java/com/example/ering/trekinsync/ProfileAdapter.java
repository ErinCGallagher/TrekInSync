package com.example.ering.trekinsync;

import android.content.Context;

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
        listItems.add(new LabelDescriptionRowBinder("name", "Erin Gallagher"));

    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
