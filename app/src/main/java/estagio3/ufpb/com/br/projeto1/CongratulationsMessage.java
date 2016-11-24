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
    private ImageButton play;
    private ImageButton exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulations_message);
        imageView = (ImageView)findViewById(R.id.congratulations);
        pontosText = (TextView) findViewById(R.id.pontosText);
        exit = (ImageButton) findViewById(R.id.exitGame);
        play = (ImageButton) findViewById(R.id.playAgain);
        Bundle bundle = getIntent().getExtras();
        imageView.setImageResource(bundle.getInt("image"));
        int pontos = bundle.getInt("pontos");
        String pontuação = "";
        if (pontos > 1)
            pontuação = "Parabéns você fez "+pontos+" Pontos!";
        else pontuação = "Parabéns você fez "+pontos+" Ponto!";
        pontosText.setText(pontuação);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessage.this,ShufflerGameMode.class));
                finish();
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CongratulationsMessage.this,MainActivity.class));
                finish();
            }
        });
    }



}
