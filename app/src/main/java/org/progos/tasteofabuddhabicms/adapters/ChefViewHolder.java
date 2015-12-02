package org.progos.tasteofabuddhabicms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.utility.SquareImageView;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class ChefViewHolder extends RecyclerView.ViewHolder {

    public SquareImageView chefImg;
    public TextView chefName;

    public ChefViewHolder(View itemView) {
        super(itemView);
        chefImg = (SquareImageView) itemView.findViewById(R.id.chefImg);
        chefName = (TextView) itemView.findViewById(R.id.chefName);
    }
}
