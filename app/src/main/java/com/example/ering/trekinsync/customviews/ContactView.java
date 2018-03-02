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

public class ContactView extends LinearLayout {
    private TextView contactName;
    private TextView contactDescription;

    public ContactView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.contact_detail_cell, this);
        contactName =  (TextView) container.findViewById(R.id.contact_name);
        contactDescription = (TextView) container.findViewById(R.id.contact_description);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ContactDetailView, 0, 0);
            try {
                setContactName(array.getString(R.styleable.ContactDetailView_viewContactName));
                setDescription(array.getString(R.styleable.ContactDetailView_viewContactDescription));
            } finally {
                array.recycle();
            }
        }
    }

    public void setContactName(String name) {
        contactName.setText(name);
    }

    public void setDescription(String description) {
        contactDescription.setText(description);
    }
}
