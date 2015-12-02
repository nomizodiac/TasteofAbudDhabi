package org.progos.tasteofabuddhabicms.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.adapters.ChefsAdapter;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class ChefDetailsFragment extends Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_chef_details, container, false);
        uInit(view);
        return view;
    }


    private void uInit(View view) {


    }
}
