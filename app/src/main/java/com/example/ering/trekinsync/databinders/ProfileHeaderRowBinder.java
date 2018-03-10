package com.example.ering.trekinsync.databinders;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.viewholders.ProfileHeaderViewHolder;

public class ProfileHeaderRowBinder extends BaseDataBinder<ProfileHeaderViewHolder> {
    private String profileName;
    private int headerColor;
    private LayerDrawable profileIcon;
    private DataInputListener<String> listener;
    private boolean isEditMode = false;

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor, Drawable profileIcon) {
        this.profileName = profileName;
        this.headerColor = headerColor;

        //setup profile icon
        Drawable[] layers = new Drawable[1];
        layers[0] = profileIcon;
        this.profileIcon = new LayerDrawable(layers);
    }

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor,
                                  LayerDrawable profileIcon, DataInputListener<String> listener) {
        this.profileName = profileName;
        this.headerColor = headerColor;
        this.profileIcon = profileIcon;
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
        holder.setTextChangeListener(listener);
        holder.setProfileIcon(profileIcon);
    }
}