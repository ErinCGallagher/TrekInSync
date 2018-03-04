package com.example.ering.trekinsync.databinders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.EditPhoneNumberView;
import com.example.ering.trekinsync.viewholders.EditPhoneNumberViewHolder;

public class EditPhoneNumberRowBinder extends BaseDataBinder<EditPhoneNumberViewHolder> {
    private String phoneNumberRelationship;
    private String phoneNumberType;
    private String phoneNumber;

    /**
     * creates a view holder for edit phone number cell with phone number type and relationship.
     */
    public EditPhoneNumberRowBinder(@NonNull String relationship, @NonNull String type, @Nullable String phoneNumber) {
        this.phoneNumberRelationship = relationship;
        this.phoneNumberType = type;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public EditPhoneNumberViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(EditPhoneNumberViewHolder.getLayoutId(), parent);
        return new EditPhoneNumberViewHolder(view);
    }

    @Override
    public void bindViewHolder(EditPhoneNumberViewHolder holder) {
        final EditPhoneNumberView editPhoneNumberView = holder.editPhoneNumberView;
        editPhoneNumberView.setRelationship(phoneNumberRelationship);
        editPhoneNumberView.setPhoneNumberType(phoneNumberType);
        editPhoneNumberView.setPhoneNumber(phoneNumber);
    }
}
