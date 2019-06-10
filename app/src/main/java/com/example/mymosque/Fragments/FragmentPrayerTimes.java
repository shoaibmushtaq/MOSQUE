package com.example.mymosque.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityManagerCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymosque.Adapter.MyPagerAdapter;
import com.example.mymosque.R;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.github.vivchar.viewpagerindicator.ViewPagerIndicator;


import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FragmentPrayerTimes extends Fragment {

    View v;
TextView time,date,islamic_Calendar;
Button applyAlarm,disableAlarm;
SharedPreferences.Editor editor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);










    }//end of onCreate method

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_prayer_times, container, false);
editor=getActivity().getSharedPreferences("Alaram check", Context.MODE_PRIVATE).edit();
        //<For Toolbar>
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Prayer Times");
        applyAlarm=(Button) v.findViewById(R.id.ApplyAlarm);
        //</For Toolbar>
       // initbutton();
disableAlarm=(Button) v.findViewById(R.id.DisableAlarm);

SharedPreferences ac=getActivity().getSharedPreferences("Alaram check",Context.MODE_PRIVATE);
if(ac.getString("alarm is set","no").equals("no")){

    applyAlarm.setVisibility(View.VISIBLE);
    disableAlarm.setVisibility(View.GONE);
}
else{

    applyAlarm.setVisibility(View.GONE);
    disableAlarm.setVisibility(View.VISIBLE);
}
        time=(TextView) v.findViewById(R.id.txt_time);
        date=(TextView) v.findViewById(R.id.text_date);
        islamic_Calendar=v.findViewById(R.id.islamic_date);
        final ViewPager pager = (ViewPager) v.findViewById(R.id.viewPager);
        ImageView leftNav = (ImageView) v.findViewById(R.id.left_nav);
        ImageView rightNav = (ImageView) v.findViewById(R.id.right_nav);
FragmentSecond fragmentSecond=new FragmentSecond();
       applyAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyAlarm.setVisibility(View.GONE);
                String currentdate=fragmentSecond.get_curr_date();
                //Toast.makeText(getContext(),"Alarm set",Toast.LENGTH_LONG).show();
                editor.putString("alarm_date",currentdate);
                editor.putString("alarm is set","yes");
                editor.putInt("button is Apply",1);
                editor.putString("Activate disable button","yes");

                editor.apply();
              //  FragmentSecond.alarm_check=true;
         disableAlarm.setVisibility(View.VISIBLE);


            }
        });
disableAlarm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        disableAlarm.setVisibility(View.GONE);
        Toast.makeText(getContext(),"Alarm disable",Toast.LENGTH_LONG).show();
        editor.putString("Activate disable button","no");
        editor.putString("alarm is set","no");
        editor.apply();
        applyAlarm.setVisibility(View.VISIBLE);
       // fragmentSecond.refresh_json();

    }
});
        // Images left navigation
//get current date
        Date c = Calendar.getInstance().getTime();

        String f=String.valueOf(c);
        String curr_date=f.substring(0,10);
        String Curr_year=f.substring(29);
        date.setText(curr_date+" "+Curr_year);
        String curr_time=f.substring(11,19);
      time.setText(curr_time);
//get islamic date
        UmmalquraCalendar ca = new UmmalquraCalendar();
        ca.get(Calendar.YEAR);
        ca.get(Calendar.MONTH);
        ca.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.UK);
        ca.get(Calendar.DAY_OF_MONTH);


        //  Toast.makeText(this,""+cal,Toast.LENGTH_SHORT).show();
        Log.d("OK", "onCreate: "+ca.get(Calendar.YEAR)+"    "+ca.get(Calendar.MONTH)+"  "+ca.get(Calendar.DAY_OF_MONTH)+"   "+ca.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.ENGLISH));
        String date_islamic=ca.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.ENGLISH)+"-"+ca.get(Calendar.DAY_OF_MONTH)+"-"+ca.get(Calendar.YEAR);
        islamic_Calendar.setText(date_islamic);



        pager.setAdapter(new MyPagerAdapter(getFragmentManager()));
        ViewPagerIndicator viewPagerIndicator = (ViewPagerIndicator) v.findViewById(R.id.view_pager_indicator);

        viewPagerIndicator.setupWithViewPager(pager);

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    pager.setCurrentItem(tab);
                } else if (tab == 0) {
                    pager.setCurrentItem(tab);
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                tab++;
                pager.setCurrentItem(tab);
            }
        });















        return v;
    }//End onCreateView Method
    public void initbutton(){

        applyAlarm=v.findViewById(R.id.ApplyAlarm);
        disableAlarm=v.findViewById(R.id.DisableAlarm);
        FragmentSecond fs=new FragmentSecond();
        SharedPreferences pref=getActivity().getPreferences(Context.MODE_PRIVATE);
        if(pref.getString("Activate disable button","no").equals("yes")) {
            applyAlarm.setVisibility(View.GONE);
            disableAlarm.setVisibility(View.VISIBLE);

        }
            else{
                disableAlarm.setVisibility(View.GONE);
                applyAlarm.setVisibility(View.VISIBLE);


            }

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch(pos) {

                case 0: return new FragmentFirst();
                case 1: return new FragmentSecond();
                case 2: return new FragmentThird();

                case 3: return new FragmentFourth();

                default: return new FragmentFourth();
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }




}
