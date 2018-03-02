package com.example.ering.trekinsync.adapters;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ering.trekinsync.fragments.IntroFragment;

public class IntroAdapter extends FragmentPagerAdapter {

    public IntroAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return IntroFragment.newInstance(Color.parseColor("#02d7f4"), position);
            case 1:
                return IntroFragment.newInstance(Color.parseColor("#03A9F4"), position);
            case 2:
                return IntroFragment.newInstance(Color.parseColor("#026ef4"), position);
            default:
                return IntroFragment.newInstance(Color.parseColor("#0252f4"), position);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
