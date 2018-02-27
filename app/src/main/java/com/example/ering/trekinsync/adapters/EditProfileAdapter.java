package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.presenters.EditProfilePresenter;

import java.util.ArrayList;
import java.util.List;

public class EditProfileAdapter extends BaseAdapter {

    private final EditProfilePresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;
    private RecyclerViewClickListener clickListener;

    /**
     * Create an adapter to display travel contact cells
     * @param profilePresenter
     */
    public EditProfileAdapter(EditProfilePresenter profilePresenter) {
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the edit profile adapter.
     */
    public void buildRows() {
        listItems.add(new LabelDescriptionRowBinder("Citizenship", "Canadian", clickListener));
    }

    public void setClickListener(RecyclerViewClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
