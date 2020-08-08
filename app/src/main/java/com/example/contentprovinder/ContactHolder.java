package com.example.contentprovinder;


import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContactHolder extends RecyclerView.ViewHolder {
    public TextView tv_id;
    public final TextView tvName;
    public final TextView tvPhone;
    public LinearLayout llItemContact;
    public ContactHolder(View convertView) {
        super(convertView);
        tvName = convertView.findViewById(R.id.tv_item_contact_name);
        tvPhone = convertView.findViewById(R.id.tv_item_contact_phone_number);
        tv_id = itemView.findViewById(R.id.tv_item_contact_id);
        llItemContact = itemView.findViewById(R.id.ll_item_contact);
    }

}
