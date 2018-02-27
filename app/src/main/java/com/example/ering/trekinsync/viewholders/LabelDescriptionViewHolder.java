package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.customviews.LabelDescriptionView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;

public class LabelDescriptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public LabelDescriptionView labelDescriptionView;
    public RecyclerViewClickListener clickListener;

    /**
     * creates a view holder for a static label and description cell
     * @param itemView
     */
    public LabelDescriptionViewHolder(View itemView, RecyclerViewClickListener clickListener) {
        super(itemView);
        labelDescriptionView = (LabelDescriptionView) itemView.findViewById(R.id.label_description_cell);
        this.clickListener = clickListener;
        itemView.setOnClickListener(this);
    }

    public static int getLayoutId() {
        return R.layout.label_description_row_entry;
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null) clickListener.onClick(v, getAdapterPosition());
    }
}
