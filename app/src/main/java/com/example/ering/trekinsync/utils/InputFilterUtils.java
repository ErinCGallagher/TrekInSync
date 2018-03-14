package com.example.ering.trekinsync.utils;

import android.text.InputFilter;
import android.text.Spanned;

public class InputFilterUtils {

    private InputFilterUtils() { /* cannot be instantiated */ }

    //filter name field with just letters, multi-language support
    public static InputFilter getAlphaFilter() {
        return new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                StringBuilder builder = new StringBuilder();
                for (int i = start; i < end; i++) {
                    char c = source.charAt(i);
                    if (Character.isLetter(c) || Character.isSpaceChar(c)) {
                        builder.append(c);
                    }
                }
                boolean charactersValid = (builder.length() == end - start);
                return charactersValid ? null : builder.toString();
            }
        };
    }
}
