package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.InsuranceCompany;
import com.example.ering.trekinsync.models.User;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DisplayQrCodeActivity extends AppCompatActivity {
    private final static int QR_CODE_WIDTH = 800 ;
    private ImageView imageView;
    private LinearLayout preferencesContainer;
    private Button generateQRrButton;
    private Bitmap bitmap ;
    private User user;
    private ProgressBar progressBar;

    private ImageButton calendarButton;
    private TextView expiryDate;
    private RadioButton shareInsuranceInfo;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr_code);
        context = getApplicationContext();

        imageView = (ImageView) findViewById(R.id.qr_code_view);
        preferencesContainer =  (LinearLayout) findViewById(R.id.preferences_container);
        generateQRrButton = (Button) findViewById(R.id.generate_qr_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("UserObj");
        //TODO add insurance info radio button logic
        //remove insurance information
        InsuranceCompany[] emptyInsurance = new InsuranceCompany[0];
        user.setInsuranceInfo(emptyInsurance);

        //TODO add datepicker logic for expiry date

        generateQRrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressSpinner();
                preferencesContainer.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        generateQrCode();
                    }
                }).start();
            }
        });

        calendarButton = (ImageButton) findViewById(R.id.calendar_button);
        expiryDate = (TextView) findViewById(R.id.date);
        shareInsuranceInfo = (RadioButton) findViewById(R.id.insurance_button);
        expiryDate.setText("March 15, 2018");
    }

    private void generateQrCode() {
        //TODO remove insurance info from user before encoding
        Gson gson = new Gson();
        String json = gson.toJson(user);

        try {
            final Bitmap testBitmap = encodeToQrCode(json);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    bitmap = testBitmap;
                    imageView.setImageBitmap(bitmap);
                    hideProgressSpinner();
                }
            });
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_LONG).show();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    hideProgressSpinner();
                }
            });
        }
    }

    private void showProgressSpinner() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    private void hideProgressSpinner() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    /**
     * Sample Code used found here.
     * https://www.android-examples.com/generate-qr-code-in-android-using-zxing-library-in-android-studio/
     * @param text, string value to encode
     * @return
     * @throws WriterException
     */
    private Bitmap  encodeToQrCode(String text) throws WriterException {
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
                        getResources().getColor(R.color.qrCodeBlack):getResources().getColor(R.color.qrCodeWhite);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, QR_CODE_WIDTH, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
