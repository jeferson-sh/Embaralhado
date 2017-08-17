package estagio3.ufpb.com.br.embaralhando.util;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import estagio3.ufpb.com.br.embaralhando.R;

public class BackgroundSoundServiceUtil extends Service {

    private static MediaPlayer MEDIA_PLAYER;
    private static boolean STOP_BACKGROUND_MUSIC_ENABLE = true;
    private static boolean ISPLAYNG = true;

    @Override
    public void onCreate() {
        super.onCreate();
        MEDIA_PLAYER = MediaPlayer.create(this, R.raw.back_step);
        MEDIA_PLAYER.setLooping(true);
        MEDIA_PLAYER.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
    public int onStartCommand(Intent intent, int flags, int startId) {
        MEDIA_PLAYER.start();
        return flags;
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

    @Override
    public void onDestroy() {
        MEDIA_PLAYER.stop();
        MEDIA_PLAYER.release();
        super.onDestroy();
    }

    public static MediaPlayer getMediaPlayer() {
        return MEDIA_PLAYER;
    }

    public static boolean isStopBackgroundMusicEnable() {
        return STOP_BACKGROUND_MUSIC_ENABLE;
    }

    public static void setStopBackgroundMusicEnable(boolean stopBackgroundMusicEnable) {
        STOP_BACKGROUND_MUSIC_ENABLE = stopBackgroundMusicEnable;
    }

    public static boolean isPlaying() {
        return ISPLAYNG;
    }

    public static void setIsPlaying(boolean isPlaying) {
        BackgroundSoundServiceUtil.ISPLAYNG = isPlaying;
    }
}