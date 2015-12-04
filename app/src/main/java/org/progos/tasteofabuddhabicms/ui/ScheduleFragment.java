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

        if (Utils.hasConnection(context))
            loadSchedules();

        return view;
    }

    private void loadSchedules() {

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

    /*private void parseSchedulesResponse(JSONArray response) {

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                JSONObject termsObject = jsonObject.getJSONObject("terms");
                JSONArray scheduleCatJsonArray = termsObject.getJSONArray("schedulecat");
                JSONObject scheduleCatJsonObj = scheduleCatJsonArray.getJSONObject(0);
                String scheduleId = scheduleCatJsonObj.getString("ID");
                String day = scheduleCatJsonObj.getString("name");
                JSONObject dateObj = scheduleCatJsonObj.getJSONObject("schedule_date");
                String date = dateObj.getString("date_sch");

                JSONObject postMetaDataJsonObject = jsonObject.getJSONObject("postmeta_data");

                JSONArray postMetaDataColumn1 = postMetaDataJsonObject.getJSONArray("column1");
                String[] postMetaDataColumn1Array = new String[postMetaDataColumn1.length()];
                for (int j = 0; j < postMetaDataColumn1.length(); j++) {
                    postMetaDataColumn1Array[j] = postMetaDataColumn1.getString(j);
                }
                JSONArray postMetaDataColumn2 = postMetaDataJsonObject.getJSONArray("column2");
                String[] postMetaDataColumn2Array = new String[postMetaDataColumn2.length()];
                for (int k = 0; k < postMetaDataColumn1.length(); k++) {
                    postMetaDataColumn2Array[k] = postMetaDataColumn2.getString(k);
                }

                ArrayList<ScheduleItem> scheduleItems = new ArrayList<>();
                for(int l=0; i<postMetaDataColumn1.length(); l++)
                {
                    ScheduleItem scheduleItem = new ScheduleItem(postMetaDataColumn1Array[i], postMetaDataColumn2Array[i]);
                    scheduleItems.add(scheduleItem);
                }

                Schedule schedule = new Schedule(scheduleId, day, date, scheduleItems);
                schedules.add(schedule);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }*/


    private void uInit(View view) {

        scheduleList = (RecyclerView) view.findViewById(R.id.scheduleList);
        scheduleListProgress = (ProgressBar) view.findViewById(R.id.scheduleListProgress);
    }
}
