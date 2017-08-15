package estagio3.ufpb.com.br.embaralhando.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import estagio3.ufpb.com.br.embaralhando.R;

public class CongratulationsMessageActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, Runnable {

    private ImageView imageView;
    private TextView pontosText;
    private ImageButton playbt;
    private ImageButton exitbt;
    private TextView congratulationsMessage;
    private TextToSpeech tts;
    private String scoreMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_message);
        imageView = (ImageView) findViewById(R.id.congratulationsImage);
        this.congratulationsMessage = (TextView) findViewById(R.id.congratulationsMessageText);
        pontosText = (TextView) findViewById(R.id.pontosText);
        exitbt = (ImageButton) findViewById(R.id.exitGame);
        playbt = (ImageButton) findViewById(R.id.playAgain);
        Bundle bundle = getIntent().getExtras();
        imageView.setImageResource(bundle.getInt("image"));
        int score = bundle.getInt("score");
        scoreMessage = "";
        String name = bundle.getString("name");
        if (score <= 50) {
            this.congratulationsMessage.setText("Continue praticando " + name + ".");
            scoreMessage = "Você fez " + score + " Pontos!";

        } else if (score > 50 && score < 70) {
            this.congratulationsMessage.setText("Parabéns " + name + "! Mas pratique um pouco mais!");
            scoreMessage = "Você fez " + score + " Pontos!";
        } else if (score >= 70 && score < 100) {
            this.congratulationsMessage.setText("Muito bom " + name + "! Parabéns!");
            scoreMessage = "Você fez " + score + " Pontos!";
        } else {
            this.congratulationsMessage.setText("Excelente " + name + "!");
            scoreMessage = "Você fez " + score + " Pontos!";
        }
        pontosText.setText(scoreMessage);
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
        tts = new TextToSpeech(this, this);
        Locale brazil = new Locale("PT");
        int codigo = tts.isLanguageAvailable(brazil);
        if (codigo == TextToSpeech.LANG_AVAILABLE) {
            tts.setLanguage(brazil);
            Log.d("Funcionou", "PT BR");
        }
        playbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessageActivity.this, SelectCategoriesToPlayActivity.class));
                finish();
            }
        });
        exitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessageActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void speakMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(congratulationsMessage.getText().toString(), TextToSpeech.QUEUE_ADD, null,null);
            tts.speak(scoreMessage, TextToSpeech.QUEUE_ADD, null,null);
        } else {
            tts.speak(congratulationsMessage.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            tts.speak(scoreMessage, TextToSpeech.QUEUE_ADD, null);
        }


    }

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            Log.d("Tudo certo", "Funcionou");
        }
    }

    @Override
    public void run() {
        speakMessage();
    }

    @Override
    protected void onDestroy() {
        if (this.tts != null) {
            this.tts.stop();
            this.tts.shutdown();
        }
        super.onDestroy();
    }
}
