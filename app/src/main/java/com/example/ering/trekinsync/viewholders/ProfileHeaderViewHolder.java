package com.example.ering.trekinsync.viewholders;

import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.DataInputListener;

public class ProfileHeaderViewHolder extends RecyclerView.ViewHolder {
    public TextView profileName;
    public LinearLayout topBackgroundHeader;
    public EditText editProfileName;
    public boolean isEditMode = false;

    public ProfileHeaderViewHolder(View itemView) {
        super(itemView);
        profileName = itemView.findViewById(R.id.profile_name);
        topBackgroundHeader = itemView.findViewById(R.id.top_background_container);
        editProfileName = itemView.findViewById(R.id.edit_profile_name);
    }

    public static int getLayoutId() {
        return R.layout.profile_header;
    }

    public void setTitle(String titleText) {
        if (isEditMode) {
            editProfileName.setText(titleText);
        } else{
            profileName.setText(titleText);
        }
    }

    public void setHeaderColor(@ColorInt int headerColor) {
        topBackgroundHeader.setBackgroundColor(headerColor);
    }

    public void setEditMode(boolean editMode) {
        if (editMode) {
            isEditMode = true;
            profileName.setVisibility(View.GONE);
            editProfileName.setVisibility(View.VISIBLE);
        }
    }

    public void setTextChangeListener(final DataInputListener<String> listener) {
        editProfileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(editProfileName.getText().toString());
            }
        });
    }
}
