package com.example.ering.trekinsync.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.R;

public class IntroFragment extends android.support.v4.app.Fragment {

    private static final String BACKGROUND_COLOUR = "backgroundColor";
    private static final String PAGE = "page";

    private int backgroundColour;
    private int page;

    public static IntroFragment newInstance(int backgroundColor, int page) {
        IntroFragment fragment = new IntroFragment();
        Bundle b = new Bundle();
        b.putInt(BACKGROUND_COLOUR, backgroundColor);
        b.putInt(PAGE, page);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getArguments().containsKey(BACKGROUND_COLOUR))
            throw new RuntimeException("Fragment must contain a background argument!");
        backgroundColour = getArguments().getInt(BACKGROUND_COLOUR);

        if (!getArguments().containsKey(PAGE))
            throw new RuntimeException("Fragment must contain a page argument!");
        page = getArguments().getInt(PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //display layout based on page
        int layoutResId;
        switch (page) {
            case 0:
                layoutResId = R.layout.intro_frag_1;
                break;
            case 1:
                layoutResId = R.layout.intro_frag_2;
                break;
            case 2:
                layoutResId = R.layout.intro_frag_3;
                break;
            default:
                layoutResId = R.layout.intro_frag_4;
        }

        View view = getActivity().getLayoutInflater().inflate(layoutResId, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View background = view.findViewById(R.id.intro_background);
        background.setBackgroundColor(backgroundColour);
    }
}
