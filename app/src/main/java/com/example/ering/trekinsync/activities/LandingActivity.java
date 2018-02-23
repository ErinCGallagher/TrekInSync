package com.example.ering.trekinsync.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.adapters.LandingAdapter;
import com.example.ering.trekinsync.presenters.LandingPresenter;

public class LandingActivity extends AppCompatActivity {

    private Context context;
    private TextView profileName;
    private TextView personalProfileLink;
    private RecyclerView contactListView;
    private LandingPresenter presenter;
    private LandingAdapter adapter;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabBarcode;
    private TextView fabCameraLabel;
    private TextView fabBarcodeLabel;
    private View dimMenuBackground;

    private boolean floatingButtonMenuState = false;
    private final static double FAB_MENU_MARGIN = 0.60;
    private final static double FAB_MENU_HEIGHT_1 = 2.15;
    private final static double FAB_MENU_HEIGHT_2 = 3.45;
    private final static double FAB_MENU_LABEL_HEIGHT = 1.5;
    private final static double FAB_MENU_LABEL_MARGIN = 2.0;
    private final static float ALPHA_FULLY_TRANSPARENT = 0.0f;
    private final static float ALPHA_OPAQUE = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        context = getApplicationContext();
        startLandingPageSetup();
        initializeFabMenu();
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

    private void initializeFabMenu() {
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!floatingButtonMenuState) {
                    displayDimBackground();
                    displayFloatingButtonMenu();
                    fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
                } else {
                    hideDimBackground();
                    fab.setImageResource(R.mipmap.ic_person_add_white_24dp);
                    hideFloatingButtonMenu();
                }
                floatingButtonMenuState = !floatingButtonMenuState;
            }
        });

        fabCamera = (FloatingActionButton) findViewById(R.id.fab_camera);
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: close menu before leaving activity
                Intent intent = new Intent(context, QrScannerActivity.class);
                startActivity(intent);
            }
        });

        fabBarcode = (FloatingActionButton) findViewById(R.id.fab_barcode);
        fabBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: close menu before leaving activity
                Intent intent = new Intent(context, DisplayQrCodeActivity.class);
                startActivity(intent);
            }
        });

        fabCameraLabel = (TextView) findViewById(R.id.fab_camera_label);
        fabBarcodeLabel = (TextView) findViewById(R.id.fab_barcode_label);
        dimMenuBackground = (View) findViewById(R.id.dim_menu_background);
    }

    private void displayDimBackground() {
        AlphaAnimation animation1 = new AlphaAnimation(ALPHA_FULLY_TRANSPARENT, 0.65f);
        animation1.setDuration(150);
        dimMenuBackground.setVisibility(View.VISIBLE);
        dimMenuBackground.startAnimation(animation1);
    }

    private void hideDimBackground() {
        AlphaAnimation animation1 = new AlphaAnimation(0.65f, ALPHA_FULLY_TRANSPARENT);
        animation1.setDuration(250);
        dimMenuBackground.setVisibility(View.INVISIBLE);
        dimMenuBackground.startAnimation(animation1);
    }

    private void displayFloatingButtonMenu() {
        AlphaAnimation animation1 = new AlphaAnimation(ALPHA_FULLY_TRANSPARENT, ALPHA_OPAQUE);
        animation1.setDuration(150);
        AlphaAnimation animation2 = new AlphaAnimation(ALPHA_FULLY_TRANSPARENT, ALPHA_OPAQUE);
        animation2.setDuration(300);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) fabCamera.getLayoutParams();
        layoutParams.bottomMargin = (int) (fabCamera.getHeight() * FAB_MENU_HEIGHT_1);
        layoutParams.rightMargin = (int) (fabCamera.getWidth() * FAB_MENU_MARGIN);
        fabCamera.setLayoutParams(layoutParams);
        fabCamera.setVisibility(View.VISIBLE);
        fabCamera.startAnimation(animation1);
        fabCamera.setClickable(true);

        FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) fabBarcode.getLayoutParams();
        layoutParams2.bottomMargin = (int) (fabBarcode.getHeight() * FAB_MENU_HEIGHT_2);
        layoutParams2.rightMargin = (int) (fabBarcode.getWidth() * FAB_MENU_MARGIN);
        fabBarcode.setLayoutParams(layoutParams2);
        fabBarcode.setVisibility(View.VISIBLE);
        fabBarcode.startAnimation(animation2);
        fabBarcode.setClickable(true);

        FrameLayout.LayoutParams layoutParamsLabel = (FrameLayout.LayoutParams) fabCameraLabel.getLayoutParams();
        layoutParamsLabel.bottomMargin = (int) (fabCamera.getHeight() * FAB_MENU_HEIGHT_1) + (int) (fabCameraLabel.getHeight() / FAB_MENU_LABEL_HEIGHT);
        layoutParamsLabel.rightMargin = (int) (fabCamera.getWidth() * FAB_MENU_LABEL_MARGIN);
        fabCameraLabel.setLayoutParams(layoutParamsLabel);
        fabCameraLabel.setVisibility(View.VISIBLE);
        fabCameraLabel.startAnimation(animation1);

        FrameLayout.LayoutParams layoutParamsLabel2 = (FrameLayout.LayoutParams) fabBarcodeLabel.getLayoutParams();
        layoutParamsLabel2.bottomMargin = (int) (fabBarcode.getHeight() * FAB_MENU_HEIGHT_2) + (int) (fabBarcodeLabel.getHeight() / FAB_MENU_LABEL_HEIGHT);
        layoutParamsLabel2.rightMargin = (int) (fabBarcode.getWidth() * FAB_MENU_LABEL_MARGIN);
        fabBarcodeLabel.setLayoutParams(layoutParamsLabel2);
        fabBarcodeLabel.setVisibility(View.VISIBLE);
        fabBarcodeLabel.startAnimation(animation2);
    }


    private void hideFloatingButtonMenu() {
        AlphaAnimation animation1 = new AlphaAnimation(ALPHA_OPAQUE, ALPHA_FULLY_TRANSPARENT);
        animation1.setDuration(150);
        AlphaAnimation animation2 = new AlphaAnimation(ALPHA_OPAQUE, ALPHA_FULLY_TRANSPARENT);
        animation2.setDuration(300);

        fabCamera.startAnimation(animation2);
        fabCamera.setVisibility(View.INVISIBLE);
        fabCamera.setClickable(false);
        fabCameraLabel.startAnimation(animation2);
        fabCameraLabel.setVisibility(View.INVISIBLE);

        fabBarcode.startAnimation(animation1);
        fabBarcode.setVisibility(View.INVISIBLE);
        fabBarcode.setClickable(false);
        fabBarcodeLabel.startAnimation(animation2);
        fabBarcodeLabel.setVisibility(View.INVISIBLE);
    }
}
