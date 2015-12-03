package org.progos.tasteofabuddhabicms.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.progos.tasteofabuddhabicms.ui.ChefDetailsFragment;
import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.model.Chef;
import org.progos.tasteofabuddhabicms.ui.ChefsFragment;
import org.progos.tasteofabuddhabicms.ui.RestaurantsFragment;
import org.progos.tasteofabuddhabicms.utility.Strings;

import java.util.ArrayList;

/**
 * Created by NomBhatti on 11/26/2015.
 */
public class ChefsAdapter extends RecyclerView.Adapter<ChefViewHolder> {

    private static final int ITEM_VIEW_TYPE_HEADER = 0;
    private static final int ITEM_VIEW_TYPE_ITEM = 1;

    private final View header;
    ArrayList<Chef> chefs;

    private Context context;

    public ChefsAdapter(Context context, View header, ArrayList<Chef> chefs) {

        this.context = context;
        if (header == null) {
            throw new IllegalArgumentException("header may not be null");
        }
        this.header = header;
        this.chefs = chefs;
    }

    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public ChefViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new ChefViewHolder(context, header);
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_chefs, parent, false);
        return new ChefViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(final ChefViewHolder holder, final int position) {
        if (isHeader(position)) {
            return;
        }
        final Chef chef = chefs.get(position - 1);  // Subtract 1 for header
        holder.chefName.setText(chef.getName());
        Picasso.with(context).load(chef.getImageUrl()).into(holder.chefImg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context instanceof FragmentActivity) {
                    FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    Fragment fragment = fm.findFragmentByTag(Strings.FRAGMENT_CHEFS);
                    if (fragment instanceof ChefsFragment) {
                        ft.hide(fragment);
                        ft.add(R.id.fragmentsContainerLayout, new ChefDetailsFragment());
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return isHeader(position) ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return chefs.size() + 1;
    }


}
