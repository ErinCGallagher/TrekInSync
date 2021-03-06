package com.trekinsync.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.customviews.ContactView;
import com.trekinsync.ering.trekinsync.interfaces.RecyclerViewClickListener;

public class ContactCellViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public ContactView contactView;
    public RecyclerViewClickListener clickListener;

    public ContactCellViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        contactView = itemView.findViewById(R.id.contact_detail_cell);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
    }

    public static int getLayoutId() {
        return R.layout.contact_detail_row_entry;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
    }
}
