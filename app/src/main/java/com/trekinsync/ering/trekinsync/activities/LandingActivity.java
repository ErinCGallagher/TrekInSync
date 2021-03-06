package com.trekinsync.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.adapters.LandingAdapter;
import com.trekinsync.ering.trekinsync.fragments.FabMenuFragment;
import com.trekinsync.ering.trekinsync.interfaces.LandingView;
import com.trekinsync.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.trekinsync.ering.trekinsync.models.User;
import com.trekinsync.ering.trekinsync.presenters.LandingPresenter;
import com.trekinsync.ering.trekinsync.utils.UserSingletonUtils;

import de.hdodenhof.circleimageview.CircleImageView;

public class LandingActivity extends AppCompatActivity implements RecyclerViewClickListener, LandingView {

    private Context context;
    private TextView profileName;
    private RecyclerView contactListView;
    private LandingPresenter presenter;
    private LandingAdapter adapter;
    private CircleImageView profileIcon;
    private android.support.v7.app.ActionBar actionBar;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        context = getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar =  getSupportActionBar();
        setupDrawer();

        //create User Utils Singleton
        UserSingletonUtils.init(context);

        //If on boarding has not been completed, launch intro screens
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);
        if (!sharedPref.getBoolean(IntroActivity.COMPLETED_ON_BOARDING, false)) {
            launchAppDetailPages(true);
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
            setUpProfileHeader();
            refreshFabMenuData();
        }
    }

    private void setupDrawer() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBar.setTitle("");

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        int id = menuItem.getItemId();
                        menuItem.setChecked(true);
                        if (id == R.id.nav_profile) {
                            presenter.handleNavMenuViewProfile();
                        } else if (id == R.id.nav_edit_profile) {
                            presenter.handleNavMenuEditProfile();
                        } else if (id == R.id.nav_details) {
                            launchAppDetailPages(false);
                        }
                        menuItem.setChecked(false);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void startLandingPageSetup() {
        //setup presenter
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name_key),Context.MODE_PRIVATE);
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
        profileName = findViewById(R.id.profile_name);
        profileIcon = findViewById(R.id.profile_icon);

        profileName.setText(presenter.getUserName());
        profileIcon.setImageDrawable(presenter.getProfileIcon());
        profileIcon.setOnClickListener(new View.OnClickListener() {
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
    public void launchEditProfilePage(@NonNull User user) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra("UserObj", user);
        startActivity(intent);
    }

    @Override
    public void launchCreateProfilePage() {
        Intent intent = new Intent(context, EditProfileActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void launchAppDetailPages(boolean isOnboarding) {
        Intent intent = new Intent(this, IntroActivity.class);
        intent.putExtra("Onboarding", isOnboarding);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void refreshFabMenuData() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FabMenuFragment fragment = new FabMenuFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("UserObj", presenter.getUser());
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }
}
