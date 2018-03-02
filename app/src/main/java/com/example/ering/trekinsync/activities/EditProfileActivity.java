package com.example.ering.trekinsync.activities;

import android.app.DatePickerDialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.EditProfileAdapter;
import com.example.ering.trekinsync.interfaces.EditProfileView;
import com.example.ering.trekinsync.models.User;
import com.example.ering.trekinsync.presenters.EditProfilePresenter;

import java.util.Calendar;
import java.util.Date;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            presenter.handleSaveProfileButtonClick();
        }
        return super.onOptionsItemSelected(item);
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
    public void launchDatePicker(Date birthDate, DatePickerDialog.OnDateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(birthDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, listener, year, month, day);
        datePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        datePicker.show();
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

    @Override
    public void launchProfileView(User editedUser) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.putExtra("UserObj", editedUser);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void launchLandingView() {
        Intent intent = new Intent(context, LandingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void launchConfirmationAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.save_profile_alert_message)
                .setTitle(R.string.save_profile_alert_title);

        builder.setPositiveButton(R.string.save_alert_positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
        builder.setNegativeButton(R.string.save_alert_Negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        presenter.handleBackButtonClick();
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
}
