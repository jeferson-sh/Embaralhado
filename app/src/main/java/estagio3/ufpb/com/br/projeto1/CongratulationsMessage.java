package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CongratulationsMessage extends AppCompatActivity {

    private ImageView imageView;
    private TextView pontosText;
    private ImageButton playbt;
    private ImageButton exitbt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_message);
        imageView = (ImageView)findViewById(R.id.congratulations);
        pontosText = (TextView) findViewById(R.id.pontosText);
        exitbt = (ImageButton) findViewById(R.id.exitGame);
        playbt = (ImageButton) findViewById(R.id.playAgain);
        Bundle bundle = getIntent().getExtras();
        imageView.setImageResource(bundle.getInt("image"));
        int score = bundle.getInt("score");
        String scoreMessage = "";
        if (score > 1)
            scoreMessage = "Você fez "+score+" Pontos!";
        else scoreMessage = "Você fez "+score+" Ponto!";
        pontosText.setText(scoreMessage);
        playbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessage.this,ShuffleGameMode.class));
                finish();
            }
        });
        exitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessage.this,MainActivity.class));
                finish();
            }
        });
    }



}
