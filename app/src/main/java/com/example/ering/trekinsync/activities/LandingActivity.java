package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;

public class LandingActivity extends AppCompatActivity {

    private Context context;
    private TextView profileName;
    private TextView personalProfileLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        context = getApplicationContext();
        startLandingPageSetup();
    }

    private void startLandingPageSetup() {
        profileName = (TextView) findViewById(R.id.profile_name);
        personalProfileLink = (TextView) findViewById(R.id.profile_link);

        profileName.setText("Erin Gallagher");
        personalProfileLink.setText("View Profile");

        personalProfileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
