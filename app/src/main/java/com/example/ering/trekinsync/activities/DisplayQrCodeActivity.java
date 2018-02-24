package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.QrCodeView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.QrCodePresenter;

public class DisplayQrCodeActivity extends AppCompatActivity implements QrCodeView{
    private ImageView imageView;
    private LinearLayout preferencesContainer;
    private Button generateQRrButton;
    private User user;
    private ProgressBar progressBar;
    private QrCodePresenter presenter;

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
        calendarButton = (ImageButton) findViewById(R.id.calendar_button);
        expiryDate = (TextView) findViewById(R.id.date);
        shareInsuranceInfo = (RadioButton) findViewById(R.id.insurance_button);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("UserObj");
        presenter = new QrCodePresenter(context, this, user);


        generateQRrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleGenerateQrCodeButtonTap();
            }
        });

        //TODO add datepicker logic for expiry date
        expiryDate.setText("March 15, 2018");

        //TODO add insurance info radio button logic
    }


    @Override
    public void showProgressSpinner() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminate(true);
    }

    @Override
    public void hideProgressSpinner() {
        progressBar.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePreferencesContainer() {
        preferencesContainer.setVisibility(View.GONE);
    }

    @Override
    public void setQrCodeBitmap(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
