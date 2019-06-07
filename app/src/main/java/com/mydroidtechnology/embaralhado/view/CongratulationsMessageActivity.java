package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

import java.text.MessageFormat;
import java.util.Locale;

public class CongratulationsMessageActivity extends AppCompatActivity implements TextToSpeech.OnInitListener, Runnable {

    private TextView congratulationsMessage;
    private TextToSpeech textToSpeech;
    private String scoreMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_message);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        ImageView imageView = findViewById(R.id.congratulationsImage);
        this.congratulationsMessage = findViewById(R.id.congratulationsMessageText);
        TextView pontosText = findViewById(R.id.pontosText);
        ImageButton exitbt = findViewById(R.id.exitGame);
        ImageButton playbt = findViewById(R.id.playAgain);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        imageView.setImageResource(bundle.getInt("image"));
        double score = bundle.getDouble("score");
        scoreMessage = "";
        String name = bundle.getString("name");
        if (score < 50) {
            this.congratulationsMessage.setText(MessageFormat.format("Continue praticando {0}.", name));
        } else if (score >= 50 && score < 70) {
            this.congratulationsMessage.setText(MessageFormat.format("Parabéns {0}! Mas pratique um pouco mais!", name));
        } else if (score >= 70 && score < 100) {
            this.congratulationsMessage.setText(MessageFormat.format("Muito bom {0}! Parabéns!", name));
        } else {
            this.congratulationsMessage.setText(MessageFormat.format("Excelente {0}!", name));
        }
        pontosText.setText(scoreMessage);
        Handler handler = new Handler();
        handler.postDelayed(this, 2000);
        textToSpeech = new TextToSpeech(this, this);
        Locale brazil = new Locale("PT");
        int codigo = textToSpeech.isLanguageAvailable(brazil);
        if (codigo == TextToSpeech.LANG_AVAILABLE) {
            textToSpeech.setLanguage(brazil);
            Log.d("Funcionou", "PT BR");
        }
        playbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                startActivity(new Intent(CongratulationsMessageActivity.this, SelectCategoriesToPlayActivity.class));
                finish();
            }
        });
        exitbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                startActivity(new Intent(CongratulationsMessageActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void speakMessage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(congratulationsMessage.getText().toString(), TextToSpeech.QUEUE_ADD, null, null);
            textToSpeech.speak(scoreMessage, TextToSpeech.QUEUE_ADD, null, null);
        } else {
            textToSpeech.speak(congratulationsMessage.getText().toString(), TextToSpeech.QUEUE_ADD, null);
            textToSpeech.speak(scoreMessage, TextToSpeech.QUEUE_ADD, null);
        }


    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            run();
        }
    }

    @Override
    public void run() {
        speakMessage();
    }

    @Override
    protected void onDestroy() {
        if (this.textToSpeech != null) {
            this.textToSpeech.stop();
            this.textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}
