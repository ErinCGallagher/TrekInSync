package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.LandingAdapter;
import com.example.ering.trekinsync.fragments.FabMenuFragment;
import com.example.ering.trekinsync.interfaces.LandingView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.LandingPresenter;
import com.example.ering.trekinsync.utils.UserSingletonUtils;

public class LandingActivity extends AppCompatActivity implements RecyclerViewClickListener, LandingView {

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

        //create User Utils Singleton
        UserSingletonUtils.init(context);

        //If on boarding has not been completed, launch intro screens
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean(IntroActivity.COMPLETED_ON_BOARDING, false)) {
            startActivity(new Intent(this, IntroActivity.class));
            finish();
        } else {
            startLandingPageSetup();
            initializeFabMenu();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (presenter != null) {
            presenter.updateProfileAndContacts();
        }
    }

    private void startLandingPageSetup() {
        //setup presenter
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.trekinsync.userData",Context.MODE_PRIVATE);
        presenter = new LandingPresenter(sharedPref, context, this);

        //setup adapter
        adapter = new LandingAdapter(presenter);
        initRecyclerView();
        adapter.buildRows();

        setUpProfileHeader();
    }

    private void initRecyclerView() {
        contactListView = (RecyclerView) findViewById(R.id.contact_recycler_view);
        contactListView.setLayoutManager(new LinearLayoutManager(context));
        contactListView.setItemAnimator(new DefaultItemAnimator());
        contactListView.setAdapter(adapter);
        adapter.setClickListener(this);
    }

    private void setUpProfileHeader() {
        profileName = (TextView) findViewById(R.id.profile_name);
        personalProfileLink = (TextView) findViewById(R.id.profile_link);

        profileName.setText(presenter.getUserName());
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
    }

    @Override
    public void onClick(View v, int position) {
        presenter.handleTravelContactClick(position);
    }

    @Override
    public void reloadData() {
        contactListView.post(new Runnable() {
            @Override
            public void run() {
                adapter.reloadData();
            }
        });
    }

    @Override
    public void launchProfilePage(@NonNull User user) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("UserObj", user);
        startActivity(intent);
    }

    @Override
    public void launchCreateProfilePage() {
        Intent intent = new Intent(context, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    private void initializeFabMenu() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FabMenuFragment fragment = new FabMenuFragment();
        //pass in user data to fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("UserObj", presenter.getUser());
        fragment.setArguments(bundle);
        //add fragment to activity
        fragmentTransaction.add(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
