package com.example.android.mepill;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddPillAlarmActivity extends AppCompatActivity {
    FloatingActionButton fabDone;

    String medicineName;
    int quantity;

    EditText medicineInput;
    EditText quantityInput;

    Button pickTimeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pill_register);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarWelcome);
        setSupportActionBar(toolbar);

        medicineInput = (EditText) findViewById(R.id.medicineName);
        quantityInput = (EditText) findViewById(R.id.quantity);

        pickTimeButton = (Button) findViewById(R.id.pick_time);
        pickTimeButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View view){
            startActivity(new Intent(AddPillAlarmActivity.this, TimePickerActivity.class));
        }
        });


            FloatingActionButton fabDone = (FloatingActionButton) findViewById(R.id.fab_done);
            fabDone.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    medicineName = medicineInput.getText().toString();
                    quantity = Integer.valueOf(quantityInput.getText().toString());
                }
            });
        }
    }