package com.trekinsync.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.customviews.OpenFieldWithLabelView;

public class OpenTextWithLabelCellViewHolder extends RecyclerView.ViewHolder {
    public OpenFieldWithLabelView openTextView;

    /**
     * creates a view holder for open text with label cell
     * @param itemView
     */
    public OpenTextWithLabelCellViewHolder(View itemView) {
        super(itemView);
        openTextView = itemView.findViewById(R.id.open_text_with_label_cell);
    }

    public static int getLayoutId() {
        return R.layout.open_text_field_with_label_row_entry;
    }
}
