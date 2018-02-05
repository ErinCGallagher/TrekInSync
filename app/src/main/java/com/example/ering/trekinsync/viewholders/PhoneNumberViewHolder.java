package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.PhoneNumberView;

public class PhoneNumberViewHolder extends RecyclerView.ViewHolder {
    public PhoneNumberView phoneNumberView;

    /**
     * creates a view holder for phone number cell
     * @param itemView
     */
    public PhoneNumberViewHolder(View itemView) {
        super(itemView);
        phoneNumberView = (PhoneNumberView) itemView.findViewById(R.id.phone_number_cell);
    }

    public static int getLayoutId() {
        return R.layout.phone_number_row_entry;
    }
}
