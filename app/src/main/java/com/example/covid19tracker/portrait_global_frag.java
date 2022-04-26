package com.example.covid19tracker;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.time.LocalDate;


/**
 * A simple {@link Fragment} subclass.
 */
public class portrait_global_frag extends Fragment {

    View view;

    public portrait_global_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_portrait_global_frag, container, false);
        return view;
    }

    public void seeAllCountries(View view){
        Intent intent = new Intent(getActivity(), AllCountriesActivity.class);
        startActivity(intent);
    }

}
