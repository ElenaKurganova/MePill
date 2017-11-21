package com.example.android.mepill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddPillAlarmActivity extends AppCompatActivity {
    FloatingActionButton fabDone;

    private String medicine;
    private int quantity;
    private int hour;
    private int minute;
    private TextView textToTime;

    EditText medicineInput;
    EditText quantityInput;


    Button pickTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AddPillAlarm", "Starting activity");
        setContentView(R.layout.activity_pill_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWelcome);
        setSupportActionBar(toolbar);

        initializeTextToBeUpdated();

        if (AlarmCreationSingleton.getInstance().getMedicine() != null) {
            medicineInput = (EditText) findViewById(R.id.medicineName);
            medicineInput.setText(AlarmCreationSingleton.getInstance().getMedicine());
        }

        if (AlarmCreationSingleton.getInstance().getQuantity() != 0) {
            quantityInput = (EditText) findViewById(R.id.quantityName);
            quantityInput.setText(String.valueOf(AlarmCreationSingleton.getInstance().getQuantity()));
        }


        if (AlarmCreationSingleton.getInstance().getHour() != -1 &&
                AlarmCreationSingleton.getInstance().getMinute() != -1) {
            hour = AlarmCreationSingleton.getInstance().getHour();
            minute = AlarmCreationSingleton.getInstance().getMinute();

            // convert int values for hours and minutes to Strings
            String hours = String.valueOf(hour);
            String minutes = String.valueOf(minute);

            if (minute < 10) {
                // Change minutes format(11:6 => 11:06)
                minutes = "0" + minutes;
            }

            // update the text in the Textbox
            setReminderStatus(hours + ":" + minutes);
        }


        pickTimeButton = (Button) findViewById(R.id.pick_time);
        pickTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicineInput = (EditText) findViewById(R.id.medicineName);
                quantityInput = (EditText) findViewById(R.id.quantityName);

                AlarmCreationSingleton.getInstance().setMedicine(medicineInput.getText().toString());
                AlarmCreationSingleton.getInstance().setQuantity(Integer.valueOf(quantityInput.getText().toString()));


                startActivity(new Intent(AddPillAlarmActivity.this, TimePickerActivity.class));
            }
        });

        // return to Welcome Screen
        FloatingActionButton fabDone = (FloatingActionButton) findViewById(R.id.fab_done);
        fabDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddPillAlarmActivity.this, WelcomeScreenView.class));

            }
        });
    }

    // initialize text view to be updated
    private void initializeTextToBeUpdated() {
        textToTime = findViewById(R.id.text_time);
    }

    private void setReminderStatus(String output) {
        textToTime.setText(output);
    }
}

