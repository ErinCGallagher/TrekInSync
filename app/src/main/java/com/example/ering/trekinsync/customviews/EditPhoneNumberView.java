package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.media.Image;
import android.support.annotation.Nullable;
import android.text.Editable;
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
import com.example.ering.trekinsync.utils.UserSingletonUtils;

public class EditPhoneNumberView extends LinearLayout {

    private Spinner relationshipField;
    private Spinner phoneNumberType;
    private EditText phoneNumber;
    private ImageView deleteIcon;

    public EditPhoneNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.edit_phone_number_cell, this);
        relationshipField = (Spinner) container.findViewById(R.id.relationship_spinner);
        phoneNumberType = (Spinner) container.findViewById(R.id.phone_type_spinner);
        phoneNumber = (EditText) container.findViewById(R.id.edit_phone_number);
        deleteIcon = (ImageView) container.findViewById(R.id.delete_icon);

        initSpinnerAdapters();

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

    public void setRelationshipAndListener(String value, AdapterView.OnItemSelectedListener listener) {
        setRelationship(value);
        relationshipField.setOnItemSelectedListener(listener);
    }

    public void setPhoneTypeAndListener(String value, AdapterView.OnItemSelectedListener listener) {
        setPhoneNumberType(value);
        phoneNumberType.setOnItemSelectedListener(listener);
    }

    public void setPhoneNumberAndListener(String value, final DataInputListener<String> listener) {
        phoneNumber.setText(value);
        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(phoneNumber.getText().toString());
            }
        });
    }

    public void setDeleteOnClickListener(OnClickListener listener) {
        deleteIcon.setOnClickListener(listener);
    }

    private void setRelationship(String value) {
        int pos = UserSingletonUtils.getInstance().getPhoneRelationKeyPosition(value);
        relationshipField.setSelection(pos, true);
    }

    private void setPhoneNumberType(String value) {
        int pos = UserSingletonUtils.getInstance().getPhoneTypeKeyPosition(value);
        phoneNumberType.setSelection(pos, true);
    }

    private void setPhoneNumber(String value) {
        phoneNumber.setText(value);
    }

    private void initSpinnerAdapters() {
        ArrayAdapter<CharSequence> relationAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.contact_relation_values, android.R.layout.simple_spinner_item);
        relationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationshipField.setAdapter(relationAdapter);

        ArrayAdapter<CharSequence> phoneTypeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.number_type_values, android.R.layout.simple_spinner_item);
        phoneTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        phoneNumberType.setAdapter(phoneTypeAdapter);
    }
}
