package estagio3.ufpb.com.br.projeto1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return flags;
    }
    @Override
    public void onDestroy() {
        player.stop();
        player.release();
    }

    @Override
    public void onLowMemory() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}