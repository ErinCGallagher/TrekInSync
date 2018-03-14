package com.example.ering.trekinsync.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class AddRowIconTitleCellViewHolder extends RecyclerView.ViewHolder {
    public TextView title;
    public LinearLayout addRowButtonContainer;

    public AddRowIconTitleCellViewHolder(View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title_text);
        addRowButtonContainer = itemView.findViewById(R.id.add_row_button_container);
    }

    public static int getLayoutId() {
        return R.layout.add_row_icon_title_cell;
    }

    public void setTitle(String titleText) {
        title.setText(titleText);
    }

    public void setAddRowButtonListener(View.OnClickListener listener) {
        addRowButtonContainer.setOnClickListener(listener);
    }
}
