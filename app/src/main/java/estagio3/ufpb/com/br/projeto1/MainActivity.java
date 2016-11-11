package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton playButton;
    private ImageButton settingsButton;
    private ImageButton scoreButton;
    private ImageButton menubt;
    private ImageButton soundbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(BackgroundSoundService.ISPLAY)
            startService(new Intent(this,BackgroundSoundService.class));
        this.menubt = (ImageButton) findViewById(R.id.menuButton);
        this.soundbt = (ImageButton) findViewById(R.id.somButton);
        this.playButton = (ImageButton) findViewById(R.id.playButton);
        this.settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        this.scoreButton = (ImageButton) findViewById(R.id.scoreButton);
        if(!BackgroundSoundService.ISPLAY)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);
        this.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGameModeActivity();
            }
        });
        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });
        this.scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScoreActivity();
            }
        });
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic(v);

            }
        });
        this.menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);


            }
        });
    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                return true;
            default:
                return false;
        }
    }

    private void stopMusic(View v) {
        if(BackgroundSoundService.ISPLAY){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(MainActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(MainActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = true;
        }
    }

    private void startGameModeActivity(){
        Intent intent = new Intent(this,GameModeActivity.class);
        startActivity(intent);
        finish();
    }
    private void startSettingsActivity(){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
        finish();
    }
    private void startScoreActivity(){
        Intent intent = new Intent(this,ScoreActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
