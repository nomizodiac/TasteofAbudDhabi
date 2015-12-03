package org.progos.tasteofabuddhabicms.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.utility.Commons;
import org.progos.tasteofabuddhabicms.utility.FontFactory;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class RestaurantDetailsViewHolder extends RecyclerView.ViewHolder {

    Context context;
    public TextView dishNameTextView, dishDetailTextView;

    public RestaurantDetailsViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        dishNameTextView = (TextView) itemView.findViewById(R.id.dishNameTextView);
        dishDetailTextView = (TextView) itemView.findViewById(R.id.dishDetailTextView);

        if (dishNameTextView != null)
            dishNameTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_SEMI_BOLD));
        if (dishDetailTextView != null)
            dishDetailTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_SEMI_BOLD));
    }
}
