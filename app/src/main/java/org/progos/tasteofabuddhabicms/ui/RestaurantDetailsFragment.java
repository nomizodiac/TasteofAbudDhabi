package org.progos.tasteofabuddhabicms.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.progos.tasteofabuddhabicms.AppController;
import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.adapters.RestaurantDetailsAdapter;
import org.progos.tasteofabuddhabicms.adapters.ScheduleAdapter;
import org.progos.tasteofabuddhabicms.utility.Commons;
import org.progos.tasteofabuddhabicms.utility.Strings;
import org.progos.tasteofabuddhabicms.utility.Utils;
import org.progos.tasteofabuddhabicms.webservices.Urls;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class RestaurantDetailsFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    Context context;
    RecyclerView restaurantDetailsList;
    ProgressBar restaurantsListProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);
        uInit(view);

        String restaurantId = getArguments().getString(Strings.RESTAURANT_ID);

        //restaurantsList.addItemDecoration(new MarginDecoration(this));
        restaurantDetailsList.setHasFixedSize(true);
        restaurantDetailsList.setLayoutManager(new LinearLayoutManager(context));

        View header = LayoutInflater.from(context).inflate(R.layout.header_list_restaurant_details, restaurantDetailsList, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "grid_layout_header", Toast.LENGTH_SHORT).show();
            }
        });

        final RestaurantDetailsAdapter adapter = new RestaurantDetailsAdapter(context, header, 30);
        restaurantDetailsList.setAdapter(adapter);

        if (Utils.hasConnection(context))
            loadRestaurantDetails(restaurantId);

        return view;
    }

    private void loadRestaurantDetails(String restaurantId) {

        Log.d(TAG, Strings.RESTAURANT_ID + " :" + restaurantId);
        String url = Urls.base_url + "posts/" + restaurantId + "/meta";

        RestaurantsDetailsRequest jsonObjReq = new RestaurantsDetailsRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Response-RestaurantDetails: " + response.toString());
                restaurantsListProgress.setVisibility(View.GONE);
                restaurantDetailsList.setVisibility(View.VISIBLE);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public class RestaurantsDetailsRequest extends JsonObjectRequest {

        public RestaurantsDetailsRequest(int method, String url, JSONObject jsonRequest,
                                         Response.Listener<JSONObject> listener,
                                         Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        public RestaurantsDetailsRequest(String url, JSONObject jsonRequest,
                                         Response.Listener<JSONObject> listener,
                                         Response.ErrorListener errorListener) {
            super(url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            return createBasicAuthHeader(Commons.AUTH_USERNAME, Commons.AUTH_PASSWORD);
        }

        Map<String, String> createBasicAuthHeader(String username, String password) {
            Map<String, String> headerMap = new HashMap<>();

            String credentials = username + ":" + password;
            String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            headerMap.put("Authorization", "Basic " + encodedCredentials);

            return headerMap;
        }
    }

    private void uInit(View view) {

        restaurantDetailsList = (RecyclerView) view.findViewById(R.id.restaurantsList);
        restaurantsListProgress = (ProgressBar) view.findViewById(R.id.restaurantsListProgress);

    }
}
