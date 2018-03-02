package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.InsuranceView;
import com.example.ering.trekinsync.viewholders.InsuranceViewHolder;

import java.util.ArrayList;

public class InsuranceRowBinder extends BaseDataBinder<InsuranceViewHolder> {
    private String label;
    private String phoneNumber;
    private String checkBoxLabel;
    private boolean checkBoxState;
    private ArrayList<String> detailsLabels;
    private ArrayList<String> detailsNumbers;

    /**
     * creates a view holder for an Insurance cell view
     */
    public InsuranceRowBinder(String label, String phoneNumber, String checkBoxLabel, boolean checkBoxState, ArrayList<String> detailsLabels, ArrayList<String> detailsNumbers) {
        this.label = label;
        this.phoneNumber = phoneNumber;
        this.checkBoxLabel = checkBoxLabel;
        this.checkBoxState = checkBoxState;
        this.detailsLabels = detailsLabels;
        this.detailsNumbers = detailsNumbers;
    }

    @Override
    public InsuranceViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(InsuranceViewHolder.getLayoutId(), parent);
        return new InsuranceViewHolder(view);
    }

    @Override
    public void bindViewHolder(InsuranceViewHolder holder) {
        final InsuranceView insuranceView = holder.insuranceView;
        insuranceView.setLabel(label);
        insuranceView.setPhoneNumber(phoneNumber);
        insuranceView.setCheckboxLabel(checkBoxLabel);
        insuranceView.setCheckboxState(checkBoxState);
        insuranceView.setDetailsLabels(detailsLabels);
        insuranceView.setDetailNumbers(detailsNumbers);
    }
}
