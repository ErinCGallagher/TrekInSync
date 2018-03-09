package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.LabelDescriptionView;

public class DualLabelDescriptionViewHolder extends RecyclerView.ViewHolder {
    public LabelDescriptionView leftSide;
    public LabelDescriptionView rightSide;

    /**
     * creates a view holder for a static label and description cell
     * @param itemView
     */
    public DualLabelDescriptionViewHolder(View itemView) {
        super(itemView);
        leftSide = itemView.findViewById(R.id.left_side);
        rightSide = itemView.findViewById(R.id.right_side);
    }

    public static int getLayoutId() {
        return R.layout.dual_label_description_cell;
    }
}