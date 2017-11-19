package com.example.android.mepill;

/**
 * Created by Elena on 13-Nov-17.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiver", "This is receiver");

        //get "extra" String from the intent
        String intentString = intent.getExtras().getString("extra");
        Log.i("The key is: ", intentString);

        //make an intent to the ringtone service
        Intent serviceIntent = new Intent (context, PlayingService.class);

        //put the "extra" String from MainActivity to the PlayingService
        serviceIntent.putExtra("extra", intentString);

        //begin the ringtone service
        context.startService(serviceIntent);


    }
}