package com.example.mymosque.Fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mymosque.API;
import com.example.mymosque.Adapter.AdapterRVTimes;
import com.example.mymosque.MonthlyTime;
import com.example.mymosque.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.MissingFormatArgumentException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentSecond extends Fragment {
    public static final String TAG = "MyFragment.TAG";
    private ArrayList<String> NamazTimes = new ArrayList<>();
    View v;
    String date;
    SharedPreferences getprefs;
static Boolean alarm_check;
Button apply;
    RecyclerView recyclerView;
    ArrayList<MonthlyTime> monthlyTimes;
    String Alarmcheck;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_second, container, false);
alarm_check=false;
        v.findViewById(R.id.secondfrag);
        getprefs=getActivity().getSharedPreferences("Alaram check", Context.MODE_PRIVATE);
        Alarmcheck=getprefs.getString("alarm is set","no");
        monthlyTimes = new ArrayList<>();
        recyclerView = (RecyclerView) v.findViewById(R.id.RV_times);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
   date = get_curr_date();
        refresh_json();
        return v;
    }
public void refresh_json(){
    fetch_json("http://masjidi.co.uk/api/", 38,"30-5-2019");

}
    public String get_curr_date() {
        Date c = Calendar.getInstance().getTime();


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        Log.d("date", formattedDate);


        return formattedDate;
    }

    public void fetch_json(String url, int userid, String date) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        //pass interface refverence to retrofit and it will generate class automatically
        API apiInterface = retrofit.create(API.class);
//making the call object
        Call<List<MonthlyTime>> call = apiInterface.getmonthlytime(userid, date);
        //calling enqueue method
        call.enqueue(new Callback<List<MonthlyTime>>() {
            @Override
            public void onResponse(Call<List<MonthlyTime>> call, Response<List<MonthlyTime>> response) {

                monthlyTimes = (ArrayList<MonthlyTime>) response.body();

                AdapterRVTimes rvTimes = new AdapterRVTimes(getContext(), monthlyTimes);
if(getprefs.getString("alarm is set","no").equals("yes")) {
    Toast.makeText(getContext(), "yes", Toast.LENGTH_LONG).show();

    setCalendar(monthlyTimes);
}
                recyclerView.setAdapter(rvTimes);


            }


            @Override
            public void onFailure(Call<List<MonthlyTime>> call, Throwable t) {
            Log.d("failure", t.getCause().toString());
            }
        });
    }
    private void setCalendar(ArrayList<MonthlyTime> prayerTimeList) {
        PendingIntent pendingIntent;
        Date hour1 = null;
        Date minuutes1 = null;



        Date datec = null;
        String formattedDate = null;


      //  for (int i = 0; i < prayerTimeList.size(); i++) {
          //  String strDate = prayerTimeList.get(i).getDate();
            //SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            //try {
              //  datec = df.parse(strDate);

                //SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
                //formattedDate = fd.format(datec);
                //Log.d("second", formattedDate);
            //} catch (ParseException e) {
              //  e.printStackTrace();
            //}

            //if (formattedDate.equals(date)) {


                //  if (Build.VERSION.SDK_INT >= 23)
//curently replacing .get(i) with .get(0) because api's current datte is causing problem
                String fajrTime = prayerTimeList.get(0).getFajr();
                String fajrTimehour = fajrTime.substring(0, 2);
                String fajrTimeMinutes = fajrTime.substring(3, 5);
                //setting zuhur time
                String ZuhrTime = prayerTimeList.get(0).getZhuhr();
                String ZuhrTimehour = ZuhrTime.substring(0, 2);
                String ZuhurTimeMinutes = ZuhrTime.substring(3, 5);
                //setting asr time

                String AsrTime = prayerTimeList.get(0).getAsr();
                String AsrTimehour = AsrTime.substring(0, 2);
                String AsrTimeMinutes = AsrTime.substring(3, 5);
                //setting MaghribTime
                String MaghribTime = prayerTimeList.get(0).getMaghrib();
                String MaghribTimehour = MaghribTime.substring(0, 2);
                String MaghribTimeMinutes = MaghribTime.substring(3, 5);


                //setting isha time
                String IShaTime = prayerTimeList.get(0).getIsha();
                String IShaTimehour = IShaTime.substring(0, 2);
                String IShaTimeMinutes = IShaTime.substring(3, 5);
                //setting calendars
                Calendar calendarfajar = Calendar.getInstance();
                Calendar calendarzuhur = Calendar.getInstance();
                Calendar calendarAsr = Calendar.getInstance();
                Calendar calendarMaghrib = Calendar.getInstance();
                Calendar calendarISha = Calendar.getInstance();
                //setting fajar calendar
                calendarfajar.set(calendarfajar.get(Calendar.YEAR), calendarfajar.get(Calendar.MONTH), calendarfajar.get(Calendar.DAY_OF_MONTH),
                        Integer.parseInt(fajrTimehour), Integer.parseInt(fajrTimeMinutes), 0);
                //setting zuhur calendar
                calendarzuhur.set(calendarzuhur.get(Calendar.YEAR), calendarzuhur.get(Calendar.MONTH), calendarzuhur.get(Calendar.DAY_OF_MONTH),
                        Integer.parseInt(ZuhrTimehour), Integer.parseInt(ZuhurTimeMinutes), 0);
//settingAsrcalendar
                calendarAsr.set(calendarAsr.get(Calendar.YEAR), calendarAsr.get(Calendar.MONTH), calendarAsr.get(Calendar.DAY_OF_MONTH),
                        Integer.parseInt(AsrTimehour), Integer.parseInt(AsrTimeMinutes), 0);
                //setting maghrib calendar
                calendarMaghrib.set(calendarMaghrib.get(Calendar.YEAR), calendarMaghrib.get(Calendar.MONTH), calendarMaghrib.get(Calendar.DAY_OF_MONTH),
                        Integer.parseInt(MaghribTimehour), Integer.parseInt(MaghribTimeMinutes), 0);
                //setting ISha Calendar
                calendarISha.set(calendarISha.get(Calendar.YEAR), calendarISha.get(Calendar.MONTH), calendarISha.get(Calendar.DAY_OF_MONTH),
                        Integer.parseInt(IShaTimehour), Integer.parseInt(IShaTimeMinutes), 0);
Toast.makeText(getContext(),"asr time"+prayerTimeList.get(0).getAsr(),Toast.LENGTH_LONG).show();
                ArrayList<Calendar> calendars = new ArrayList<>();
                calendars.add(calendarfajar);
                calendars.add(calendarzuhur);
                calendars.add(calendarAsr);
                calendars.add(calendarMaghrib);
                calendars.add(calendarISha);
                setAlarm(calendars);
            }
            //}
       // }
    //}

   // }

        public void setAlarm(ArrayList<Calendar> calendars){
        Toast.makeText(getContext(),"Alarm setting",Toast.LENGTH_LONG).show();
            for(int j=0;j<calendars.size();j++){
                if(System.currentTimeMillis()>calendars.get(j).getTimeInMillis()){
                    calendars.get(j).add(Calendar.DATE,1);

                }
            long time = calendars.get(j).getTimeInMillis();

                    AlarmManager am = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

                    Intent intent = new Intent(getContext(), MyAlarm.class);
                    intent.putExtra("azan name", j);
                    PendingIntent pi = PendingIntent.getBroadcast(getContext(), j, intent, 0);
                    am.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pi);


            }
    }
    




















}

