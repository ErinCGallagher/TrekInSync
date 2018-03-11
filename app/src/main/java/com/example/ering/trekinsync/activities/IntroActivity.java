package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.IntroAdapter;

public class IntroActivity extends AppCompatActivity {

    public static final String COMPLETED_ON_BOARDING = "com.example.trekinsync.completedonboarding";
    private ViewPager viewPager;
    private TextView doneButton;
    private Context context;
    private boolean isOnboarding = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);
        context = getApplicationContext();

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        doneButton = (TextView) findViewById(R.id.done_button);

        //Set Adapter
        viewPager.setAdapter(new IntroAdapter(getSupportFragmentManager()));

        //Add Tab buttons
        TabLayout tabLayout = (TabLayout) findViewById(R.id.dot_indicator);
        tabLayout.setupWithViewPager(viewPager, true);

        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(COMPLETED_ON_BOARDING, true);
        editor.apply();

        //get onboarding intent
        Bundle bundle = getIntent().getExtras();
        try {
            isOnboarding = bundle.getBoolean("Onboarding");
        } catch (Exception e) {
            e.printStackTrace();
        }

        @StringRes int buttonText = isOnboarding ? R.string.intro_lets_go_button : R.string.intro_done_button;
        doneButton.setText(buttonText);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnboarding) {
                    launchCreateProfile();
                } else {
                    backToLanding();
                }
            }
        });
    }

    private void launchCreateProfile() {
        Intent intent = new Intent(context, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void backToLanding() {
        finish();
    }
}
