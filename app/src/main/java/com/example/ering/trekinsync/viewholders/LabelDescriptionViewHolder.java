package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.LabelDescriptionView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;

public class LabelDescriptionViewHolder extends RecyclerView.ViewHolder {
    public LabelDescriptionView labelDescriptionView;

    /**
     * creates a view holder for a static label and description cell
     * @param itemView
     */
    public LabelDescriptionViewHolder(View itemView) {
        super(itemView);
        labelDescriptionView = (LabelDescriptionView) itemView.findViewById(R.id.label_description_cell);
    }

    public static int getLayoutId() {
        return R.layout.label_description_row_entry;
    }

}
