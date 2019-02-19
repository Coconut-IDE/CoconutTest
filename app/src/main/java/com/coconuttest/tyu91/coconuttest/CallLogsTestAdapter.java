package com.coconuttest.tyu91.coconuttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CallLogsTestAdapter extends RecyclerView.Adapter<CallLogsTestAdapter.ViewHolder>{
    // Store a member variable for the contacts
    private ArrayList<String> mCallLogsResults;

    // Pass in the contact array into the constructor
    public CallLogsTestAdapter(ArrayList<String> callLogsResults) {
        mCallLogsResults = callLogsResults;
    }

    @NonNull
    @Override
    public CallLogsTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View callLogsItemView = inflater.inflate(R.layout.item_calllogs_test, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(callLogsItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CallLogsTestAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvCallLogsItem.setText(mCallLogsResults.get(i));


    }

    @Override
    public int getItemCount() {
        return mCallLogsResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvCallLogsItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCallLogsItem = itemView.findViewById(R.id.tvCallLogsItem);
        }
    }

    public void clear() {
        mCallLogsResults.clear();
        notifyDataSetChanged();
    }
}
