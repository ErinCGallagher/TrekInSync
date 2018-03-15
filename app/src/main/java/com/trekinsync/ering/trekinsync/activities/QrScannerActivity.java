package com.trekinsync.ering.trekinsync.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Toast;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.interfaces.QrScannerView;
import com.trekinsync.ering.trekinsync.presenters.QrScannerPresenter;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler, QrScannerView {

    public static final int PERMISSION_REQUEST_CAMERA = 1;

    private ZXingScannerView mScannerView;
    private Context context;
    private android.support.v7.app.ActionBar actionBar;
    private QrScannerPresenter presenter;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_qr_code_scanner);
        context = getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar =  getSupportActionBar();
        presenter = new QrScannerPresenter(context, this);

        String actionBarTitle = presenter.getActionBarTitle();
        actionBar.setTitle(actionBarTitle);

        ViewGroup scannerContentFrame = (ViewGroup) findViewById(R.id.scanner_frame);
        mScannerView = new ZXingScannerView(this);
        scannerContentFrame.addView(mScannerView);

        if (!presenter.isCameraAccessGranted()) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    PERMISSION_REQUEST_CAMERA);
        }
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
        presenter.handleQrCodeScanResults(rawResult.toString());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //TODO: swap if statement
                } else {
                    Toast.makeText(this, "Camera Permission is required to scan travel contact QR codes", Toast.LENGTH_LONG).show();
                    returnToLandingActivity();
                }
                return;
            }
        }
    }

    @Override
    public void returnToLandingActivity() {
        Intent intent = new Intent(context, LandingActivity.class);
        startActivity(intent);
    }
}
