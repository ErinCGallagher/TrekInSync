package com.trekinsync.ering.trekinsync.databinders;

import android.view.View;
import android.view.ViewGroup;

import com.trekinsync.ering.trekinsync.viewholders.SectionDividerTitleViewHolder;

public class SectionDividerTitleRowBinder extends BaseDataBinder<SectionDividerTitleViewHolder> {
    private String title;

    public SectionDividerTitleRowBinder(String title) {
        this.title = title;
    }
    @Override
    public SectionDividerTitleViewHolder createViewHolder(ViewGroup parent) {
        View view = getView(SectionDividerTitleViewHolder.getLayoutId(), parent);
        return new SectionDividerTitleViewHolder(view);
    }

    @Override
    public void bindViewHolder(SectionDividerTitleViewHolder holder) {
        holder.setTitle(title);
    }
}
