package org.progos.tasteofabuddhabicms.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.model.RestaurantItem;
import org.progos.tasteofabuddhabicms.utility.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class RestaurantDetailsAdapter extends RecyclerView.Adapter<RestaurantDetailsViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View header;
    ArrayList<RestaurantItem> restaurantItems;

    private Context context;


    public RestaurantDetailsAdapter(Context context, View header, ArrayList<RestaurantItem> restaurantItems) {

        this.context = context;
        if (header == null) {
            throw new IllegalArgumentException("header may not be null");
        }
        this.header = header;
        this.restaurantItems = restaurantItems;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public RestaurantDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new RestaurantDetailsViewHolder(context, header);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_restaurant_details, parent, false);
        return new RestaurantDetailsViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final RestaurantDetailsViewHolder holder, final int position) {
        if (isHeader(position)) {
            return;
        }
        final RestaurantItem restaurantItem = restaurantItems.get(position - 1);
        holder.dishNameTextView.setText(restaurantItem.getTitle());
        holder.dishDetailTextView.setText(restaurantItem.getDescription());
        holder.priceTextView.setText(restaurantItem.getPrice());

        if (restaurantItem.getDescription().isEmpty())
            holder.dishDetailTextView.setVisibility(View.GONE);
        if (restaurantItem.getPrice().isEmpty())
            holder.priceTextView.setVisibility(View.GONE);
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return restaurantItems.size() + 1;
    }
}
