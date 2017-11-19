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

import com.idescout.sql.SqlScoutServer;


public class MainActivity extends AppCompatActivity {

    //Alarm manager
    private AlarmManager alarmManager;
    private TimePicker alarmTimePicker;
    private TextView textToUpdate;
    private Context context;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SqlScoutServer.create(this, getPackageName());

        AlarmDbHelper alarmDbHelper = new AlarmDbHelper(this);

        //TO DELETE
        Alarm alarm = new Alarm();
        alarm.setHour(11);
        alarm.setMedicine("panadol");
        alarm.setMinute(55);
        alarm.setScheduleName("some fake alarm");

        alarmDbHelper.createRow(alarm);
        alarmDbHelper.createRow(alarm);
        //END TO DELETE

        setContentView(R.layout.activity_main);
        this.context = this;

        initializeVariables();

        // initialize the start button
        final Button alarm_on = findViewById(R.id.alarm_on);

        // create an intent to the Alarm Receiver class
        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        // create onClick listener to start an alarm
        alarm_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("This is log", "??????????????????????7787");

                // get the int values of the hours and minutes
                int hoursInt = alarmTimePicker.getHour();
                int minutesInt = alarmTimePicker.getMinute();

                // create an instance of calendar
                final Calendar calendar = Calendar.getInstance();

                // setting calendar instance with the hour and minutes picked on the Time Picker
                calendar.set(Calendar.HOUR_OF_DAY, hoursInt);
                calendar.set(Calendar.MINUTE, minutesInt);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // to prevent triggering the alarm if time passed for the day
                if (calendar.before(Calendar.getInstance())) {
                    Log.d("This is log", "Adding a day to run tomorrow");
                    calendar.add(Calendar.DATE, 1);
                }





                // convert int values to Strings
                String hour = String.valueOf(hoursInt);
                String minute = String.valueOf(minutesInt);


                if (minutesInt < 10) {
                    // 11.6 --> 11.06
                    minute = "0" + minutesInt;
                }

                // method that changes the update text Textbox
                set_alarm_text("Alarm set to " + hour + ":" + minute);

                // put in extra string into my intent, tells the clock that you pressed the "alarm on" button
                intent.putExtra("extra", "alarm on");


                // create a pending intent that delays the intent until the specified calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.i("When to run", "Planning to run at " + calendar.toString());
                // setup the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            }
        });

        // initialize the stop button
        Button alarm_off = findViewById(R.id.alarm_off);

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

    private void initializeVariables() {
        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // initialize timePicker
        alarmTimePicker = findViewById(R.id.timePicker2);
        alarmTimePicker.setIs24HourView(true);

        // initialize update text box
        textToUpdate = findViewById(R.id.updated_text);
    }

    private void set_alarm_text(String output) {
        textToUpdate.setText(output);


    }
}
