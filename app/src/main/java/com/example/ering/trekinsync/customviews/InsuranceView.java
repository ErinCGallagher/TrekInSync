package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

import java.util.ArrayList;

public class InsuranceView extends LinearLayout {
    private TextView labelView;
    private TextView phoneNumber;
    private TextView checkBoxLabel;
    private CheckBox checkBox;

    //insurance detail fields details
    private LinearLayout container1;
    private LinearLayout container2;
    private LinearLayout container3;

    private TextView[] detailLabelsArray;
    private TextView detailsLabel1;
    private TextView detailsLabel2;
    private TextView detailsLabel3;

    private TextView[] detailNumbersArray;
    private TextView detailsNumber1;
    private TextView detailsNumber2;
    private TextView detailsNumber3;

    public InsuranceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.insurance_cell, this);
        labelView = (TextView) container.findViewById(R.id.label);
        phoneNumber = (TextView) container.findViewById(R.id.phone_number);
        checkBoxLabel = (TextView) container.findViewById(R.id.checkbox_label);
        checkBox = (CheckBox) container.findViewById(R.id.checkbox);

        container1 = (LinearLayout) container.findViewById(R.id.details_container1);
        container2 = (LinearLayout) container.findViewById(R.id.details_container2);
        container3 = (LinearLayout) container.findViewById(R.id.details_container3);

        setUpListOfLabels(container);
        setUpListOfNumbers(container);


        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InsuranceView, 0, 0);
            try {
                setLabel(array.getString(R.styleable.InsuranceView_viewInsuranceLabel));
                setPhoneNumber(array.getString(R.styleable.InsuranceView_viewInsuranceNumber));
                setCheckboxLabel(array.getString(R.styleable.InsuranceView_viewCheckboxLabel));
                setCheckboxState(array.getBoolean(R.styleable.InsuranceView_viewCheckboxState, false));

                ArrayList<String> labelsList = new ArrayList<>();
                labelsList.add(array.getString(R.styleable.InsuranceView_viewDetailsLabel1));
                labelsList.add(array.getString(R.styleable.InsuranceView_viewDetailsLabel2));
                labelsList.add(array.getString(R.styleable.InsuranceView_viewDetailsLabel3));
                setDetailsLabels(labelsList);

                ArrayList<String> numbersList = new ArrayList<>();
                numbersList.add(array.getString(R.styleable.InsuranceView_viewDetailsNumber1));
                numbersList.add(array.getString(R.styleable.InsuranceView_viewDetailsNumber2));
                numbersList.add(array.getString(R.styleable.InsuranceView_viewDetailsNumber3));
                setDetailNumbers(numbersList);
            } finally {
                array.recycle();
            }
        }
    }

    public void setLabel(String label) {
        labelView.setText(label);
    }

    public void setPhoneNumber(String phoneNumberText) {
        phoneNumber.setText(phoneNumberText);
    }

    public void setCheckboxLabel(String checkboxLabelText) {
        checkBoxLabel.setText(checkboxLabelText);
    }

    public void setCheckboxState(Boolean state) {
        checkBox.setChecked(state);
        checkBox.setEnabled(false);
    }

    /**
     * Set detail labels given the list of labels no greater than 3
     * @param labelsList
     */
    public void setDetailsLabels(ArrayList<String> labelsList) {
        for (int i = 0; i < detailLabelsArray.length-1; i++) {
            if (i > labelsList.size()) {
                detailLabelsArray[i].setText(null);
                //TODO: hide container
            } else {
                detailLabelsArray[i].setText(labelsList.get(i));
            }
        }
    }

    /**
     * Set detail numbers given list of numbers no greater than 3
     * @param numbersList
     */
    public void setDetailNumbers(ArrayList<String> numbersList) {
        for (int i = 0; i < detailNumbersArray.length-1; i++) {
            if (i > numbersList.size()) {
                detailNumbersArray[i].setText(null);
            } else {
                detailNumbersArray[i].setText(numbersList.get(i));
            }
        }
    }

    private void setUpListOfLabels(View container) {
        detailLabelsArray = new TextView[3];
        detailsLabel1 = (TextView) container.findViewById(R.id.details_label1);
        detailsLabel2 = (TextView) container.findViewById(R.id.details_label2);
        detailsLabel3 = (TextView) container.findViewById(R.id.details_label3);
        detailLabelsArray[0] = detailsLabel1;
        detailLabelsArray[1] = detailsLabel2;
        detailLabelsArray[2] = detailsLabel3;
    }

    private void setUpListOfNumbers(View container) {
        detailNumbersArray = new TextView[3];
        detailsNumber1 = (TextView) container.findViewById(R.id.details_number1);
        detailsNumber2 = (TextView) container.findViewById(R.id.details_number2);
        detailsNumber3 = (TextView) container.findViewById(R.id.details_number3);
        detailNumbersArray[0] = detailsNumber1;
        detailNumbersArray[1] = detailsNumber2;
        detailNumbersArray[2] = detailsNumber3;
    }
}
