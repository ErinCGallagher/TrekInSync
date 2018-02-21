package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.ContactView;

public class ContactCellViewHolder extends RecyclerView.ViewHolder {
    public ContactView contactView;

    public ContactCellViewHolder(View itemView) {
        super(itemView);
        contactView = (ContactView) itemView.findViewById(R.id.contact_detail_cell);
    }

    public static int getLayoutId() {
        return R.layout.contact_detail_row_entry;
    }
}
