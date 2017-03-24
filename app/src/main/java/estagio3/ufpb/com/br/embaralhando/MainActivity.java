package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton playButton;
    private ImageButton settingsButton;
    private ImageButton scoreButton;
    private ImageButton soundbt;
    private DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_24dp,getTheme()));
        }else{
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_24dp));
        }

        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.playButton = (ImageButton) findViewById(R.id.playButton);
        this.settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        this.scoreButton = (ImageButton) findViewById(R.id.scoreButton);
        this.dataBase = new DataBase(this);
        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white_24dp);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dataBase.searchWordsDatabase("asdgasgdsdfhgdfghdfgjfgjhfghksdfgASDF").isEmpty()){
                    Snackbar.make(view, "Não existem palavras cadastradas!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                }else{
                    startGame();
                    finish();
                }

            }
        });
        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
                finish();
            }
        });
        this.scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScoreActivity();
                finish();
            }
        });
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(v);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                return true;
            case R.id.op2:
                Intent intent = new Intent(this,ContextsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op3:
                intent = new Intent(this,ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op4:
                finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void controlMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white_24dp);
            stopService(new Intent(MainActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white_24dp);
            startService(new Intent(MainActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    private void startGame(){
        Intent intent = new Intent(this,ShuffleGameActivity.class);
        startActivity(intent);
        finish();
    }
    private void startSettingsActivity(){
        Intent intent = new Intent(this,ContextsActivity.class);
        startActivity(intent);
    }
    private void startScoreActivity(){
        Intent intent = new Intent(this,ScoreActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}
