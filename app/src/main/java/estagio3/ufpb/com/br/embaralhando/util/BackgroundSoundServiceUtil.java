package estagio3.ufpb.com.br.embaralhando.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import estagio3.ufpb.com.br.embaralhando.R;

public class BackgroundSoundServiceUtil extends Service {

    public static MediaPlayer MEDIA_PLAYER;
    public static boolean STOP_BACKGROUND_MUSIC_ENABLE = true;
    public static boolean ISPLAYNG= true;

    @Override
    public void onCreate() {
        super.onCreate();
        MEDIA_PLAYER = MediaPlayer.create(this, R.raw.back_step);
        MEDIA_PLAYER.setLooping(true);
        MEDIA_PLAYER.setVolume(100,100);
        MEDIA_PLAYER.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        MEDIA_PLAYER.start();
        return flags;
    }
    @Override
    public void onDestroy() {
        MEDIA_PLAYER.stop();
        MEDIA_PLAYER.release();
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