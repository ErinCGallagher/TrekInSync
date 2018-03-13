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
    private TextView sortingLabel;
    private LinearLayout border;

    public ContactView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.contact_detail_cell, this);
        contactName = container.findViewById(R.id.contact_name);
        contactDescription = container.findViewById(R.id.contact_description);
        sortingLabel = container.findViewById(R.id.sorting_label);
        border = container.findViewById(R.id.border_line);
        border.setVisibility(GONE);

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

    public void setSortingLabel(String label, boolean isFirstCell) {
        if (label != null) {
            sortingLabel.setText(label);
            border.setVisibility(!isFirstCell ? VISIBLE : GONE);
        }
    }
}
