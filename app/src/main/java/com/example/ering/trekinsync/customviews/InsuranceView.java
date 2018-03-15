package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

import java.util.ArrayList;

public class InsuranceView extends LinearLayout {
    private TextView labelView;
    private TextView phoneNumber;

    //insurance detail fields details
    private LinearLayout[] detailContainerArray;
    private LinearLayout container1;
    private LinearLayout container2;

    private TextView[] detailLabelsArray;
    private TextView detailsLabel1;
    private TextView detailsLabel2;

    private TextView[] detailNumbersArray;
    private TextView detailsNumber1;
    private TextView detailsNumber2;

    public InsuranceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.insurance_cell, this);
        labelView = (TextView) container.findViewById(R.id.label);
        phoneNumber = (TextView) container.findViewById(R.id.phone_number);

        setUpListOfContainers(container);
        setUpListOfLabels(container);
        setUpListOfNumbers(container);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InsuranceView, 0, 0);
            try {
                setLabel(array.getString(R.styleable.InsuranceView_viewInsuranceLabel));
                setPhoneNumber(array.getString(R.styleable.InsuranceView_viewInsuranceNumber));

                ArrayList<String> labelsList = new ArrayList<>();
                labelsList.add(array.getString(R.styleable.InsuranceView_viewDetailsLabel1));
                labelsList.add(array.getString(R.styleable.InsuranceView_viewDetailsLabel2));
                setDetailsLabels(labelsList);

                ArrayList<String> numbersList = new ArrayList<>();
                numbersList.add(array.getString(R.styleable.InsuranceView_viewDetailsNumber1));
                numbersList.add(array.getString(R.styleable.InsuranceView_viewDetailsNumber2));
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

    /**
     * Set detail labels given the list of labels no greater than 3
     * @param labelsList
     */
    public void setDetailsLabels(ArrayList<String> labelsList) {
        for (int i = 0; i < detailLabelsArray.length-1; i++) {
            if (i >= labelsList.size()) {
                detailLabelsArray[i].setText(null);
                detailContainerArray[i].setVisibility(GONE);
            } else {
                detailLabelsArray[i].setText(labelsList.get(i));
                detailContainerArray[i].setVisibility(VISIBLE);
            }
        }
    }

    /**
     * Set detail numbers given list of numbers no greater than 3
     * @param numbersList
     */
    public void setDetailNumbers(ArrayList<String> numbersList) {
        for (int i = 0; i < detailNumbersArray.length-1; i++) {
            if (i >= numbersList.size()) {
                detailNumbersArray[i].setText(null);
                detailContainerArray[i].setVisibility(GONE);
            } else {
                if (numbersList.get(i).isEmpty()) {
                    detailContainerArray[i].setVisibility(GONE);
                    detailContainerArray[i].setVisibility(GONE);
                } else {
                    detailNumbersArray[i].setText(numbersList.get(i));
                    detailContainerArray[i].setVisibility(VISIBLE);
                }
            }
        }
    }

    private void setUpListOfLabels(View container) {
        detailLabelsArray = new TextView[3];
        detailsLabel1 = container.findViewById(R.id.details_label1);
        detailsLabel2 = container.findViewById(R.id.details_label2);
        detailLabelsArray[0] = detailsLabel1;
        detailLabelsArray[1] = detailsLabel2;
    }

    private void setUpListOfNumbers(View container) {
        detailNumbersArray = new TextView[3];
        detailsNumber1 = container.findViewById(R.id.details_number1);
        detailsNumber2 = container.findViewById(R.id.details_number2);
        detailNumbersArray[0] = detailsNumber1;
        detailNumbersArray[1] = detailsNumber2;
    }

    private void setUpListOfContainers(View container) {
        detailContainerArray = new LinearLayout[3];
        container1 = container.findViewById(R.id.details_container1);
        container2 = container.findViewById(R.id.details_container2);
        detailContainerArray[0] = container1;
        detailContainerArray[1] = container2;
    }
}
