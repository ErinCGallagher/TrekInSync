package com.trekinsync.ering.trekinsync.adapters;

import com.trekinsync.ering.trekinsync.databinders.BaseDataBinder;
import com.trekinsync.ering.trekinsync.databinders.ContactCellRowBinder;
import com.trekinsync.ering.trekinsync.databinders.SectionDividerTitleRowBinder;
import com.trekinsync.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.trekinsync.ering.trekinsync.models.User;
import com.trekinsync.ering.trekinsync.presenters.LandingPresenter;
import com.trekinsync.ering.trekinsync.utils.UserSingletonUtils;

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
        User previousContact = null;
        if (presenter.getTravelContacts() != null && !presenter.getTravelContacts().isEmpty()) {
            for (User contact: presenter.getTravelContacts()) {
                listItems.add(new ContactCellRowBinder(contact.getName(),
                        UserSingletonUtils.getInstance().getCountryName(contact.getCitizenship()),
                        presenter.getSortingPrefix(previousContact, contact),
                        previousContact == null,
                        clickListener));
                previousContact = contact;
            }
        } else {
            //TODO display empty contacts view
        }
    }

    public void setClickListener(RecyclerViewClickListener itemClickListener) {
        this.clickListener = itemClickListener;
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
