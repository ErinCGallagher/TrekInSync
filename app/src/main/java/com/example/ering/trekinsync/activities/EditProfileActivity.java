package com.example.ering.trekinsync.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.EditProfileView;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
    }
}
