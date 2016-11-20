package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShufflerGameMode extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton soundbt;
    private ImageView imageQuestion;
    private LinearLayout [] drops;
    private LinearLayout dragContainer;
    private ImageView [] letras;
    private int count;
    private Integer nivelAleatorio;
    private List<Palavra> palavras;
    private List<Pontuação> pontuações;
    private int pontos;
    private ImageButton nextWord;
    private static final int FINAL_NIVEL = 10;

    private List<Integer> niveis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffler_game_mode);

        MyOnDragListener myOnDragListener = new MyOnDragListener();
        MyOnLongClickListener myOnLongClickListener = new MyOnLongClickListener();
        PalavrasApplication palavrasApplication = (PalavrasApplication) ShufflerGameMode.this.getApplicationContext();
        this.palavras = palavrasApplication.getPalavras();
        this.pontuações = palavrasApplication.getPontuações();
        this.pontos = 10;
        count = 1;
        this.niveis = shufflerNíveis();
        nivelAleatorio = niveis.get(count);


        ImageButton menubt = (ImageButton) findViewById(R.id.menuButton);
        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);
        ImageButton restartButton = (ImageButton) findViewById(R.id.restart_button);
        ImageButton checkButon = (ImageButton) findViewById(R.id.checkButton);
        this.nextWord = (ImageButton) findViewById(R.id.nextButton);
        this.soundbt = (ImageButton) findViewById(R.id.somButton);

        if(!BackgroundSoundService.ISPLAY)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);

        ImageView letra0 = (ImageView) findViewById(R.id.letra0);
        ImageView letra1 = (ImageView) findViewById(R.id.letra1);
        ImageView letra2 = (ImageView) findViewById(R.id.letra2);
        ImageView letra3 = (ImageView) findViewById(R.id.letra3);
        ImageView letra4 = (ImageView) findViewById(R.id.letra4);
        ImageView letra5 = (ImageView) findViewById(R.id.letra5);
        ImageView letra6 = (ImageView) findViewById(R.id.letra6);
        ImageView letra7 = (ImageView) findViewById(R.id.letra7);
        ImageView letra8 = (ImageView) findViewById(R.id.letra8);
        ImageView letra9 = (ImageView) findViewById(R.id.letra9);
        this.letras = new ImageView[]{letra0, letra1, letra2, letra3, letra4, letra5, letra6, letra7, letra8, letra9};

        LinearLayout drop0 = (LinearLayout) findViewById(R.id.drop0);
        LinearLayout drop1 = (LinearLayout) findViewById(R.id.drop1);
        LinearLayout drop2 = (LinearLayout) findViewById(R.id.drop2);
        LinearLayout drop3 = (LinearLayout) findViewById(R.id.drop3);
        LinearLayout drop4 = (LinearLayout) findViewById(R.id.drop4);
        LinearLayout drop5 = (LinearLayout) findViewById(R.id.drop5);
        LinearLayout drop6 = (LinearLayout) findViewById(R.id.drop6);
        LinearLayout drop7 = (LinearLayout) findViewById(R.id.drop7);
        LinearLayout drop8 = (LinearLayout) findViewById(R.id.drop8);
        LinearLayout drop9 = (LinearLayout) findViewById(R.id.drop9);
        this.drops = new LinearLayout[]{drop0, drop1, drop2, drop3, drop4, drop5, drop6, drop7, drop8, drop9};

        this.dragContainer = (LinearLayout)findViewById(R.id.drag);

        letra0.setOnLongClickListener(myOnLongClickListener);
        letra1.setOnLongClickListener(myOnLongClickListener);
        letra2.setOnLongClickListener(myOnLongClickListener);
        letra3.setOnLongClickListener(myOnLongClickListener);
        letra4.setOnLongClickListener(myOnLongClickListener);
        letra5.setOnLongClickListener(myOnLongClickListener);
        letra6.setOnLongClickListener(myOnLongClickListener);
        letra7.setOnLongClickListener(myOnLongClickListener);
        letra8.setOnLongClickListener(myOnLongClickListener);
        letra9.setOnLongClickListener(myOnLongClickListener);

        drop0.setOnDragListener(myOnDragListener);
        drop1.setOnDragListener(myOnDragListener);
        drop2.setOnDragListener(myOnDragListener);
        drop3.setOnDragListener(myOnDragListener);
        drop4.setOnDragListener(myOnDragListener);
        drop5.setOnDragListener(myOnDragListener);
        drop6.setOnDragListener(myOnDragListener);
        drop7.setOnDragListener(myOnDragListener);
        drop8.setOnDragListener(myOnDragListener);
        drop9.setOnDragListener(myOnDragListener);

        menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);

            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLettersWrong();
            }
        });

        checkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyWord(palavras.get(nivelAleatorio).getPalavra());
            }
        });

        this.nextWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toGetNextWord();
            }
        });
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();

            }
        });

        loadWord(nivelAleatorio);
        setImage(nivelAleatorio);
    }

    private void toGetNextWord(){
        clearNivel();
        setCount();
        nivelAleatorio = niveis.get(count);
        loadWord(nivelAleatorio);
        setImage(nivelAleatorio);
    }

    private List<Integer> shufflerNíveis(){
        List<Integer> aux = new ArrayList<Integer>();
        for(int i = 0; i < palavras.size(); i++){
            aux.add(i);
        }
        Collections.shuffle(aux);
        return aux;
    }

    private void removeLettersWrong() {
        for (int i = 0; i< palavras.get(nivelAleatorio).getPalavra().length();i++){
            if (letras[i].getContentDescription().equals("f")){
                ViewGroup dropLayout = (ViewGroup) letras[i].getParent();
                dropLayout.removeView(letras[i]);
                dragContainer.addView(letras[i]);
                letras[i].setVisibility(View.VISIBLE);
                drops[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void stopMusic() {
        if(BackgroundSoundService.ISPLAY){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(ShufflerGameMode.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(ShufflerGameMode.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = true;
        }
    }

    private void setCount() {
        if(count < FINAL_NIVEL)
            this.count++;
        else {
            adicionarPontuação();
        }
    }

    private void adicionarPontuação() {
        if(this.pontos <= 3){
            this.pontuações.add(new Pontuação(getResources().getDrawable(R.drawable.low_score),1));
        }else if(this.pontos > 3 && this.pontos <= 7){
            this.pontuações.add(new Pontuação(getResources().getDrawable(R.drawable.medium_score),2));
        }else{
            this.pontuações.add(new Pontuação(getResources().getDrawable(R.drawable.hight_score),3));
        }
    }

    private void clearNivel(){
        for(int i = 0; i < letras.length;i++){
            ViewGroup dropLayout = (ViewGroup) letras[i].getParent();
            dropLayout.removeView(letras[i]);
            dragContainer.addView(letras[i]);
            letras[i].setVisibility(View.GONE);
            drops[i].setVisibility(View.GONE);

        }
    }

    private void setImage(int i) {
        this.imageQuestion.setImageBitmap(palavras.get(i).getImage());
    }

    public void loadWord(int pos){
        this.nextWord.setEnabled(false);
        char aux [] = shuffle(palavras.get(pos).getPalavra()).toCharArray();
        char aux2 [] = palavras.get(pos).getPalavra().toCharArray();
        for (int i= 0; i < aux.length; i++){
            String letra = String.valueOf(aux[i]).toUpperCase();
            letras[i].setContentDescription("f");
            drops[i].setTag(aux2[i]);
            switch (letra){
                case "A":
                    letras[i].setImageResource(R.drawable.a);
                    letras[i].setTag("A");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "B":
                    letras[i].setImageResource(R.drawable.b);
                    letras[i].setTag("B");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "C":
                    letras[i].setImageResource(R.drawable.c);
                    letras[i].setTag("C");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "D":
                    letras[i].setImageResource(R.drawable.d);
                    letras[i].setTag("D");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "E":
                    letras[i].setImageResource(R.drawable.e);
                    letras[i].setTag("E");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "F":
                    letras[i].setImageResource(R.drawable.f);
                    letras[i].setTag("F");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "G":
                    letras[i].setImageResource(R.drawable.g);
                    letras[i].setTag("G");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "H":
                    letras[i].setImageResource(R.drawable.h);
                    letras[i].setTag("H");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "I":
                    letras[i].setImageResource(R.drawable.i);
                    letras[i].setTag("I");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "J":
                    letras[i].setImageResource(R.drawable.j);
                    letras[i].setTag("J");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "K":
                    letras[i].setImageResource(R.drawable.k);
                    letras[i].setTag("K");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "L":
                    letras[i].setImageResource(R.drawable.l);
                    letras[i].setTag("L");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "M":
                    letras[i].setImageResource(R.drawable.m);
                    letras[i].setTag("M");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "N":
                    letras[i].setImageResource(R.drawable.n);
                    letras[i].setTag("N");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "O":
                    letras[i].setImageResource(R.drawable.o);
                    letras[i].setTag("O");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "P":
                    letras[i].setImageResource(R.drawable.p);
                    letras[i].setTag("P");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Q":
                    letras[i].setImageResource(R.drawable.q);
                    letras[i].setTag("Q");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "R":
                    letras[i].setImageResource(R.drawable.r);
                    letras[i].setTag("R");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "S":
                    letras[i].setImageResource(R.drawable.s);
                    letras[i].setTag("S");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "T":
                    letras[i].setImageResource(R.drawable.t);
                    letras[i].setTag("T");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "U":
                    letras[i].setImageResource(R.drawable.u);
                    letras[i].setTag("U");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "V":
                    letras[i].setImageResource(R.drawable.v);
                    letras[i].setTag("V");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "W":
                    letras[i].setImageResource(R.drawable.w);
                    letras[i].setTag("W");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "X":
                    letras[i].setImageResource(R.drawable.x);
                    letras[i].setTag("X");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Y":
                    letras[i].setImageResource(R.drawable.y);
                    letras[i].setTag("Y");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Z":
                    letras[i].setImageResource(R.drawable.z);
                    letras[i].setTag("Z");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Ç":
                    letras[i].setImageResource(R.drawable.cc);
                    letras[i].setTag("Ç");
                    letras[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
            }
        }

    }

    private String shuffle(String s) {
        List<String> letras = Arrays.asList(s.split(""));
        Collections.shuffle(letras);
        StringBuilder t = new StringBuilder(s.length());
        for (String k : letras) {
            t.append(k);
        }
        return t.toString();
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
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return false;
        }
    }

    private void VerifyWord(String s) {
        CharSequence correct = "f";
        for (int i = 0; i< s.length();i++){
            if(letras[i].getContentDescription().equals("f")) {
                correct = letras[i].getContentDescription();
                break;
            }else{
                correct = letras[i].getContentDescription();
            }
        }
        if(correct.equals("v")){
            this.nextWord.setEnabled(true);
            MediaPlayer mp = MediaPlayer.create(this,R.raw.correct);
            mp.start();
            mp.setVolume(200,200);
        }else{
            this.pontos--;
            MediaPlayer mp = MediaPlayer.create(this,R.raw.wrong);
            mp.start();
            mp.setVolume(200,200);
            removeLettersWrong();
        }

    }
}
