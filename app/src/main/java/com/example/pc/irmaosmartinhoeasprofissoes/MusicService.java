package com.example.pc.irmaosmartinhoeasprofissoes;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Bruno on 17/11/2017.
 */

public class MusicService extends Service {

    MediaPlayer mp;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        mp = MediaPlayer.create(this, R.raw.mainmenu);
        mp.setLooping(true);
        mp.setVolume(100, 100);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mp.isPlaying()) {
            mp.start();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mp.stop();
        super.onDestroy();
    }

    public static void playSound(Context context, int uri)
    {
        MediaPlayer mp = MediaPlayer.create(context, uri);
        mp.start();
    }
}
