package com.example.android.mepill;

/**
 * Created by Elena on 16-Nov-17.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "We are in the receiver");

        //fetch extra string from the intent
        String getYourString = intent.getExtras().getString("extra");

        Log.i("What is the key?", getYourString);

        //create an intent to the ringtone service
        Intent serviceIntent = new Intent (context, RingtonePlayingService.class);

        //pass the extra string from MainActivity to the RingtonePlayingService
        serviceIntent.putExtra("extra", getYourString);

        //start the ringtone service
        context.startService(serviceIntent);


    }
}