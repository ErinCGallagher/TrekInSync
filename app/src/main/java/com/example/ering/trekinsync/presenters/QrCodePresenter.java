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

public class QrCodePresenter {
    private final static int QR_CODE_WIDTH = 800 ;

    private Context context;
    private QrCodeView view;
    private Bitmap bitmap ;
    private User user;

    public QrCodePresenter(Context context, QrCodeView view, User user) {
        this.context = context;
        this.view = view;
        this.user = removeInsuranceInformation(user);
    }

    /**
     * Handle Generate QR code button tap given set preferences by user.
     * Generates a QR code given the user data.
     */
    public void handleGenerateQrCodeButtonTap() {
        //TODO add functionality t handle user preferences for qr code generation
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
     * Removes the insurance information data from the User object.
     * @param user, user Object Model
     * @return User object model with empty insurance information
     */
    private User removeInsuranceInformation(User user) {
        //remove insurance information
        InsuranceCompany[] emptyInsurance = new InsuranceCompany[0];
        user.setInsuranceInfo(emptyInsurance);
        return user;
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
