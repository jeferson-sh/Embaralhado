package estagio3.ufpb.com.br.embaralhando;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BackgroundSoundService extends Service {

    private MediaPlayer player;
    protected static boolean PLAYING = true;

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.back_step);
        player.setLooping(true);
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
        super.onLowMemory();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}