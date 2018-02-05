package com.example.ering.trekinsync;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * A data binder adapter base class.
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).createViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        getItems().get(position).bindViewHolder(holder);
    }

    @Override
    public int getItemCount() {
        return getItems().size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItems().get(position).getViewType();
    }

    private <T extends BaseDataBinder> T getDataBinder(int viewType) {
        for (BaseDataBinder binder: getItems()) {
            if (binder.getViewType() == viewType) {
                return (T) binder;
            }
        }
        return null;
    }

    public abstract List<BaseDataBinder> getItems();
}
