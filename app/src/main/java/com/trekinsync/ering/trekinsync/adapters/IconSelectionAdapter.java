package com.trekinsync.ering.trekinsync.adapters;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.trekinsync.ering.trekinsync.R;
import com.trekinsync.ering.trekinsync.models.IconModel;

import java.util.List;

public class IconSelectionAdapter extends ArrayAdapter<IconModel> {
    private final List<IconModel> list;
    private final Context context;
    private final int selected;

    static class ViewHolder {
        protected ImageView icon;
        protected ImageView selectedIcon;
    }


    public IconSelectionAdapter(Context context, List<IconModel> list, int selected) {
        super(context, R.layout.custom_icon_spinner_item, list);
        this.context = context;
        this.list = list;
        this.selected = selected;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
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
        @DrawableRes int drawable = list.get(position).getDrawable();
        holder.icon.setImageDrawable(context.getResources().getDrawable(drawable));
        return view;
    }
}
