package org.progos.tasteofabuddhabicms.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.progos.tasteofabuddhabicms.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class RestaurantDetailsAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View header;
    private final List<String> labels;

    private Context context;

    public RestaurantDetailsAdapter(Context context, View header, int count) {

        this.context = context;
        if (header == null) {
            throw new IllegalArgumentException("header may not be null");
        }
        this.header = header;
        this.labels = new ArrayList<String>(count);
        for (int i = 0; i < count; ++i) {
            labels.add(String.valueOf(i));
        }
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public ScheduleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new ScheduleViewHolder(header);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_restaurant_details, parent, false);
        return new ScheduleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ScheduleViewHolder holder, final int position) {
        if (isHeader(position)) {
            return;
        }
        final String label = labels.get(position - 1);  // Subtract 1 for header
        /*holder..setText(label);
        Drawable drawable = context.getResources().getDrawable(context.getResources().getIdentifier("rest_01", "drawable", context.getPackageName()));
        Utils.setBackground(holder.chefImg, drawable);*/
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return labels.size() + 1;
    }


}
