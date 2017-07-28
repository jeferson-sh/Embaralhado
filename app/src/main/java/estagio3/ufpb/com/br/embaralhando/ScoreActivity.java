package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class ScoreActivity extends AppCompatActivity{

    private ListView listView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));

        //toolbar
        this.toolbar = (Toolbar) findViewById(R.id.toolbar_score);
        setSupportActionBar(toolbar);
        this.toolbar.inflateMenu(R.menu.main_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Últimas pontuações");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        listView = (ListView) findViewById(R.id.listViewPontos);
        listView.setAdapter(new ScoreAdapter(this));
    }

    private void startMainActivity() {
        Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundService.PLAYING) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            stopService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            startService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!BackgroundSoundService.PLAYING) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startMainActivity();
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

