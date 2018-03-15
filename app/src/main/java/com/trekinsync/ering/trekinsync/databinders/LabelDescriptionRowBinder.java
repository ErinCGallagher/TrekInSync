package com.trekinsync.ering.trekinsync.databinders;

import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;

import com.trekinsync.ering.trekinsync.customviews.LabelDescriptionView;
import com.trekinsync.ering.trekinsync.viewholders.LabelDescriptionViewHolder;

public class LabelDescriptionRowBinder extends BaseDataBinder<LabelDescriptionViewHolder> {
    private String label;
    private String description;
    private @DrawableRes int iconResId;
    private View.OnClickListener clickListener;
    private boolean isEditing;

    /**
     * creates a view holder for a static label and description form field with on click listener.
     */
    public LabelDescriptionRowBinder(String label, String description, @DrawableRes int iconResId, View.OnClickListener clickListener) {
        this.label = label;
        this.description = description;
        this.clickListener = clickListener;
        this.isEditing = true;
        this.iconResId = iconResId;
    }

    /**
     * creates a view holder for a static label and description form field.
     */
    public LabelDescriptionRowBinder(String label, String description) {
        this.label = label;
        this.description = description;
        this.clickListener = null;
        this.isEditing = false;
    }

    @Override
    public LabelDescriptionViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(LabelDescriptionViewHolder.getLayoutId(), parent);
        return new LabelDescriptionViewHolder(view);
    }

    @Override
    public void bindViewHolder(LabelDescriptionViewHolder holder) {
        final LabelDescriptionView labelDescriptionView = holder.labelDescriptionView;
        labelDescriptionView.setLabel(label);
        labelDescriptionView.setDescription(description);
        labelDescriptionView.setIconVisibility(isEditing);
        if (clickListener != null) {
            labelDescriptionView.setOnClickListener(clickListener);
        }
        if(iconResId != 0) {
            labelDescriptionView.setIcon(iconResId);
        }
    }
}
