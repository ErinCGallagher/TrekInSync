package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ering.trekinsync.adapters.ProfileAdapter;
import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity {

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
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = bundle.getParcelable("UserObj");
        //setup presenter
        presenter = new ProfilePresenter(user, context);
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
