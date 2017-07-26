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
    private ImageButton soundbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon,getTheme()));
        }else{
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.listViewPontos);
        listView.setAdapter(new ScoreAdapter(this));
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(v);

            }
        });
    }

    private void controlMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);
            stopService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white);
            startService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                intent = new Intent(this,CategorieActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op3:
                return true;
            case R.id.op4:
                finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }
    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

