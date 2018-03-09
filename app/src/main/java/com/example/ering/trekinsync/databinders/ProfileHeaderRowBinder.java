package com.example.ering.trekinsync.databinders;

import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.viewholders.ProfileHeaderViewHolder;

public class ProfileHeaderRowBinder extends BaseDataBinder<ProfileHeaderViewHolder> {
    private String profileName;
    private int headerColor;

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor) {
        this.profileName = profileName;
        this.headerColor = headerColor;
    }
    @Override
    public ProfileHeaderViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(ProfileHeaderViewHolder.getLayoutId(), parent);
        return new ProfileHeaderViewHolder(view);
    }

    @Override
    public void bindViewHolder(ProfileHeaderViewHolder holder) {
        holder.setTitle(profileName);
        holder.setHeaderColor(headerColor);
    }
}