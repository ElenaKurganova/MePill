package com.example.android.mepill;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.idescout.sql.SqlScoutServer;


public class MainActivity extends AppCompatActivity {

    //Alarm manager
    private AlarmManager alarmManager;

    //TimePicker to pick a time
    private TimePicker timePicker;
    private TextView textToUpdate;
    private Context context;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

        // initialize the OK button
        final Button alarmOn = findViewById(R.id.alarm_activate);

        // create an intent to the Alarm Receiver class
        final Intent intent = new Intent(this.context, AlarmReceiver.class);

        // make onClick listener to activate the alarm
        alarmOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("This is log", "???????");

                // get the int values of the hours and minutes
                int hoursInt = timePicker.getHour();
                int minutesInt = timePicker.getMinute();

                // create an instance of calendar
                final Calendar calendar = Calendar.getInstance();

                // setting calendar instance with the hour and minutes picked on the Time Picker
                calendar.set(Calendar.HOUR_OF_DAY, hoursInt);
                calendar.set(Calendar.MINUTE, minutesInt);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                // to prevent triggering the alarm if time already passed for the day
                if (calendar.before(Calendar.getInstance())) {
                    Log.d("This is log", "Adding a day to run tomorrow");
                    calendar.add(Calendar.DATE, 1);
                }

                // convert int values for hour and minute to Strings
                String hour = String.valueOf(hoursInt);
                String minute = String.valueOf(minutesInt);

                if (minutesInt < 10) {
                    // Change minutes format(11:6 => 11:06)
                    minute = "0" + minutesInt;
                }

                // update the text in the Textbox
                setReminderStatus("Reminder set to " + hour + ":" + minute);

                // send "extra" String into the intent, tells the watch user pressed the "OK" button
                intent.putExtra("extra", "reminder on");

                // make a pending intent to delay the intent until the particular calendar time
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                Log.i("When to run", "Planning to run at " + calendar.toString());

                // setup the alarm manager
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        });

        // initialize the CANCEL button
        Button alarmOff = findViewById(R.id.cancel_alarm);

        // make onClick listener to cancel the reminder
        alarmOff.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                // update text regarding reminder status in the Textbox
                setReminderStatus("Reminder off");

                // cancel the alarm
                alarmManager.cancel(pendingIntent);

                // send "extra" String to the intent, tells the watch user pressed "CANCEL" button
                intent.putExtra("extra", "reminder off");

                // stop playing the ringtone
                sendBroadcast(intent);
            }

        });
    }

    private void initializeVariables() {
        //initialize alarm manager
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // initialize timePicker
        timePicker = findViewById(R.id.timePicker2);
        timePicker.setIs24HourView(true);

        // initialize text view to be updated
        textToUpdate = findViewById(R.id.text_to_update);
    }

    private void setReminderStatus(String output) {
        textToUpdate.setText(output);
    }
}
