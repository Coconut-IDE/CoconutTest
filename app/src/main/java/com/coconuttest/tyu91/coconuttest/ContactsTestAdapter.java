package com.coconuttest.tyu91.coconuttest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactsTestAdapter extends RecyclerView.Adapter<ContactsTestAdapter.ViewHolder>{

    // Store a member variable for the contacts
    private ArrayList<String> mContactsResults;

    // Pass in the contact array into the constructor
    public ContactsTestAdapter(ArrayList<String> ContactsResults) {
        mContactsResults = ContactsResults;
    }

    @NonNull
    @Override
    public ContactsTestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactsItemView = inflater.inflate(R.layout.item_contacts_test, viewGroup, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactsItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsTestAdapter.ViewHolder viewHolder, int i) {
        viewHolder.tvContactsItem.setText(mContactsResults.get(i));


    }

    @Override
    public int getItemCount() {
        return mContactsResults.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvContactsItem;
        public ViewHolder(View itemView) {
            super(itemView);
            tvContactsItem = itemView.findViewById(R.id.tvContactsItem);
        }
    }

    public void clear() {
        mContactsResults.clear();
        notifyDataSetChanged();
    }
}
