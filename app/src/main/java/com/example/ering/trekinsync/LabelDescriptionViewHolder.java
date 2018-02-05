package com.example.ering.trekinsync;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

public class LabelDescriptionViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout view1;

    /**
     * creates a view holder for a static label and description form field
     * @param itemView
     */
    public LabelDescriptionViewHolder(View itemView) {
        super(itemView);
    }

    public static int getLayoutId() {
        return R.layout.label_description_row_entry;
    }
}
