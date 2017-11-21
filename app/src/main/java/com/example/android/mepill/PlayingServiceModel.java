package com.example.android.mepill;

/**
 * Created by Elena on 13-Nov-17.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class PlayingServiceModel extends Service {

    private MediaPlayer player;
    private int triggerId;
    private boolean isRunning;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int triggerId) {
        Log.i("LocalService", "Received Trigger Id " + triggerId + ": " + intent);

        //get "extra" String values
        String status = intent.getExtras().getString("extra");
        Log.i("Ringtone state:", status);

        //update "extra" String from the Intent to triggerIds, values 0 to 1.
        assert status != null;
        switch (status) {
            case "reminder on":
                triggerId = 1;
                break;
            case "reminder off":
                triggerId = 0;
                Log.i("Trigger Id is ", status);
                break;
            default:
                triggerId = 0;
                break;
        }

        //if no sound playing, and the user pressed "OK", sound should begin playing

        if (!this.isRunning && triggerId == 1) {
            Log.i("No sound playing", "and user wants on");

            //create Media player instance
            player = MediaPlayer.create(this, R.raw.lovingyou);

            //start the sound
            player.start();

            this.isRunning = true;
            this.triggerId = 1;

            //set up the notification service
            NotificationManager notifyManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            // set up an intent that goes to the Main activity
            Intent intentMainActivity = new Intent(this.getApplicationContext(), TimePickerActivity.class);

            // set up a pending intent
            PendingIntent pendingIntentMainAct = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

            // make the notification parameters
            Notification notificationPopup = new Notification.Builder(this)
                    .setContentTitle("Take a pill. To turn the sound off")
                    .setContentText("Click me")
                    .setContentIntent(pendingIntentMainAct)
                    .setSmallIcon(R.drawable.pill)
                    .setAutoCancel(true)
                    .build();

            //set up the notification command
            notifyManager.notify(0, notificationPopup);

        }
        //if sound is playing, and the user pressed "Cancel" button, sound should stop playing
        else if (this.isRunning && triggerId == 0) {
            Log.i("there is sound playing", "and user wants off");

            //stop the sound
            player.stop();
            player.reset();

            this.isRunning = false;
            this.triggerId = 0;
        }

        //bug-proof
        //if no sound playing, and the user pressed "Cancel", do nothing
        else if (!this.isRunning && triggerId == 0) {
            Log.i("there is no sound", "and user wants off");
            this.isRunning = false;
            this.triggerId = 0;
        }

        //if sound is playing, and the user presses "OK" button, do nothing
        else if (this.isRunning && triggerId == 1) {
            Log.i("there is sound", "and user wants on");
            this.isRunning = true;
            this.triggerId = 1;
        }
        //to prevent weird events
        else {
            Log.i("else", "weird event");
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.i("Destroy called: ", "stop");
        super.onDestroy();
        this.isRunning = false;
    }
}

