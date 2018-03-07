package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class AddRowIconTitleCellViewHolder extends RecyclerView.ViewHolder {
    public TextView title;

    public AddRowIconTitleCellViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title_text);
    }

    public static int getLayoutId() {
        return R.layout.add_row_icon_title_cell;
    }

    public void setTitle(String titleText) {
        title.setText(titleText);
    }
}
