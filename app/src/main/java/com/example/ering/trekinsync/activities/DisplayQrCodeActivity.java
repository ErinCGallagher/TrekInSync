package com.example.ering.trekinsync.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.User;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

public class DisplayQrCodeActivity extends AppCompatActivity {
    private final static int QR_CODE_WIDTH = 800 ;
    private ImageView imageView;
    private Bitmap bitmap ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr_code);

        imageView = (ImageView) findViewById(R.id.qrcodeview);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = bundle.getParcelable("UserObj");

        //TODO remove insurance info from user before encoding
        Gson gson = new Gson();
        String json = gson.toJson(user);

        //TODO: add loading spinner to show progress
        try {
            bitmap = encodeToQrCode(json);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error generating QR code", Toast.LENGTH_LONG).show();
        }
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
