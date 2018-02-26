package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.presenters.EditProfilePresenter;

import java.util.ArrayList;
import java.util.List;

public class EditProfileAdapter extends BaseAdapter {

    private final EditProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;

    /**
     * Create an adapter to display travel contact cells
     * @param profilePresenter
     */
    public EditProfileAdapter(EditProfilePresenter profilePresenter) {
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
