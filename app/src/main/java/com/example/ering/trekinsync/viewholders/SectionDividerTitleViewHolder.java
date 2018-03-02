package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class SectionDividerTitleViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public SectionDividerTitleViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_text);
    }

    public static int getLayoutId() {
        return R.layout.section_divider_title_cell;
    }

    public void setTitle(String titleText) {
        title.setText(titleText);
    }
}
