package com.coconuttest.tyu91.coconuttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SmsTestAdapter extends RecyclerView.Adapter<SmsTestAdapter.ViewHolder>{

    // Store a member variable for the contacts
    private ArrayList<String> mSmsResults;

    // Pass in the contact array into the constructor
    public SmsTestAdapter(ArrayList<String> SmsResults) {
        mSmsResults = SmsResults;
    }

    @NonNull
    @Override
    public SmsTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View smsItemView = inflater.inflate(R.layout.item_sms_test, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(smsItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmsTestAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvSmsItem.setText(mSmsResults.get(i));


    }

    @Override
    public int getItemCount() {
        return mSmsResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvSmsItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSmsItem = itemView.findViewById(R.id.tvSmsItem);
        }
    }

    public void clear() {
        mSmsResults.clear();
        notifyDataSetChanged();
    }
}
