package com.coconuttest.tyu91.coconuttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CalendarTestAdapter extends RecyclerView.Adapter<CalendarTestAdapter.ViewHolder>{

    // Store a member variable for the contacts
    private ArrayList<String> mCalendarResults;

    // Pass in the contact array into the constructor
    public CalendarTestAdapter(ArrayList<String> calendarResults) {
        mCalendarResults = calendarResults;
    }

    @NonNull
    @Override
    public CalendarTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View calendarItemView = inflater.inflate(R.layout.item_calendar_test, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(calendarItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarTestAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvCalendarItem.setText(mCalendarResults.get(i));

    }

    @Override
    public int getItemCount() {
        return mCalendarResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvCalendarItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCalendarItem = itemView.findViewById(R.id.tvCalendarItem);
        }
    }

    public void clear() {
        mCalendarResults.clear();
        notifyDataSetChanged();
    }
}
