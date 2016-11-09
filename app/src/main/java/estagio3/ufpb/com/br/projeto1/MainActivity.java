package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    private ImageButton playButton;
    private ImageButton settingsButton;
    private ImageButton scoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.playButton = (ImageButton) findViewById(R.id.playButton);
        this.settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        this.scoreButton = (ImageButton) findViewById(R.id.scoreButton);
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
}
