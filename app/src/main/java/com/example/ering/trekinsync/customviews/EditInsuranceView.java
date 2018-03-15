package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.utils.InputFilterUtils;
import com.example.ering.trekinsync.utils.UserSingletonUtils;

public class EditInsuranceView extends LinearLayout {

    private EditText labelView;
    private EditText phoneNumber;
    private ImageView deleteIcon;

    private Spinner detailsLabel1;
    private Spinner detailsLabel2;
    private EditText detail1;
    private EditText detail2;

    public EditInsuranceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.edit_insurance_cell, this);
        labelView = container.findViewById(R.id.label);
        phoneNumber = container.findViewById(R.id.edit_phone_number);
        deleteIcon = container.findViewById(R.id.delete_icon);

        detailsLabel1 = container.findViewById(R.id.details_label1);
        detailsLabel2 = container.findViewById(R.id.details_label2);
        detail1 = container.findViewById(R.id.details_number1);
        detail2 = container.findViewById(R.id.details_number2);
        initSpinnerAdapters();

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditInsuranceView, 0, 0);
            try {
                setLabel(array.getString(R.styleable.EditInsuranceView_viewEditInsuranceLabel));
                setPhoneNumber(array.getString(R.styleable.EditInsuranceView_viewEditInsuranceNumber));

                setDetailsLabel1(array.getString(R.styleable.EditInsuranceView_viewEditDetailsLabel1));
                setDetailsLabel2(array.getString(R.styleable.EditInsuranceView_viewEditDetailsLabel2));

                setDetails1(array.getString(R.styleable.EditInsuranceView_viewEditDetailsNumber1));
                setDetails2(array.getString(R.styleable.EditInsuranceView_viewEditDetailsNumber2));
            } finally {
                array.recycle();
            }
        }
    }

    public void setLabel(String label, final DataInputListener<String> listener) {
        setLabel(label);
        labelView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(labelView.getText().toString().trim());
            }
        });
    }

    public void setDetailsLabel1(String value, AdapterView.OnItemSelectedListener listener) {
        setDetailsLabel1(value);
        detailsLabel1.setOnItemSelectedListener(listener);
    }

    public void setDetailsLabel2(String value, AdapterView.OnItemSelectedListener listener) {
        setDetailsLabel2(value);
        detailsLabel2.setOnItemSelectedListener(listener);
    }

    public void setPhoneNumber(String phoneNumberText, final DataInputListener<String> listener) {
        setPhoneNumber(phoneNumberText);
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(phoneNumber.getText().toString().trim());
            }
        });
    }

    public void setDeleteOnClickListener(OnClickListener listener) {
        deleteIcon.setOnClickListener(listener);
    }

    public void setDetail1Listener(String value, final DataInputListener<String> listener) {
        detail1.setText(value);
        detail1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(detail1.getText().toString().trim());
            }
        });
    }

    public void setDetail2Listener(String value, final DataInputListener<String> listener) {
        detail2.setText(value);
        detail2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(detail2.getText().toString().trim());
            }
        });
    }

    /* Private Functions */

    private void setLabel(String label) {
        labelView.setText(label);
        labelView.setFilters(new InputFilter[] {InputFilterUtils.getAlphaFilter()});
    }

    private void setPhoneNumber(String phoneNumberText) {
        phoneNumber.setText(phoneNumberText);
    }

    private void setDetailsLabel1(String value) {
        int pos = UserSingletonUtils.getInstance().getInsurancePolicyKeyPosition(value);
        detailsLabel1.setSelection(pos, true);
    }

    private void setDetailsLabel2(String value) {
        int pos = UserSingletonUtils.getInstance().getInsurancePolicyKeyPosition(value);
        detailsLabel2.setSelection(pos, true);
    }

    private void setDetails1(String value) {
        detail1.setText(value);
        detail1.setFilters(new InputFilter[] {InputFilterUtils.getAlphaNumericFilter()});
    }

    private void setDetails2(String value) {
        detail2.setText(value);
        detail2.setFilters(new InputFilter[] {InputFilterUtils.getAlphaNumericFilter()});
    }

    private void initSpinnerAdapters() {
        ArrayAdapter<CharSequence> insuranceDetailAdapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.insurance_values, android.R.layout.simple_spinner_item);
        insuranceDetailAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsLabel1.setAdapter(insuranceDetailAdapter1);

        ArrayAdapter<CharSequence> insuranceDetailAdapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.insurance_values, android.R.layout.simple_spinner_item);
        insuranceDetailAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        detailsLabel2.setAdapter(insuranceDetailAdapter2);
    }
}
