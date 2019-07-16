package com.mydroidtechnology.embaralhado.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.mydroidtechnology.embaralhado.R;

//Class responsibility for sound service.
public class BackgroundMusicService extends Service {

    private static MediaPlayer MEDIA_PLAYER;
    private static boolean STOP_BACKGROUND_MUSIC_ENABLE = true;
    private static boolean IS_PLAYING = true;
    private final IBinder binder = new LocalBinder();


    @Override
    public void onCreate() {
        super.onCreate();
        MEDIA_PLAYER = MediaPlayer.create(this, R.raw.back_step);
        MEDIA_PLAYER.setLooping(true);
        MEDIA_PLAYER.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MEDIA_PLAYER.start();
        return flags;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onDestroy() {
        MEDIA_PLAYER.stop();
        MEDIA_PLAYER.release();
        setMediaPlayer();
        super.onDestroy();
    }

    public static MediaPlayer getMediaPlayer() {
        return MEDIA_PLAYER;
    }

    private static void setMediaPlayer() {
        MEDIA_PLAYER = null;
    }

    public static boolean stopBackgroundMusicEnable() {
        return STOP_BACKGROUND_MUSIC_ENABLE;
    }

    public static void setStopBackgroundMusicEnable(boolean stopBackgroundMusicEnable) {
        STOP_BACKGROUND_MUSIC_ENABLE = stopBackgroundMusicEnable;
    }

    public static boolean isPlaying() {
        return IS_PLAYING;
    }

    public static void setIsPlaying(boolean isPlaying) {
        BackgroundMusicService.IS_PLAYING = isPlaying;
    }

    public class LocalBinder extends Binder {
        BackgroundMusicService getService() {
            // Return this instance of LocalService so clients can call public methods
            return BackgroundMusicService.this;
        }
    }

}