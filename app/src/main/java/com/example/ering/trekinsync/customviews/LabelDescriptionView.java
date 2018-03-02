package com.example.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

/**
 * Simple field with a label field above a description field
 */
public class LabelDescriptionView extends LinearLayout {
    private TextView labelView;
    private TextView descriptionView;
    private LinearLayout dropDownIconContainer;
    private LinearLayout viewContainer;
    private ImageView icon;

    public LabelDescriptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.label_description_cell, this);
        labelView = (TextView) container.findViewById(R.id.label);
        descriptionView = (TextView) container.findViewById(R.id.description);
        dropDownIconContainer = (LinearLayout) container.findViewById(R.id.drop_down_icon_container);
        viewContainer = (LinearLayout) container.findViewById(R.id.view_container);
        icon = (ImageView) container.findViewById(R.id.icon);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LabelDescriptionView, 0, 0);
            try {
                setLabel(array.getString(R.styleable.LabelDescriptionView_viewLabel));
                setDescription(array.getString(R.styleable.LabelDescriptionView_viewDescription));
                setIconVisibility(array.getBoolean(R.styleable.LabelDescriptionView_viewIconVisibility, false));
            } finally {
                array.recycle();
            }
        }
    }

    public void setLabel(final String label) {
        labelView.setText(label);
    }

    public void setDescription(final String description) {
        descriptionView.setText(description);
    }

    public void setIcon(@DrawableRes int resId) {
        icon.setImageResource(resId);
    }

    public void setIconVisibility(final boolean iconVisibility) {
        dropDownIconContainer.setVisibility(iconVisibility? VISIBLE : GONE);
    }

    public void setOnClickListener(OnClickListener clickListener) {
        viewContainer.setOnClickListener(clickListener);
    }
}
