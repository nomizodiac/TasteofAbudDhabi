package org.progos.tasteofabuddhabicms.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.progos.tasteofabuddhabicms.R;
import org.progos.tasteofabuddhabicms.adapters.RestaurantDetailsAdapter;
import org.progos.tasteofabuddhabicms.adapters.ScheduleDetailsAdapter;

/**
 * Created by NomBhatti on 11/25/2015.
 */
public class ScheduleDetailsFragment extends Fragment {

    Context context;
    RecyclerView scheduleDetailsList;
    ProgressBar scheduleListProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        uInit(view);


        //restaurantsList.addItemDecoration(new MarginDecoration(this));
        scheduleDetailsList.setHasFixedSize(true);
        scheduleDetailsList.setLayoutManager(new LinearLayoutManager(context));

        View header = LayoutInflater.from(context).inflate(R.layout.header_list_schedule_details, scheduleDetailsList, false);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "grid_layout_header", Toast.LENGTH_SHORT).show();
            }
        });

        final ScheduleDetailsAdapter adapter = new ScheduleDetailsAdapter(context, header, 30);
        scheduleDetailsList.setAdapter(adapter);

        scheduleListProgress.setVisibility(View.GONE);
        scheduleDetailsList.setVisibility(View.VISIBLE);

        return view;
    }

    private void uInit(View view) {

        scheduleDetailsList = (RecyclerView) view.findViewById(R.id.scheduleList);
        scheduleListProgress = (ProgressBar) view.findViewById(R.id.scheduleListProgress);

    }
}
