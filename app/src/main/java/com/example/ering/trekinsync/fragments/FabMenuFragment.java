package com.example.ering.trekinsync.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ering.trekinsync.R;
import com.example.ering.trekinsync.activities.DisplayQrCodeActivity;
import com.example.ering.trekinsync.activities.QrScannerActivity;
import com.example.ering.trekinsync.models.User;

/**
 * Fragment that manages the Floating Action Button menu logic.
 */
public class FabMenuFragment extends android.support.v4.app.Fragment {

    private FloatingActionButton fab;
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

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fab_menu_layout, container, false);

        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fabCamera = (FloatingActionButton) view.findViewById(R.id.fab_camera);
        fabBarcode = (FloatingActionButton) view.findViewById(R.id.fab_barcode);
        fabCameraLabel = (TextView) view.findViewById(R.id.fab_camera_label);
        fabBarcodeLabel = (TextView) view.findViewById(R.id.fab_barcode_label);
        dimMenuBackground = (View) view.findViewById(R.id.dim_menu_background);

        getUserBundle();
        initFabMenu();
        return view;
    }

    @Override
    public void onStop() {
        hideFabMenu();
        super.onStop();
    }

    private void getUserBundle() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = bundle.getParcelable("UserObj");
        }
    }

    private void initFabMenu() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!floatingButtonMenuState) {
                    displayFabMenu();
                } else {
                    hideFabMenu();
                }
            }
        });

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: use startActivityForResult()
                Intent intent = new Intent(getActivity(), QrScannerActivity.class);
                startActivity(intent);
            }
        });

        fabBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: if user = null, display error
                if (user != null) {
                    Intent intent = new Intent(getActivity(), DisplayQrCodeActivity.class);
                    intent.putExtra("UserObj", user);
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Display Fab Menu logic for dim background and menu items.
     */
    private void displayFabMenu() {
        displayDimBackground();
        displayFloatingButtonMenu();
        fab.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
        floatingButtonMenuState = !floatingButtonMenuState;
    }

    /**
     * Hide Fab Menu logic for dim background and menu items.
     */
    private void hideFabMenu() {
        hideDimBackground();
        fab.setImageResource(R.mipmap.ic_person_add_white_24dp);
        hideFloatingButtonMenu();
        floatingButtonMenuState = !floatingButtonMenuState;
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
