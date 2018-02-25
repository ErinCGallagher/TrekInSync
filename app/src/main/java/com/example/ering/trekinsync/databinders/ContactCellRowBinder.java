package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.ContactView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.viewholders.ContactCellViewHolder;

public class ContactCellRowBinder extends BaseDataBinder<ContactCellViewHolder> {

    private String contactName;
    private String contactDescription;
    private RecyclerViewClickListener clickListener;

    /**
     * creates a view holder for a Contact Cell View.
     */
    public ContactCellRowBinder(String name, String description, RecyclerViewClickListener clickListener) {
        this.contactName = name;
        this.contactDescription = description;
        this.clickListener = clickListener;
    }

    @Override
    public ContactCellViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(ContactCellViewHolder.getLayoutId(), parent);
        return new ContactCellViewHolder(view, clickListener);
    }

    @Override
    public void bindViewHolder(ContactCellViewHolder holder) {
        final ContactView contactView = holder.contactView;
        contactView.setContactName(contactName);
        contactView.setDescription(contactDescription);
    }
}
