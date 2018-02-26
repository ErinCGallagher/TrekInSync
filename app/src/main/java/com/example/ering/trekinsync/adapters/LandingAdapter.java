package com.example.ering.trekinsync.adapters;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.databinders.ContactCellRowBinder;
import com.example.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.LandingPresenter;

import java.util.ArrayList;
import java.util.List;

public class LandingAdapter extends BaseAdapter {

    private final LandingPresenter presenter;
    private final ArrayList<BaseDataBinder> listItems;
    private RecyclerViewClickListener clickListener;

    /**
     * Create an adapter to display travel contact cells
     * @param profilePresenter
     */
    public LandingAdapter(LandingPresenter profilePresenter) {
        presenter = profilePresenter;
        listItems = new ArrayList<>();
    }

    /**
     * populates data in the profile adapter.
     */
    public void buildRows() {
        listItems.clear();
        //General Info
        listItems.add(new SectionDividerTitleRowBinder(presenter.getSectionTitle()));
        if (presenter.getTravelContacts() != null && !presenter.getTravelContacts().isEmpty()) {
            for (User contact: presenter.getTravelContacts()) {
                listItems.add(new ContactCellRowBinder(contact.getName(), contact.getCitizenship(), clickListener));
            }
        } else {
            //TODO display empty contacts view
        }
    }

    public void setClickListener(RecyclerViewClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public List<BaseDataBinder> getItems() {
        return listItems;
    }
}
