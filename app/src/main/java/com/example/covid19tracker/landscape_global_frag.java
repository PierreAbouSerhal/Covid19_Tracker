package com.example.covid19tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class landscape_global_frag extends Fragment {

    RecyclerView recyclerView;
    GlobalAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    View view;
    final String TAG = "Debug";


    public landscape_global_frag() {
        // Required empty public constructor
    }

    public void seeAllCountries (View view){
        Intent intent = new Intent(getActivity(), AllCountriesActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_landscape_global_frag, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.global_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        myAdapter = new GlobalAdapter(getActivity(), ApplicationClass.globalValues);
        recyclerView.setAdapter(myAdapter);

    }

    public void notifyDataChanged(){
        myAdapter.notifyDataSetChanged();
    }
}
