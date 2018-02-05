package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.databinders.BaseDataBinder;
import com.example.ering.trekinsync.viewholders.LabelDescriptionViewHolder;

public class LabelDescriptionRowBinder extends BaseDataBinder<LabelDescriptionViewHolder> {

    private String label;
    private String description;
    /**
     * creates a view holder for a static label and description form field
     */
    public LabelDescriptionRowBinder(String label, String description) {
        this.label = label;
        this.description = description;
    }


    @Override
    public LabelDescriptionViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(LabelDescriptionViewHolder.getLayoutId(), parent);
        return new LabelDescriptionViewHolder(view);
    }

    @Override
    public void bindViewHolder(LabelDescriptionViewHolder holder) {

    }
}
