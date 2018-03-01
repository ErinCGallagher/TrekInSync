package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.LabelDescriptionRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
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

    /**
     * populates data in the edit profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        //TODO: indicate updates happening on changes, no save necessary
        //General Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getGeneralSectionTitle()));
        listItems.add(new LabelDescriptionRowBinder("Citizenship",
                presenter.getUserCitizenship(),
                presenter.createCitizenshipDropDownRowListener()));

        //Health info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getHealthSectionTitle()));
        listItems.add(new LabelDescriptionRowBinder("Blood Type",
                presenter.getUserBloodType(),
                presenter.createBloodTypeDropDownListener()));
    }

    public void reloadData() {
        buildRows();
        notifyDataSetChanged();
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
