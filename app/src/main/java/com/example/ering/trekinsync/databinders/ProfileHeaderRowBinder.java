package com.example.ering.trekinsync.databinders;

import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.viewholders.ProfileHeaderViewHolder;

public class ProfileHeaderRowBinder extends BaseDataBinder<ProfileHeaderViewHolder> {
    private String profileName;
    private int headerColor;
    private DataInputListener<String> listener;
    private boolean isEditMode = false;

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor) {
        this.profileName = profileName;
        this.headerColor = headerColor;
    }

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor, DataInputListener<String> listener) {
        this.profileName = profileName;
        this.headerColor = headerColor;
        this.listener = listener;
        this.isEditMode = true;
    }

    @Override
    public ProfileHeaderViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(ProfileHeaderViewHolder.getLayoutId(), parent);
        return new ProfileHeaderViewHolder(view);
    }

    @Override
    public void bindViewHolder(ProfileHeaderViewHolder holder) {
        holder.setEditMode(isEditMode);
        holder.setTitle(profileName);
        holder.setHeaderColor(headerColor);
    }
}