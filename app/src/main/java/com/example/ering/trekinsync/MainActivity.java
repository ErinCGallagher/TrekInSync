package com.example.ering.trekinsync;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements ProfileView {

    private ProfilePresenter presenter;
    private ProfileAdapter adapter;
    private RecyclerView profileListView;
    private Context context;
    private android.support.v7.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        actionBar =  getSupportActionBar();
        startPersonalProfileFlow();
    }

    private void startPersonalProfileFlow() {
        //setup presenter
        presenter = new ProfilePresenter();
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
