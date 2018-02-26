package com.example.ering.trekinsync.interfaces;

import android.graphics.Bitmap;

/**
 * Create an interface for view related activities
 */
public interface QrCodeView {
    void showProgressSpinner();
    void hideProgressSpinner();
    void hidePreferencesContainer();
    void setQrCodeBitmap(Bitmap bitmap);
    void setExpiryDateText(String expiryDate);
}
