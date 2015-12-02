package org.progos.tasteofabuddhabicms.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.progos.tasteofabuddhabicms.AppController;
import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.adapters.ChefsAdapter;
import org.progos.tasteofabuddhabicms.model.Chef;
import org.progos.tasteofabuddhabicms.model.Restaurant;
import org.progos.tasteofabuddhabicms.utility.Utils;
import org.progos.tasteofabuddhabicms.webservices.Urls;

import java.util.ArrayList;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class ChefsFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    Context context;
    RecyclerView chefsList;
    ChefsAdapter adapter;
    ArrayList<Chef> chefs;
    ProgressBar chefsListProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_chefs, container, false);
        uInit(view);

        chefs = new ArrayList<>();
        //restaurantsList.addItemDecoration(new MarginDecoration(this));
        chefsList.setHasFixedSize(true);
        final GridLayoutManager manager = new GridLayoutManager(context, 2);
        chefsList.setLayoutManager(manager);

        View header = LayoutInflater.from(context).inflate(R.layout.header_list_chefs, chefsList, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "grid_layout_header", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new ChefsAdapter(context, header, chefs);
        chefsList.setAdapter(adapter);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return adapter.isHeader(position) ? manager.getSpanCount() : 1;
            }
        });

        if (Utils.hasConnection(context))
            loadChefs();

        return view;
    }

    private void loadChefs() {

        String url = "posts?type[]=chefs";

        JsonArrayRequest req = new JsonArrayRequest(Urls.base_url + url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(TAG, "Response-Restaurants: " + response.toString());
                parseChefsResponse(response);
                adapter.notifyDataSetChanged();
                chefsListProgress.setVisibility(View.GONE);
                chefsList.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(req);
    }

    private void parseChefsResponse(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String chefId = jsonObject.getString("ID");
                String chefName = jsonObject.getString("title");
                JSONObject imageJsonObj = jsonObject.getJSONObject("featured_image");
                String imageUrl = imageJsonObj.getString("guid");

                Chef chef = new Chef(chefId, chefName, imageUrl);
                chefs.add(chef);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    private void uInit(View view) {

        chefsList = (RecyclerView) view.findViewById(R.id.chefsList);
        chefsListProgress = (ProgressBar) view.findViewById(R.id.chefsListProgress);

    }
}
