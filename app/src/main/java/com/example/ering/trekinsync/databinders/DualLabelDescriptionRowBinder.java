package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.LabelDescriptionView;
import com.example.ering.trekinsync.viewholders.DualLabelDescriptionViewHolder;

public class DualLabelDescriptionRowBinder extends BaseDataBinder<DualLabelDescriptionViewHolder> {
    private String labelLeft;
    private String descriptionLeft;
    private String labelRight;
    private String descriptionRight;

    /**
     * creates a view holder for a static label and description form field with on click listener.
     */
    public DualLabelDescriptionRowBinder(String labelLeft, String descriptionLeft, String labelRight, String descriptionRight) {
        this.labelLeft = labelLeft;
        this.descriptionLeft = descriptionLeft;
        this.labelRight = labelRight;
        this.descriptionRight = descriptionRight;
    }


    @Override
    public DualLabelDescriptionViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(DualLabelDescriptionViewHolder.getLayoutId(), parent);
        return new DualLabelDescriptionViewHolder(view);
    }

    @Override
    public void bindViewHolder(DualLabelDescriptionViewHolder holder) {
        final LabelDescriptionView labelDescriptionViewLeft = holder.leftSide;
        final LabelDescriptionView labelDescriptionViewRight = holder.rightSide;
        labelDescriptionViewLeft.setLabel(labelLeft);
        labelDescriptionViewLeft.setDescription(descriptionLeft);
        labelDescriptionViewRight.setLabel(labelRight);
        labelDescriptionViewRight.setDescription(descriptionRight);
    }
}