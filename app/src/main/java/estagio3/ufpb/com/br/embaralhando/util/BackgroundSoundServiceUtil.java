package estagio3.ufpb.com.br.embaralhando.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import estagio3.ufpb.com.br.embaralhando.R;

public class BackgroundSoundServiceUtil extends Service {

    private MediaPlayer player;
    public static boolean PLAYING = true;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.back_step);
        player.setLooping(true);
        player.setVolume(100,100);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return flags;
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}