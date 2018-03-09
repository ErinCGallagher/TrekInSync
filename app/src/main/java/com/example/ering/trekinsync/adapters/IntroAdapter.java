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
                return IntroFragment.newInstance(Color.parseColor("#3B5B8A"), position);
            case 1:
                return IntroFragment.newInstance(Color.parseColor("#779F90"), position);
            case 2:
                return IntroFragment.newInstance(Color.parseColor("#BDDADA"), position);
            default:
                return IntroFragment.newInstance(Color.parseColor("#FCE972"), position);
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
