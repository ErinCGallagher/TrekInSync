package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ering.trekinsync.adapters.ProfileAdapter;
import com.example.ering.trekinsync.interfaces.ProfileView;
import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.presenters.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements ProfileView {

    private ProfilePresenter presenter;
    private ProfileAdapter adapter;
    private RecyclerView profileListView;
    private Context context;
    private android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = getApplicationContext();
        actionBar =  getSupportActionBar();
        startPersonalProfileFlow();
    }

    private void startPersonalProfileFlow() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        //setup presenter
        presenter = new ProfilePresenter(sharedPref, context);
        String actionBarTitle = presenter.getActionBarTitle();
        actionBar.setTitle(actionBarTitle);

        //setup adapter
        adapter = new ProfileAdapter(getApplicationContext(), presenter);
        adapter.buildRows();
        initRecyclerView();
    }

    private void initRecyclerView() {
        profileListView = (RecyclerView) findViewById(R.id.profileRecyclerView);
        profileListView.setLayoutManager(new LinearLayoutManager(context));
        profileListView.setItemAnimator(new DefaultItemAnimator());
        profileListView.setAdapter(adapter);
    }

}
