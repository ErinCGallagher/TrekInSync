package com.trekinsync.ering.trekinsync.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.interfaces.DataInputListener;

public class OpenFieldWithLabelView extends LinearLayout {
    private EditText openTextField;
    private TextView label;

    public OpenFieldWithLabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(final Context context, AttributeSet attrs) {
        View container = LayoutInflater.from(context).inflate(R.layout.open_text_with_label_cell, this);
        openTextField = container.findViewById(R.id.description);
        label = container.findViewById(R.id.label);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.OpenFieldWithLabelView, 0, 0);
            try {
                setOpenText(array.getString(R.styleable.OpenFieldWithLabelView_viewOpenTextDescription));
                setLabel(array.getString(R.styleable.OpenFieldWithLabelView_viewOpenTextLabel));
            } finally {
                array.recycle();
            }
        }
    }

    public void setOpenText(String text) {
        openTextField.setText(text.trim());
    }

    public void setLabel(String label) {
        this.label.setText(label);
    }

    public void setListener(final DataInputListener<String> listener) {
        openTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                listener.onInputReceived(openTextField.getText().toString().trim());
            }
        });
    }
}
