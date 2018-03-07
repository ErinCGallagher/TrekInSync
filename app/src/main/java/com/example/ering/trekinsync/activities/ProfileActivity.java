package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ering.trekinsync.adapters.ProfileAdapter;
import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.interfaces.ProfileView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.ProfilePresenter;

public class ProfileActivity extends AppCompatActivity implements ProfileView{

    private ProfilePresenter presenter;
    private ProfileAdapter adapter;
    private RecyclerView profileListView;
    private Context context;
    private android.support.v7.app.ActionBar actionBar;

    static final int EDIT_PROFILE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        context = getApplicationContext();
        actionBar =  getSupportActionBar();
        startPersonalProfileFlow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        presenter.handleMenuSetup(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            presenter.handleDeleteProfileButtonClick();
        } else if (id == R.id.action_edit) {
            presenter.handleEditProfileButtonClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshMenu() {
        invalidateOptionsMenu();
    }

    @Override
    public void launchLandingPage() {
        finish();
    }

    @Override
    public void launchConfirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.delete_profile_alert_message)
                .setTitle(R.string.delete_profile_alert_title);

        builder.setPositiveButton(R.string.delete_alert_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.handleDeleteContactConfirmation();
            }
        });
        builder.setNegativeButton(R.string.delete_alert_Negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //dismiss dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void launchEditProfile() {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra("UserObj", presenter.getUser());
        startActivityForResult(intent, EDIT_PROFILE_REQUEST);
    }

    @Override
    public void reloadData() {
        profileListView.post(new Runnable() {
            @Override
            public void run() {
                adapter.reloadData();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_PROFILE_REQUEST) {
            if (resultCode == RESULT_OK) {
                User editedUser = parseUserIntent(data);
                presenter.setUser(editedUser);
            }
        }
    }

    private User parseUserIntent(Intent intentData) {
        Bundle bundle = intentData.getExtras();
        User user = null;
        try {
            user = bundle.getParcelable("UserObj");
        } catch (Exception e) {
            //Do Nothing
        }
        return user;
    }

    private void startPersonalProfileFlow() {
        User user = parseUserIntent(getIntent());
        //setup presenter
        presenter = new ProfilePresenter(user, context, this);
        String actionBarTitle = presenter.getActionBarTitle();
        actionBar.setTitle(actionBarTitle);

        //setup adapter
        adapter = new ProfileAdapter(presenter);
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
