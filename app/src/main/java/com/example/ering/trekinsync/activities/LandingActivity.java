package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.LandingAdapter;
import com.example.ering.trekinsync.presenters.LandingPresenter;

import java.io.Serializable;

public class LandingActivity extends AppCompatActivity {

    private Context context;
    private TextView profileName;
    private TextView personalProfileLink;
    private RecyclerView contactListView;
    private LandingPresenter presenter;
    private LandingAdapter adapter;

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
        personalProfileLink.setText("View Profile >");

        personalProfileLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                //TODO: check is user profile was created
                intent.putExtra("UserObj", presenter.getUser());
                startActivity(intent);
            }
        });

        //setup presenter
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        presenter = new LandingPresenter(sharedPref, context);

        //setup adapter
        adapter = new LandingAdapter(getApplicationContext(), presenter);
        adapter.buildRows();
        initRecyclerView();
    }

    private void initRecyclerView() {
        contactListView = (RecyclerView) findViewById(R.id.contact_recycler_view);
        contactListView.setLayoutManager(new LinearLayoutManager(context));
        contactListView.setItemAnimator(new DefaultItemAnimator());
        contactListView.setAdapter(adapter);
    }
}
