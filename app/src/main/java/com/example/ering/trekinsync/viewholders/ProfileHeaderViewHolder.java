package com.example.ering.trekinsync.viewholders;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class ProfileHeaderViewHolder extends RecyclerView.ViewHolder {
    public TextView profileName;
    public LinearLayout topBackgroundHeader;

    public ProfileHeaderViewHolder(View itemView) {
        super(itemView);
        profileName = itemView.findViewById(R.id.profile_name);
        topBackgroundHeader = itemView.findViewById(R.id.top_background_container);
    }

    public static int getLayoutId() {
        return R.layout.profile_header;
    }

    public void setTitle(String titleText) {
        profileName.setText(titleText);
    }

    public void setHeaderColor(@ColorInt int headerColor) {
        topBackgroundHeader.setBackgroundColor(headerColor);
    }
}
