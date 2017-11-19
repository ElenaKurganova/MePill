package com.example.android.mepill;

/**
 * Created by Elena on 16-Nov-17.
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import junit.framework.Assert;

public class RingtonePlayingService extends Service {

    private MediaPlayer mediaSong;
    private int startId;
    private boolean isRunning;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        //fetch the extra string values
        String state = intent.getExtras().getString("extra");
        Log.i("Ringtone state:", state);


        //this converts the extra string from the intent to start Ids, values 0 or 1.
        assert state != null;
        switch (state) {
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.i("Start Id is ", state);
                break;
            default:
                startId = 0;
                break;
        }


        //if else statements
        //if there is no music playing, and the user pressed "alarm on", music should start playing

        if (!this.isRunning && startId == 1) {
            Log.i("there is no music", "and you want on");

            //create an instance of Media player
            mediaSong = MediaPlayer.create(this, R.raw.lovingyou);
            //start the ringtone
            mediaSong.start();

            this.isRunning = true;
            this.startId = 1;

            //notification
            //set up the notification service
            NotificationManager notifyManager = (NotificationManager)
                    getSystemService(NOTIFICATION_SERVICE);

            // set up an intent that goes to the Main activity
            Intent intentMainActivity = new Intent(this.getApplicationContext(), MainActivity.class);
            // set up a pending intent
            PendingIntent pendingIntentMainAct = PendingIntent.getActivity(this, 0, intentMainActivity, 0);

            // make the notification parameters
            Notification notificationPopup = new Notification.Builder(this)
                    .setContentTitle("An alarm is going off")
                    .setContentText("Click me")
                    .setContentIntent(pendingIntentMainAct)
                    .setSmallIcon(R.drawable.pill)
                    .setAutoCancel(true)
                    .build();


            //set up the notification command
            notifyManager.notify(0, notificationPopup);

        }
        //if there is music playing, and the user pressed "alarm off", music should stop playing
        else if (this.isRunning && startId == 0) {
            Log.i("there is music", "and you want end");

            //stop the ringtone
            mediaSong.stop();
            mediaSong.reset();

            this.isRunning = false;
            this.startId = 0;
        }


        //these are if the user presses random buttons just to bug-proof the app
        //if there is no music playing, and the user pressed "alarm off", do nothing
        else if (!this.isRunning && startId == 0) {
            Log.i("there is no music", "and you want end");
            this.isRunning = false;
            this.startId = 0;

        }
        //if there is music playing, and the user presses "alarm on" button, do nothing
        else if (this.isRunning && startId == 1) {
            Log.i("there is music", "and you want start");
            this.isRunning = true;
            this.startId = 1;
        }
        //to catch odd events
        else {
            Log.i("else", "somehow you reached this");
        }
        return START_NOT_STICKY;


    }

    @Override
    public void onDestroy() {
        // Tell the user we stopped.
        Log.i("On Destroy called", "ta da");
        super.onDestroy();
        this.isRunning = false;

    }


}

