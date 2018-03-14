package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.EditInsuranceView;
import com.example.ering.trekinsync.customviews.InsuranceView;

public class EditInsuranceViewHolder extends RecyclerView.ViewHolder {
    public EditInsuranceView editInsuranceView;

    /**
     * creates a view holder for an insurance view with phone number and details
     * @param itemView
     */
    public EditInsuranceViewHolder(View itemView) {
        super(itemView);
        editInsuranceView = itemView.findViewById(R.id.edit_insurance_cell);
    }

    public static int getLayoutId() {
        return R.layout.edit_insurance_row_entry;
    }
}