package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ering.trekinsync.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
        private ZXingScannerView mScannerView;
        private Context context;

        @Override
        public void onCreate(Bundle state) {
            super.onCreate(state);
            setContentView(R.layout.activity_qr_code_scanner);
            context = getApplicationContext();

            ViewGroup scannerContentFrame = (ViewGroup) findViewById(R.id.scanner_frame);
            mScannerView = new ZXingScannerView(this);
            scannerContentFrame.addView(mScannerView);
        }

        @Override
        public void onResume() {
            super.onResume();
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }

        @Override
        public void onPause() {
            super.onPause();
            mScannerView.stopCamera();
        }

        @Override
        public void handleResult(Result rawResult) {
            Toast.makeText(this, "Scanned: " + rawResult.getText(), Toast.LENGTH_LONG).show();
            convertToUserObject();
        }

        private void convertToUserObject() {
            Intent intent = new Intent(context, LandingActivity.class);
            startActivity(intent);
        }
}
