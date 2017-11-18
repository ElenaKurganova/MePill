package com.example.android.mepill;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends AppCompatActivity {

    //Alarm manager
    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView updateText;
    Context context;
    PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = this;

        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // initialize timePicker
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker2);
        alarmTimePicker.setIs24HourView(true);

        // initialize update text box
        updateText = (TextView) findViewById(R.id.updated_text);

        // create an instance of calendar
        final Calendar calendar = Calendar.getInstance();

        // create an intent to the Alarm Receiver class
        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        // initialize the start button
        final Button alarm_on = (Button) findViewById(R.id.alarm_on);

        // create onClick listener to start an alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("This is log", "??????????????????????7787");


                // setting calendar instance with the hour and minutes picked on the Time Picker
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                // to prevent triggering the alarm if time passed for the day
                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.HOUR_OF_DAY, 24);

                }


                // get the int values of the hours and minutes
                int hours_int = alarmTimePicker.getHour();
                int minutes_int = alarmTimePicker.getMinute();


                // convert int values to Strings
                String hour = String.valueOf(hours_int);
                String minute = String.valueOf(minutes_int);


                if (minutes_int < 10) {
                    // 11.6 --> 11.06
                    minute = "0" + minutes_int;
                }

                // method that changes the update text Textbox
                set_alarm_text("Alarm set to " + hour + ":" + minute);

                // put in extra string into my intent, tells the clock that you pressed the "alarm on" button
                intent.putExtra("extra", "alarm on");


                // create a pending intent that delays the intent until the specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


                // setup the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        // initialize the stop button
        Button alarm_off = (Button) findViewById(R.id.alarm_off);

        // create onClick listener to turn off an alarm
        alarm_off.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {


                // method that changes the update text Textbox
                set_alarm_text("Alarm off");

                // cancel the alarm
                alarmManager.cancel(pendingIntent);

                // put extra string to my intent, tells the clock that you pressed "alarm off" button
                intent.putExtra("extra", "alarm off");


                // stop the ringtone
                sendBroadcast(intent);
            }

        });

    }

    private void set_alarm_text(String output) {
        updateText.setText(output);


    }
}
