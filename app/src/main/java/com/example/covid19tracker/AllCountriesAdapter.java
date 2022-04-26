package com.example.covid19tracker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllCountriesAdapter extends RecyclerView.Adapter<AllCountriesAdapter.ViewHolder> implements Filterable {
    final String TAG = "Debug";
    AllCountriesInterface activity;

    public interface AllCountriesInterface {
        void onItemClicked(int index);
    }

    ArrayList<CountryDetails> countryDetails;
    ArrayList<CountryDetails> FullCountryDetails;

    AllCountriesAdapter (Context context, ArrayList<CountryDetails> list){
        countryDetails = new ArrayList<>(list);
        activity = (AllCountriesInterface) context;
        FullCountryDetails = new ArrayList<>(list);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivFlag;
        TextView tvCountryName, tvConfirmed, tvDeaths, tvRecovered;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvCountryName = itemView.findViewById(R.id.tvCountryName);
            tvConfirmed = itemView.findViewById(R.id.tvConfirmed);
            tvDeaths = itemView.findViewById(R.id.tvDeaths);
            tvRecovered = itemView.findViewById(R.id.tvRecovered);
            ivFlag = itemView.findViewById(R.id.ivCountry);

        }
    }


    @NonNull
    @Override
    public AllCountriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_row, parent, false);

        return new AllCountriesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCountriesAdapter.ViewHolder holder, int position) {
            holder.itemView.setTag(countryDetails.get(position));
            for (Country c : Country.COUNTRIES){
                if(c.getCode().equals(countryDetails.get(position).getIso2())){
                    holder.tvCountryName.setText(c.getName());
                    holder.tvConfirmed.setText("Confirmed Cases: " + String.valueOf(countryDetails.get(position).getConfNum()));
                    holder.tvRecovered.setText("Recovered: " + String.valueOf(countryDetails.get(position).getRecovNum()));
                    holder.tvDeaths.setText("Deaths: " + String.valueOf(countryDetails.get(position).getDeathNum()));
                    holder.ivFlag.setImageResource(c.getFlag());
                }
            }
    }

    @Override
    public int getItemCount() {
        return countryDetails.size();
    }

    @Override
    public Filter getFilter(){
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CountryDetails> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(FullCountryDetails);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (CountryDetails det : FullCountryDetails){
                    if(det.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(det);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryDetails.clear();
            countryDetails.addAll((ArrayList)results.values);
            notifyDataSetChanged();
        }
    };

}
