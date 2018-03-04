package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.EditPhoneNumberView;

public class EditPhoneNumberViewHolder extends RecyclerView.ViewHolder {
    public EditPhoneNumberView editPhoneNumberView;

    /**
     * creates a view holder for phone number cell
     * @param itemView
     */
    public EditPhoneNumberViewHolder(View itemView) {
        super(itemView);
        editPhoneNumberView = (EditPhoneNumberView) itemView.findViewById(R.id.edit_phone_number_cell);
    }

    public static int getLayoutId() {
        return R.layout.edit_phone_number_row_entry;
    }
}
