package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class EditPhoneNumberView extends LinearLayout {

    private TextView relationshipField;
    private TextView phoneNumberType;
    private EditText phoneNumber;

    public EditPhoneNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.edit_phone_number_cell, this);
        relationshipField = (TextView) container.findViewById(R.id.relationship);
        phoneNumberType = (TextView) container.findViewById(R.id.phone_type);
        phoneNumber = (EditText) container.findViewById(R.id.edit_phone_number);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.EditPhoneNumberView, 0, 0);
            try {
                setPhoneNumber(array.getString(R.styleable.EditPhoneNumberView_viewEditPhone));
                setPhoneNumberType(array.getString(R.styleable.EditPhoneNumberView_viewEditPhoneType));
                setRelationship(array.getString(R.styleable.EditPhoneNumberView_viewEditRelationship));
            } finally {
                array.recycle();
            }
        }
    }

    public void setRelationship(String value) {
        relationshipField.setText(value);
    }

    public void setPhoneNumberType(String value) {
        phoneNumberType.setText(value);
    }

    public void setPhoneNumber(String value) {
        phoneNumber.setText(value);
    }
}
