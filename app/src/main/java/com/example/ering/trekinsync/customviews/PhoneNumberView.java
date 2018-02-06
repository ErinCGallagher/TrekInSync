package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class PhoneNumberView extends LinearLayout {
    private TextView labelView;
    private TextView phoneNumber;
    private TextView phoneNumberType;

    public PhoneNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.phone_number_cell, this);
        labelView = (TextView) container.findViewById(R.id.label);
        phoneNumber = (TextView) container.findViewById(R.id.phone_number);
        phoneNumberType = (TextView) container.findViewById(R.id.phone_type);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PhoneNumberView, 0, 0);
            try {
                setLabel(array.getString(R.styleable.PhoneNumberView_viewPhoneLabel));
                setPhoneNumber(array.getString(R.styleable.PhoneNumberView_viewPhoneNumber));
                setPhoneNumberType(array.getString(R.styleable.PhoneNumberView_viewPhoneNumberType));
            } finally {
                array.recycle();
            }
        }
    }

    public void setLabel(@NonNull final String label) {
        labelView.setText(label);
    }

    //TODO: format phone number
    public void setPhoneNumber(@NonNull final String phoneNumberText) {
        phoneNumber.setText(phoneNumberText);
    }

    public void setPhoneNumberType(@Nullable String type) {
        if (type == null) {
            phoneNumberType.setVisibility(GONE);
        } else {
            phoneNumberType.setVisibility(VISIBLE);
            phoneNumberType.setText(type);
        }
    }
}
