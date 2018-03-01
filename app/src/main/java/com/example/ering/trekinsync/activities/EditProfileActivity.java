package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.EditProfileAdapter;
import com.example.ering.trekinsync.interfaces.EditProfileView;
import com.example.ering.trekinsync.interfaces.RecyclerViewClickListener;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.EditProfilePresenter;

import static android.view.Gravity.BOTTOM;
import static android.view.Gravity.TOP;

public class EditProfileActivity extends AppCompatActivity implements EditProfileView {

    private Context context;
    private android.support.v7.app.ActionBar actionBar;
    private EditProfilePresenter presenter;
    private EditProfileAdapter adapter;
    private RecyclerView profileDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        actionBar =  getSupportActionBar();
        context = getApplicationContext();
        startProfileFlow();
    }

    private void startProfileFlow() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        User user = null;
        try {
            user = bundle.getParcelable("UserObj");
        } catch (Exception e) {
            //Do Nothing
        }

        //setup presenter
        presenter = new EditProfilePresenter(context, this, user);

        String actionBarTitle = presenter.getActionBarTitle();
        actionBar.setTitle(actionBarTitle);

        //setup adapter
        adapter = new EditProfileAdapter(presenter);
        initRecyclerView();
        adapter.buildRows();
    }

    private void initRecyclerView() {
        profileDetailsList = (RecyclerView) findViewById(R.id.profileRecyclerView);
        profileDetailsList.setLayoutManager(new LinearLayoutManager(context));
        profileDetailsList.setItemAnimator(new DefaultItemAnimator());
        profileDetailsList.setAdapter(adapter);
    }

    @Override
    public void launchPopUp(String title, @ArrayRes int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setSingleChoiceItems(itemsId, checkedItem, listener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void reloadData() {
        profileDetailsList.post(new Runnable() {
            @Override
            public void run() {
                adapter.reloadData();
            }
        });
    }
}
