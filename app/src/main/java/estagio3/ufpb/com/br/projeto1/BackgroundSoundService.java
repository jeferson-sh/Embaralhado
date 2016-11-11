package estagio3.ufpb.com.br.projeto1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class BackgroundSoundService extends Service {

    private MediaPlayer player;
    protected static boolean ISPLAY = true;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.back_step);
        player.setLooping(true); // Set looping
        player.setVolume(100,100);
    }

    public IBinder onBind(Intent arg0) {

        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return flags;
    }

    public void onStart(Intent intent, int startId) {
        // TO DO
    }
    public IBinder onUnBind(Intent arg0) {
        return null;
    }

    public void onStop() {

    }
    public void onPause() {

    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {

    }
}