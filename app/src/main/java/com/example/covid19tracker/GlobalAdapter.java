package com.example.covid19tracker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.Color.rgb;

public class GlobalAdapter extends RecyclerView.Adapter<GlobalAdapter.ViewHolder> {
    final String TAG = "Debug";
    GlobalValuesInterface activity;

    public interface GlobalValuesInterface {
        void onItemClicked(int index);
    }

    ArrayList<GlobalValues> globalList;

    GlobalAdapter (Context context, ArrayList<GlobalValues> list){
        globalList = list;
        activity = (GlobalValuesInterface) context;
    }

    // In this class we declare all the components that are dynamically changing depending on each Person
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvCase, tvNum, tvRate, tvLastUpdate;
        Button bntSeeAllCountries;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvCase = itemView.findViewById(R.id.tvCase);
            tvNum = itemView.findViewById(R.id.tvNum);
            tvRate = itemView.findViewById(R.id.tvRate);
            tvLastUpdate = itemView.findViewById(R.id.tvLastUpdate);
            bntSeeAllCountries = itemView.findViewById(R.id.btnSeeAllCountries);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.onItemClicked(globalList.indexOf((GlobalValues) v.getTag()));
                }
            });
        }
    }

    // This will get the custom "Row" layout that we have created
    @NonNull
    @Override
    public GlobalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.global_row_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GlobalAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(globalList.get(position));
        holder.tvCase.setText(globalList.get(position).getGlobalCase());
        holder.tvNum.setText(String.valueOf(globalList.get(position).getCaseNum()));
        holder.tvRate.setText(String.valueOf(globalList.get(position).getCaseRate()) + "%");
        holder.tvRate.setTextColor(rgb(255, 255, 255));
        holder.tvLastUpdate.setText("Last Update: " + ApplicationClass.lastUpdate);

        String Case = globalList.get(position).getGlobalCase();

        switch (Case){
            case "CONFIRMED":
                holder.tvRate.setBackgroundColor(rgb(255, 165, 0));
                holder.tvRate.setText("");
                break;
            case "RECOVERED":
                holder.tvRate.setBackgroundColor(rgb(0, 128, 0));
                holder.tvRate.append(" RECOVERY RATE");
                holder.tvNum.setTextColor(rgb(0, 128, 0));
                break;
            case "DEATHS":
                holder.tvRate.setBackgroundColor(rgb(255, 0, 0));
                holder.tvRate.append(" FATALITY RATE");
                holder.tvNum.setTextColor(rgb(255, 0, 0));
                break;
        }

        if(position == globalList.size()-1){
            holder.tvLastUpdate.setVisibility(View.VISIBLE);
            holder.bntSeeAllCountries.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return ApplicationClass.globalValues.size();
    }
}
