package com.example.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.example.ering.trekinsync.viewholders.AddRowIconTitleCellViewHolder;

public class AddRowIconTitleCellDataBinder extends BaseDataBinder<AddRowIconTitleCellViewHolder> {
    private String title;
    private View.OnClickListener listener;

    public AddRowIconTitleCellDataBinder(String title, View.OnClickListener listener) {
        this.title = title;
        this.listener = listener;
    }
    @Override
    public AddRowIconTitleCellViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(AddRowIconTitleCellViewHolder.getLayoutId(), parent);
        view.setOnClickListener(listener);
        return new AddRowIconTitleCellViewHolder(view);
    }

    @Override
    public void bindViewHolder(AddRowIconTitleCellViewHolder holder) {
        holder.setTitle(title);
    }
}

