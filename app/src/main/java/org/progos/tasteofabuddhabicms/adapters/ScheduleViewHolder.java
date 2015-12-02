package org.progos.tasteofabuddhabicms.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.utility.SquareImageView;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class ScheduleViewHolder extends RecyclerView.ViewHolder {

    public TextView dayTextView, dateTextView;

    public ScheduleViewHolder(View itemView) {
        super(itemView);
        dayTextView = (TextView) itemView.findViewById(R.id.dayTextView);
        dateTextView = (TextView) itemView.findViewById(R.id.dateTextView);
    }
}
