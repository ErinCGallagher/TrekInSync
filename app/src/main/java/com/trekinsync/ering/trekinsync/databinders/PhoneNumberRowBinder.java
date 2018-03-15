package com.trekinsync.ering.trekinsync.databinders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.trekinsync.ering.trekinsync.customviews.PhoneNumberView;
import com.trekinsync.ering.trekinsync.viewholders.PhoneNumberViewHolder;

public class PhoneNumberRowBinder extends BaseDataBinder<PhoneNumberViewHolder> {
    private String label;
    private String phoneNumber;
    private String phoneNumberType;

    /**
     * creates a view holder for phone number cell with phone number type
     */
    public PhoneNumberRowBinder(@NonNull String label, @NonNull String phoneNumber, @Nullable String phoneNumberType) {
        this.label = label;
        this.phoneNumber = phoneNumber;
        this.phoneNumberType = phoneNumberType;
    }

    @Override
    public PhoneNumberViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(PhoneNumberViewHolder.getLayoutId(), parent);
        return new PhoneNumberViewHolder(view);
    }

    @Override
    public void bindViewHolder(PhoneNumberViewHolder holder) {
        final PhoneNumberView phoneNumberView = holder.phoneNumberView;
        phoneNumberView.setLabel(label);
        phoneNumberView.setPhoneNumber(phoneNumber);
        phoneNumberView.setPhoneNumberType(phoneNumberType);
    }
}
