package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

public class ScoreActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private ListView listView;
    private ImageButton menubt;
    private ImageButton soundbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        if(BackgroundSoundService.ISPLAY)
            startService(new Intent(this,BackgroundSoundService.class));
        this.menubt = (ImageButton) findViewById(R.id.menuButton);
        this.soundbt = (ImageButton) findViewById(R.id.somButton);
        if(!BackgroundSoundService.ISPLAY)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);

        listView = (ListView) findViewById(R.id.listViewPontos);
        listView.setAdapter(new PontuaçãoAdapter(this));
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

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    private void stopMusic(View v) {
        if(BackgroundSoundService.ISPLAY){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(ScoreActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = true;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

