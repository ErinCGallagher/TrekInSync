package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.InsuranceView;

public class InsuranceViewHolder extends RecyclerView.ViewHolder {
    public InsuranceView insuranceView;

    /**
     * creates a view holder for an insurance view with phone number and details
     * @param itemView
     */
    public InsuranceViewHolder(View itemView) {
        super(itemView);
        insuranceView = (InsuranceView) itemView.findViewById(R.id.insurance_cell);
    }

    public static int getLayoutId() {
        return R.layout.insurance_row_entry;
    }
}
