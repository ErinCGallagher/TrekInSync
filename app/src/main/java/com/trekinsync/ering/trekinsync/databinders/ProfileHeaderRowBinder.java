package com.trekinsync.ering.trekinsync.databinders;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;

import com.trekinsync.ering.trekinsync.interfaces.DataInputListener;
import com.trekinsync.ering.trekinsync.viewholders.ProfileHeaderViewHolder;

public class ProfileHeaderRowBinder extends BaseDataBinder<ProfileHeaderViewHolder> {
    private String profileName;
    private int headerColor;
    private LayerDrawable profileIcon;
    private DataInputListener<String> nameListener;
    private View.OnClickListener iconListener;
    private boolean isEditMode = false;

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor, Drawable profileIcon) {
        this.profileName = profileName;
        this.headerColor = headerColor;

        //setup profile icon
        this.profileIcon = new LayerDrawable(new Drawable[] { profileIcon });
    }

    public ProfileHeaderRowBinder(String profileName, @ColorInt int headerColor,
                                  LayerDrawable profileIcon, DataInputListener<String> nameListener,
                                  View.OnClickListener iconListener) {
        this.profileName = profileName;
        this.headerColor = headerColor;
        this.profileIcon = profileIcon;
        this.nameListener = nameListener;
        this.iconListener = iconListener;
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
        holder.setProfileIconListener(iconListener);
        holder.setTextChangeListener(nameListener);
        holder.setProfileIcon(profileIcon);
    }
}