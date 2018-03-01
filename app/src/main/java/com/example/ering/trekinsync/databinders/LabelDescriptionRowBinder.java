package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.LabelDescriptionView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.viewholders.LabelDescriptionViewHolder;

public class LabelDescriptionRowBinder extends BaseDataBinder<LabelDescriptionViewHolder> {
    private String label;
    private String description;
    private View.OnClickListener clickListener;
    private boolean isEditing;

    /**
     * creates a view holder for a static label and description form field with on click listener.
     */
    public LabelDescriptionRowBinder(String label, String description, View.OnClickListener clickListener) {
        this.label = label;
        this.description = description;
        this.clickListener = clickListener;
        this.isEditing = true;
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
    }
}
