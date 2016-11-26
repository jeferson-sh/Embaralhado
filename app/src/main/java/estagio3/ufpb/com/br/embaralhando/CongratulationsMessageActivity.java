package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CongratulationsMessageActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView pontosText;
    private ImageButton playbt;
    private ImageButton exitbt;
    private TextView congratulationsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_message);
        imageView = (ImageView)findViewById(R.id.congratulationsImage);
        this.congratulationsMessage = (TextView) findViewById(R.id.congratulationsMessageText);
        pontosText = (TextView) findViewById(R.id.pontosText);
        exitbt = (ImageButton) findViewById(R.id.exitGame);
        playbt = (ImageButton) findViewById(R.id.playAgain);
        Bundle bundle = getIntent().getExtras();
        imageView.setImageResource(bundle.getInt("image"));
        int score = bundle.getInt("score");
        String scoreMessage = "";
        if (score <= 3) {
            if(score == 1){
                this.congratulationsMessage.setText("Que pena!");
                scoreMessage = "Você fez " + score + " Ponto!";
            }else {
                this.congratulationsMessage.setText("Que pena!");
                scoreMessage = "Você fez " + score + " Pontos!";
            }
        }else if(score > 3 && score < 7){
            this.congratulationsMessage.setText("Você pode fazer melhor!");
            scoreMessage = "Você fez " + score + " Pontos!";
        }
        else {
            scoreMessage = "Você fez " + score + " Pontos!";
        }
        pontosText.setText(scoreMessage);
        playbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessageActivity.this,ShuffleGameActivity.class));
                finish();
            }
        });
        exitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessageActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}
