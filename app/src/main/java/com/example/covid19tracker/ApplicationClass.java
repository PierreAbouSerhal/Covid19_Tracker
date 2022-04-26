package com.example.covid19tracker;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ApplicationClass extends Application {
    static ArrayList<GlobalValues> globalValues = new ArrayList<>();
    static ArrayList<String> countries = new ArrayList<>();
    static ArrayList<CountryDetails> countryDetails = new ArrayList<>();
    static final String URL = "https://covid19.mathdro.id/api";
    static String lastUpdate ;
    static ArrayList<GeneralInfo> generalInfos = new ArrayList<>();
    static String note = "Information in the application in no way replaces the opinion of a health professional. If you have questions concerning your health status, consult a professional.";
    static final String CHANNEL_1_ID = "channel1";
    static final String CHANNEL_2_ID = "channel2";



    @Override
    public void onCreate() {
        super.onCreate();

        generalInfos.add(new GeneralInfo(1, "The main symptoms of COVID‑19 are as follows: fever, a new cough or a cough that gets worse, difficulty breathing, sudden loss of sense of smell without nasal congestion, with or without loss of taste"));
        generalInfos.add(new GeneralInfo(2, "People most at risk of dying from complications are those with a weak immune system  with a chronic disease such as diabetes or heart, lung and kidney disease age 70 and older."));
        generalInfos.add(new GeneralInfo(3, "There is no specific treatment or vaccine for COVID-19. Supportive treatment may, however, be provided"));
        generalInfos.add(new GeneralInfo(4, "Most people with COVID-19 will recover on their own."));
        generalInfos.add(new GeneralInfo(5, "In general, coronaviruses do not survive for long on objects. They can survive for around 3 hours on inert objects with dry surfaces and for around 6 days on inert objects with wet surfaces"));
        generalInfos.add(new GeneralInfo(6, "Wash your hands often with soap under warm running water for at least 20 seconds."));
        generalInfos.add(new GeneralInfo(7, "Use an alcohol-based hand rub if soap and water are not available."));
        generalInfos.add(new GeneralInfo(8, "Cover your mouth and nose with your arm to reduce the spread of germs."));
        generalInfos.add(new GeneralInfo(9, "If you are sick, avoid contact with more vulnerable people, including older adults and people with a weak immune system or a chronic disease."));


        //Notifications
        createNotificaationChannels();
    }


    private void createNotificaationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);

        }
    }
}
