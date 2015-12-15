package org.progos.tasteofabuddhabicms.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.progos.tasteofabuddhabicms.AppController;
import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.adapters.ScheduleDetailsAdapter;
import org.progos.tasteofabuddhabicms.model.Restaurant;
import org.progos.tasteofabuddhabicms.model.Schedule;
import org.progos.tasteofabuddhabicms.model.ScheduleItem;
import org.progos.tasteofabuddhabicms.sqlite.DataBaseHelper;
import org.progos.tasteofabuddhabicms.utility.Commons;
import org.progos.tasteofabuddhabicms.utility.FontFactory;
import org.progos.tasteofabuddhabicms.utility.Strings;
import org.progos.tasteofabuddhabicms.utility.Utils;
import org.progos.tasteofabuddhabicms.webservices.Urls;

import java.util.ArrayList;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class ScheduleDetailsFragment extends Fragment {

    Context context;
    RecyclerView scheduleDetailsList;
    ProgressBar scheduleListProgress;
    ScheduleDetailsAdapter adapter;
    ArrayList<ScheduleItem> scheduleItems;
    RelativeLayout connectionLostLayout;
    TextView connectionLostTextView;
    Schedule schedule;

    private final String TAG = getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        uInit(view);

        schedule = (Schedule) getArguments().getSerializable(Strings.SCHEDULE_OBJ);
        scheduleItems = new ArrayList<>();
        //restaurantsList.addItemDecoration(new MarginDecoration(this));
        scheduleDetailsList.setHasFixedSize(true);
        scheduleDetailsList.setLayoutManager(new LinearLayoutManager(context));

        View header = LayoutInflater.from(context).inflate(R.layout.header_list_schedule_details, scheduleDetailsList, false);
        TextView dayTextView = (TextView) header.findViewById(R.id.dayTextView);
        TextView dateTextView = (TextView) header.findViewById(R.id.dateTextView);
        dayTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_SEMI_BOLD));
        dateTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_SEMI_BOLD));


        dayTextView.setText(schedule.getDay());
        dateTextView.setText(schedule.getDate());

        adapter = new ScheduleDetailsAdapter(context, header, scheduleItems);
        scheduleDetailsList.setAdapter(adapter);
        connectionLostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadScheduleDetails(schedule.getSlug());
            }
        });

        loadScheduleDetails(schedule.getSlug());

        return view;
    }

    private void loadScheduleDetails(String slug) {

        if (!Utils.hasConnection(context)) {

            ArrayList<ScheduleItem> scheduleItemArrayList = DataBaseHelper.getInstance(context).getScheduleItems(schedule);
            if (scheduleItemArrayList != null && !scheduleItemArrayList.isEmpty()) {
                scheduleItems.addAll(scheduleItemArrayList);
                adapter.notifyDataSetChanged();
                scheduleListProgress.setVisibility(View.GONE);
                scheduleDetailsList.setVisibility(View.VISIBLE);
            } else {
                scheduleListProgress.setVisibility(View.GONE);
                scheduleDetailsList.setVisibility(View.GONE);
                connectionLostLayout.setVisibility(View.VISIBLE);
            }

        } else {
            connectionLostLayout.setVisibility(View.GONE);
            scheduleListProgress.setVisibility(View.VISIBLE);
            String url = "posts?type[]=schedules&filter[taxonomy]=schedulecat&filter[term]=" + slug;
            JsonArrayRequest req = new JsonArrayRequest(Urls.base_url + url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(final JSONArray response) {
                    Log.d(TAG, "Response-ScheduleDetails: " + response.toString());
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            parseScheduleDetailsResponse(response);
                            Activity activity = (Activity) context;
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    scheduleListProgress.setVisibility(View.GONE);
                                    scheduleDetailsList.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                    thread.start();
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

    private void parseScheduleDetailsResponse(JSONArray response) {

        scheduleItems.clear();
        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                JSONObject postMetaDataJsonObject = jsonObject.getJSONObject("postmeta_data");

                JSONArray postMetaDataColumn1 = postMetaDataJsonObject.getJSONArray("column1");
                String[] postMetaDataColumn1Array = new String[postMetaDataColumn1.length()];
                for (int j = 0; j < postMetaDataColumn1.length(); j++) {
                    postMetaDataColumn1Array[j] = postMetaDataColumn1.getString(j);
                }
                JSONArray postMetaDataColumn2 = postMetaDataJsonObject.getJSONArray("column2");
                String[] postMetaDataColumn2Array = new String[postMetaDataColumn2.length()];
                for (int k = 0; k < postMetaDataColumn2.length(); k++) {
                    postMetaDataColumn2Array[k] = postMetaDataColumn2.getString(k);
                }

                for (int l = 0; l < postMetaDataColumn1.length(); l++) {
                    ScheduleItem scheduleItem = new ScheduleItem(postMetaDataColumn1Array[l], postMetaDataColumn2Array[l], schedule.getId());
                    scheduleItems.add(scheduleItem);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (scheduleItems != null && !scheduleItems.isEmpty()) {
            DataBaseHelper.getInstance(context).addScheduleItems(scheduleItems);
        }

    }

    private void uInit(View view) {

        scheduleDetailsList = (RecyclerView) view.findViewById(R.id.scheduleList);
        scheduleListProgress = (ProgressBar) view.findViewById(R.id.scheduleListProgress);
        connectionLostLayout = (RelativeLayout) view.findViewById(R.id.connectionLostLayout);
        connectionLostTextView = (TextView) view.findViewById(R.id.connectionLostTextView);
        connectionLostTextView.setTypeface(FontFactory.getInstance().getFont(context, Commons.FONT_RALEWAY_REGULAR));
    }
}
