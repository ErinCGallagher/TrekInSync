package com.trekinsync.ering.trekinsync.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.trekinsync.ering.trekinsync.R;

import java.util.List;

public class IconSelectionAdapter extends ArrayAdapter<Drawable> {
    private final List<Drawable> list;
    private final Context context;
    private final int selected;

    static class ViewHolder {
        protected ImageView icon;
        protected ImageView selectedIcon;
    }


    public IconSelectionAdapter(Context context, List<Drawable> list, int selected) {
        super(context, R.layout.custom_icon_spinner_item, list);
        this.context = context;
        this.list = list;
        this.selected = selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.custom_icon_spinner_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.icon = view.findViewById(R.id.icon);
            viewHolder.selectedIcon = view.findViewById(R.id.selected);
            if (position == selected) {
                viewHolder.selectedIcon.setVisibility(View.VISIBLE);
            } else{
                viewHolder.selectedIcon.setVisibility(View.INVISIBLE);
            }
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.icon.setImageDrawable(list.get(position));
        return view;
    }
}
