package com.example.ering.trekinsync.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.QrCodeView;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.User;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QrCodePresenter {
    private final static int QR_CODE_WIDTH = 800 ;

    private Context context;
    private QrCodeView view;
    private Bitmap bitmap ;
    private User user;
    private Date expiryDate;
    private SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");

    public QrCodePresenter(Context context, QrCodeView view, User user) {
        this.context = context;
        this.view = view;
        this.user = user;
        setDefaultExpiryDate();
    }

    public String getDefaultFormattedExpiryDate() {
        return formatter.format(expiryDate);
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Handle Generate QR code button tap given set preferences by user.
     * Generates a QR code given the user data.
     */
    public void handleGenerateQrCodeButtonTap(boolean shareInsuranceInfoChecked, String expiryDateValue) {
        if (!shareInsuranceInfoChecked) {
            removeInsuranceInformation();
        }

        if (expiryDateValue != null) {
            user.setContactExpiryDate(expiryDateValue);
        }

        view.showProgressSpinner();
        view.hidePreferencesContainer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                generateQrCode();
            }
        }).start();
    }

    /**
     * Given date details, update the expiry date.
     * @param year, year represented as int
     * @param month, month represented as int
     * @param dayOfMonth, day of the moth represented as int
     */
    public void handleExpiryDateChange(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        this.expiryDate = calendar.getTime();
        view.setExpiryDateText(formatter.format(expiryDate));
    }


    /* Private Functions */

    /**
     * Set the default expiry date to be 2 weeks from the current day.
     */
    private void setDefaultExpiryDate() {
        int twoWeeks = 14;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, twoWeeks);
        this.expiryDate = calendar.getTime();
    }

    /**
     * Removes the insurance information data from the User object.
     * @return User object model with empty insurance information
     */
    private void removeInsuranceInformation() {
        //remove insurance information
        InsuranceCompany[] emptyInsurance = new InsuranceCompany[0];
        user.setInsuranceInfo(emptyInsurance);
    }

    private void generateQrCode() {
        Gson gson = new Gson();
        String json = gson.toJson(user);

        try {
            final Bitmap testBitmap = encodeToQrCode(json);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    bitmap = testBitmap;
                    view.setQrCodeBitmap(bitmap);
                    view.hideProgressSpinner();
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error generating QR code", Toast.LENGTH_LONG).show();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    view.hideProgressSpinner();
                }
            });
        }
    }

    /**
     * Sample Code used found here.
     * https://www.android-examples.com/generate-qr-code-in-android-using-zxing-library-in-android-studio/
     * @param text, string value to encode
     * @return
     * @throws WriterException
     */
    private Bitmap encodeToQrCode(String text) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    text,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QR_CODE_WIDTH, QR_CODE_WIDTH, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {
            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                        context.getResources().getColor(R.color.qrCodeBlack):context.getResources().getColor(R.color.qrCodeWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, QR_CODE_WIDTH, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }


}
