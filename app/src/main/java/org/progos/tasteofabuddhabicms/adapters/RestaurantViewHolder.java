package org.progos.tasteofabuddhabicms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.utility.SquareImageView;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class RestaurantViewHolder extends RecyclerView.ViewHolder {
    public SquareImageView restaurantImg;

    public RestaurantViewHolder(View itemView) {
        super(itemView);
        restaurantImg = (SquareImageView) itemView.findViewById(R.id.restaurantImg);
    }
}
