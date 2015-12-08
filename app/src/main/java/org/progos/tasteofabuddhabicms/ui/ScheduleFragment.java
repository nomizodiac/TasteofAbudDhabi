package org.progos.tasteofabuddhabicms.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import org.progos.tasteofabuddhabicms.adapters.ScheduleAdapter;
import org.progos.tasteofabuddhabicms.model.Restaurant;
import org.progos.tasteofabuddhabicms.model.Schedule;
import org.progos.tasteofabuddhabicms.model.ScheduleItem;
import org.progos.tasteofabuddhabicms.utility.Commons;
import org.progos.tasteofabuddhabicms.utility.FontFactory;
import org.progos.tasteofabuddhabicms.utility.Utils;
import org.progos.tasteofabuddhabicms.webservices.Urls;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class ScheduleFragment extends Fragment {

    private final String TAG = getClass().getSimpleName();

    Context context;
    RecyclerView scheduleList;
    ArrayList<Schedule> schedules;
    ScheduleAdapter adapter;
    ProgressBar scheduleListProgress;
    RelativeLayout connectionLostLayout;
    TextView connectionLostTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        uInit(view);
        schedules = new ArrayList<>();

        //restaurantsList.addItemDecoration(new MarginDecoration(this));
        scheduleList.setHasFixedSize(true);
        scheduleList.setLayoutManager(new LinearLayoutManager(context));

        View header = LayoutInflater.from(context).inflate(R.layout.header_list_schedule, scheduleList, false);
        TextView scheduleHeadingLbl = (TextView) header.findViewById(R.id.scheduleHeadingLbl);
        scheduleHeadingLbl.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_SEMI_BOLD));

        adapter = new ScheduleAdapter(context, header, schedules);
        scheduleList.setAdapter(adapter);

        connectionLostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadSchedules();
            }
        });
        loadSchedules();

        return view;
    }

    private void loadSchedules() {

        if (!Utils.hasConnection(context)) {
            scheduleListProgress.setVisibility(View.GONE);
            scheduleList.setVisibility(View.GONE);
            connectionLostLayout.setVisibility(View.VISIBLE);
        } else {
            connectionLostLayout.setVisibility(View.GONE);
            scheduleListProgress.setVisibility(View.VISIBLE);

            String url = "taxonomies/schedulecat/terms";
            JsonArrayRequest req = new JsonArrayRequest(Urls.base_url + url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d(TAG, "Response-Restaurants: " + response.toString());
                    parseSchedulesResponse(response);
                    adapter.notifyDataSetChanged();
                    scheduleListProgress.setVisibility(View.GONE);
                    scheduleList.setVisibility(View.VISIBLE);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                }
            });

            AppController.getInstance().addToRequestQueue(req);
        }


    }

    private void parseSchedulesResponse(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String scheduleId = jsonObject.getString("ID");
                String slug = jsonObject.getString("slug");
                String day = jsonObject.getString("name");
                JSONObject dateObj = jsonObject.getJSONObject("schedule_date");
                String date = dateObj.getString("date_sch");
                Schedule schedule = new Schedule(scheduleId, slug, day, date);
                schedules.add(schedule);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void uInit(View view) {

        scheduleList = (RecyclerView) view.findViewById(R.id.scheduleList);
        scheduleListProgress = (ProgressBar) view.findViewById(R.id.scheduleListProgress);
        connectionLostLayout = (RelativeLayout) view.findViewById(R.id.connectionLostLayout);
        connectionLostTextView = (TextView) view.findViewById(R.id.connectionLostTextView);
        connectionLostTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_REGULAR));
    }
}
