package com.example.ering.trekinsync.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.QrCodeView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.QrCodePresenter;

import java.util.Calendar;

public class DisplayQrCodeActivity extends AppCompatActivity implements QrCodeView{
    private ImageView imageView;
    private LinearLayout preferencesContainer;
    private Button generateQRrButton;
    private User user;
    private ProgressBar progressBar;
    private QrCodePresenter presenter;

    private ImageButton calendarButton;
    private TextView expiryDate;
    private CheckBox shareInsuranceInfo;
    private Context context;
    private android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_qr_code);
        context = getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar =  getSupportActionBar();

        imageView = findViewById(R.id.qr_code_view);
        preferencesContainer = findViewById(R.id.preferences_container);
        generateQRrButton = findViewById(R.id.generate_qr_button);
        progressBar = findViewById(R.id.progress_bar);
        calendarButton = findViewById(R.id.calendar_button);
        expiryDate = findViewById(R.id.date);
        shareInsuranceInfo = findViewById(R.id.insurance_button);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        user = bundle.getParcelable("UserObj");
        presenter = new QrCodePresenter(context, this, user);

        String actionBarTitle = presenter.getActionBarTitle();
        actionBar.setTitle(actionBarTitle);

        setUpOnClickListeners();
    }

    public void showDatePickerDialog(View v) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(presenter.getExpiryDate());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                presenter.handleExpiryDateChange(year, month, dayOfMonth);
            }
        }, year, month, day);
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();
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

    @Override
    public void setExpiryDateText(String expiryDateText) {
        expiryDate.setText(expiryDateText);
    }

    private void setUpOnClickListeners() {
        generateQRrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleGenerateQrCodeButtonTap(shareInsuranceInfo.isChecked());
            }
        });

        expiryDate.setText(presenter.getDefaultFormattedExpiryDate());

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
    }
}
