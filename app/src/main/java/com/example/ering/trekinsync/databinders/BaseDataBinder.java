package com.example.ering.trekinsync.databinders;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseDataBinder <T extends RecyclerView.ViewHolder> {

    /**
     *
     * @param layoutId, represents the viewType
     * @param parent, parent viewGroup
     * @return View
     */
    public View getView(@LayoutRes int layoutId, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    /**s
     * returns the viewType as an integer
     * @return
     */
    public int getViewType() {
        return this.getClass().getSimpleName().hashCode();
    }

    /**
     * Abstract method that creates a viewHolder of the given type
     * @param parent
     * @return
     */
    abstract public T createViewHolder(ViewGroup parent);

    /**
     * Binds the holder provided
     * @param holder, viewHolder of type T
     */
    abstract public void bindViewHolder(T holder);

}
