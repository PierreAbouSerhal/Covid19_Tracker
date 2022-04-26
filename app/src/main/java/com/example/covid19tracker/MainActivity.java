package com.example.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Notification;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.tomer.fadingtextview.FadingTextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String TAG = "Debug";
    private RequestQueue mQueue ;
    final static ArrayList<String> countries = new ArrayList<>();
    private int requestNum, arrayLength;
    private Intent intent ;
    private TextSwitcher tSwitcher;
    private LinearLayout loading_container;
    private View mProgressView;
    private TextView tvLoad;
    private NotificationManagerCompat notificationManager;
    private FadingTextView fadingTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);

        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        fadingTv = findViewById(R.id.fadingTv);
        loading_container = findViewById(R.id.loading_container);

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            loading_container.setTop(50);
        }

        //Global Values Intent
        intent = new Intent(MainActivity.this, GlobalCaseActivity.class);

        mQueue = Volley.newRequestQueue(this);

        showProgress(true);

        final Country[] allCountries = Country.COUNTRIES;
        ApplicationClass.countryDetails.clear();

        requestNum = 0;
        arrayLength = allCountries.length;

        for(int i =0; i< allCountries.length; i++){
            requestNum++;
            final String iso2 = allCountries[i].getCode();
            final String countryName = allCountries[i].getName();

            mQueue.add(fetchCountryDetails(iso2, countryName));

        }
        Request requestAllCountries = fetchCountries();
        Request requestGlobalVal = fetchGlobalValues();

        mQueue.add(requestAllCountries);
        mQueue.add(requestGlobalVal);

    }

    public JsonObjectRequest fetchCountries(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApplicationClass.URL+"/countries", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    countries.clear();
                    JSONArray countriesArray = response.getJSONArray("countries");
                    for(int i =0; i < countriesArray.length(); i++){
                        JSONObject country = countriesArray.getJSONObject(i);

                        countries.add(country.getString("name"));
                    }

                    ApplicationClass.countries = countries;

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return request;
    }

    public JsonObjectRequest fetchGlobalValues(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApplicationClass.URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ApplicationClass.globalValues.clear();
                    JSONObject allConf = response.getJSONObject("confirmed");
                    //tvConfirmed.setText(String.valueOf(allConf.getInt("value")));

                    JSONObject allrecov = response.getJSONObject("recovered");
                    //tvRecovered.setText(String.valueOf(allrecov.getInt("value")));

                    JSONObject allDeaths = response.getJSONObject("deaths");
                    //tvDeaths.setText(String.valueOf(allDeaths.getInt("value")));

                    String lastUpdate = response.getString("lastUpdate");
                    ApplicationClass.lastUpdate = "";

                    for(int i=0; i<10; i++){
                        ApplicationClass.lastUpdate += lastUpdate.charAt(i);
                    }

                    int total = allConf.getInt("value");

                    int confPerc = (allConf.getInt("value") *100) / total;
                    int confRecov = (allrecov.getInt("value") *100) / total;
                    int confDeath = (allDeaths.getInt("value") *100) / total;

                    ApplicationClass.globalValues.add(new GlobalValues("CONFIRMED", allConf.getInt("value"), confPerc));
                    ApplicationClass.globalValues.add(new GlobalValues("RECOVERED", allrecov.getInt("value"), confRecov));
                    ApplicationClass.globalValues.add(new GlobalValues("DEATHS", allDeaths.getInt("value"), confDeath));

                    showProgress(false);
                    MainActivity.this.finish();
                    startActivity(intent);

                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        return request;
    }

    public JsonObjectRequest fetchCountryDetails(final String iso2, final String countryName){

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, ApplicationClass.URL+"/countries/" + iso2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject confVal = response.getJSONObject("confirmed");
                        JSONObject recovVal = response.getJSONObject("recovered");
                        JSONObject deathVal = response.getJSONObject("deaths");

                        CountryDetails countryDetail = new CountryDetails(iso2, confVal.getInt("value"), recovVal.getInt("value"), deathVal.getInt("value"));
                        countryDetail.setName(countryName);
                        ApplicationClass.countryDetails.add(countryDetail);

                    } catch (JSONException e) {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            return request;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    private void showNotifications (){
        Notification notification = new NotificationCompat.Builder(this, ApplicationClass.CHANNEL_1_ID)
                .setSmallIcon(R.mipmap.covid19_main_foreground)
                .setContentTitle("TITLE EXAMPLE")
                .setContentText("TEXT EXAMPLE")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

}



