package com.example.android.mepill;

/**
 * Created by Elena on 13-Nov-17.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class AlarmReceiverController extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("AlarmReceiverController", "This is receiver");

        //get "extra" String from the intent
        String intentString = intent.getExtras().getString("extra");
        Log.i("The key is: ", intentString);

        //make an intent to the ringtone service
        Intent serviceIntent = new Intent (context, PlayingServiceModel.class);

        //put the "extra" String from TimePickerActivity to the PlayingServiceModel
        serviceIntent.putExtra("extra", intentString);

        //begin the ringtone service
        context.startService(serviceIntent);


    }
}