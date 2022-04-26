package com.example.covid19tracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class GlobalCaseActivity extends AppCompatActivity implements GlobalAdapter.GlobalValuesInterface {

    TextView tvConfirmed, tvConfPerc, tvRecovered, tvRecovPerc, tvDeaths, tvDeathPerc, tvLastUpdate;
    final String TAG = "Debug";
    landscape_global_frag landscape_global_frag;
    portrait_global_frag portrait_global_frag;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_case);

        tvConfirmed = findViewById(R.id.tvConfirmed);
        tvConfPerc = findViewById(R.id.tvConfPerc);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvRecovPerc = findViewById(R.id.tvRecovPerc);
        tvDeaths = findViewById(R.id.tvDeaths);
        tvDeathPerc = findViewById(R.id.tvDeathPerc);
        tvLastUpdate = findViewById(R.id.tvLastUpdate);

        fragmentManager = this.getSupportFragmentManager();
        landscape_global_frag = (landscape_global_frag) fragmentManager.findFragmentById(R.id.land_scape_globallist);
        portrait_global_frag = (portrait_global_frag) fragmentManager.findFragmentById(R.id.portrait_global);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.land_scape_globallist))
                    .hide(fragmentManager.findFragmentById(R.id.portrait_global))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.land_scape_globallist))
                    .show(fragmentManager.findFragmentById(R.id.portrait_global))
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        tvConfirmed.setText(String.valueOf(ApplicationClass.globalValues.get(0).getCaseNum()));
        tvRecovered.setText(String.valueOf(ApplicationClass.globalValues.get(1).getCaseNum()));
        tvRecovPerc.setText(String.valueOf(ApplicationClass.globalValues.get(1).getCaseRate()) + "% RECOVERY RATE");
        tvDeaths.setText(String.valueOf(ApplicationClass.globalValues.get(2).getCaseNum()));
        tvDeathPerc.setText(String.valueOf(ApplicationClass.globalValues.get(2).getCaseRate()) + "% FATALITY RATE");

        tvLastUpdate.setText("Last Update: " + ApplicationClass.lastUpdate);

    }

    public void seeAllCountries(View view){
        Intent intent = new Intent(GlobalCaseActivity.this, AllCountriesActivity.class);
        startActivity(intent);
    }


    // changing layouts when changing orientation

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            fragmentManager.beginTransaction()
                    .show(fragmentManager.findFragmentById(R.id.land_scape_globallist))
                    .hide(fragmentManager.findFragmentById(R.id.portrait_global))
                    .commit();

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            fragmentManager.beginTransaction()
                    .hide(fragmentManager.findFragmentById(R.id.land_scape_globallist))
                    .show(fragmentManager.findFragmentById(R.id.portrait_global))
                    .commit();
        }
    }

    @Override
    public void onItemClicked(int index) {

    }
}
