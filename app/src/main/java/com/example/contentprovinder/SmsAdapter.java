package com.example.contentprovinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsHolder> {
    @NonNull
    private Context mContext;
    private List<Contact> mySMS ;

    public SmsAdapter(@NonNull Context context, List<Contact> mySMS) {
        this.mContext = context;
        this.mySMS = mySMS;
    }



    @Override
    public SmsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_sms, parent, false);
        return new SmsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  SmsHolder holder, final int position) {
        holder.tvName_sms.setText(mySMS.get(position).namecontact_sms);
        holder.tvPhone_contentSms.setText(mySMS.get(position).Content_sms);
    }

    @Override
    public int getItemCount() {
        if (mySMS == null) {
            return 0;
        }
        return mySMS.size();
    }

    public class SmsHolder extends RecyclerView.ViewHolder  {

        public TextView tvName_sms;
        public TextView tvPhone_contentSms;

        public SmsHolder(View itemView) {
            super(itemView);
            tvName_sms = itemView.findViewById(R.id.tv_item_sms_name_contact);
            tvPhone_contentSms = itemView.findViewById(R.id.tv_item_sms_content);
        }
    }
}
