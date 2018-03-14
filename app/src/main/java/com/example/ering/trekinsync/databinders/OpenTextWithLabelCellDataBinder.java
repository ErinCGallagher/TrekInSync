package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.customviews.OpenFieldWithLabelView;
import com.example.ering.trekinsync.interfaces.DataInputListener;
import com.example.ering.trekinsync.viewholders.OpenTextWithLabelCellViewHolder;

public class OpenTextWithLabelCellDataBinder extends BaseDataBinder<OpenTextWithLabelCellViewHolder>  {
    private String label;
    private String openText;
    private  DataInputListener<String> listener;

    public OpenTextWithLabelCellDataBinder(String label, String openText, DataInputListener<String> listener) {
        this.label = label;
        this.openText = openText;
        this.listener = listener;
    }

    @Override
    public OpenTextWithLabelCellViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(OpenTextWithLabelCellViewHolder.getLayoutId(), parent);
        return new OpenTextWithLabelCellViewHolder(view);
    }

    @Override
    public void bindViewHolder(OpenTextWithLabelCellViewHolder holder) {
        final OpenFieldWithLabelView labelDescriptionView = holder.openTextView;
        labelDescriptionView.setLabel(label);
        labelDescriptionView.setOpenText(openText);
        labelDescriptionView.setListener(listener);
    }
}
