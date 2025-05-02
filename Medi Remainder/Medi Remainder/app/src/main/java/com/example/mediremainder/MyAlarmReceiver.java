package com.example.mediremainder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;

public class MyAlarmReceiver extends BroadcastReceiver {
    MediaPlayer mp;

    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mp.setLooping(true);
        mp.start();
    }
}