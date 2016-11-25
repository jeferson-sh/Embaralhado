package estagio3.ufpb.com.br.embaralhando;

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

public class ShuffleGameMode extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private ImageButton soundbt;
    private ImageView imageQuestion;
    private LinearLayout [] drops;
    private LinearLayout dragContainer;
    private ImageView [] letters;
    private int count;
    private Integer randomLevel;
    private List<Word> words;
    private List<Score> scores;
    private DataBase dataBase;
    private int score;
    private TextView textCountLevel;
    private int finalLevel;

    private List<Integer> niveis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffler_game_mode);

        MyOnDragListener myOnDragListener = new MyOnDragListener();
        MyTouchListener myTouchListener = new MyTouchListener();
        this.dataBase = new DataBase(this);
        this.words = dataBase.searchWordsDatabase();
        this.scores = dataBase.searchScoresDatabase();
        this.finalLevel = 10;

        if(words.size() < finalLevel){
            finalLevel = words.size();
        }
        this.score = 10;
        count = 0;
        this.niveis = shufflerLevelIndex();
        randomLevel = niveis.get(count);


        ImageButton menubt = (ImageButton) findViewById(R.id.menuButton);
        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);
        ImageButton restartButton = (ImageButton) findViewById(R.id.restart_button);
        ImageButton checkButon = (ImageButton) findViewById(R.id.checkButton);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.textCountLevel =(TextView) findViewById(R.id.textCountNivel);

        if(!BackgroundSoundService.PLAYING)
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
        this.letters = new ImageView[]{letra0, letra1, letra2, letra3, letra4, letra5, letra6, letra7, letra8, letra9};

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

        letra0.setOnTouchListener(myTouchListener);
        letra1.setOnTouchListener(myTouchListener);
        letra2.setOnTouchListener(myTouchListener);
        letra3.setOnTouchListener(myTouchListener);
        letra4.setOnTouchListener(myTouchListener);
        letra5.setOnTouchListener(myTouchListener);
        letra6.setOnTouchListener(myTouchListener);
        letra7.setOnTouchListener(myTouchListener);
        letra8.setOnTouchListener(myTouchListener);
        letra9.setOnTouchListener(myTouchListener);

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
                VerifyWord(words.get(randomLevel).getName());
            }
        });
        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();

            }
        });

        loadWord(randomLevel);
        setImage(randomLevel);
    }

    private void toGetNextWord(){
        setCount();
        if(count == finalLevel){
            congratulationMessage();
        }else {
            clearLevel();
            randomLevel = niveis.get(count);
            loadWord(randomLevel);
            setImage(randomLevel);
        }
    }

    private List<Integer> shufflerLevelIndex(){
        List<Integer> aux = new ArrayList<>();
        for(int i = 0; i < words.size(); i++){
            aux.add(i);
        }
        Collections.shuffle(aux);
        return aux;
    }

    private void removeLettersWrong() {
        for (int i = 0; i< words.get(randomLevel).getName().length(); i++){
            if (letters[i].getContentDescription().equals("f")){
                ViewGroup dropLayout = (ViewGroup) letters[i].getParent();
                dropLayout.setEnabled(true);
                dropLayout.removeView(letters[i]);
                dragContainer.addView(letters[i]);
                letters[i].setVisibility(View.VISIBLE);
                letters[i].setEnabled(true);
                drops[i].setVisibility(View.VISIBLE);
            }
        }
    }

    private void stopMusic() {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(ShuffleGameMode.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(ShuffleGameMode.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    private void setCount() {
        if(count < finalLevel) {
            this.count++;
        }
    }

    public void congratulationMessage() {
        int drawId = 0;
        ImageView imageView = new ImageView(this);
        if(this.score <= 3) {
            drawId = R.drawable.low_score;
            imageView.setImageResource(drawId);
        }
        else if(this.score > 3 && this.score < 7) {
            drawId = R.drawable.medium_score;
            imageView.setImageResource(drawId);
        }
        else  {
            drawId = R.drawable.hight_score;
            imageView.setImageResource(drawId);
        }
        Score p = new Score(imageView.getDrawable(), score);
        insertScore(p);
        Intent intent = new Intent(ShuffleGameMode.this,CongratulationsMessage.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image",drawId);
        bundle.putInt("score", score);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void insertScore(Score p){
        if(scores.size() >= 11) {
            scores.remove(0);
            dataBase.deleteScore(1);
        }
        dataBase.insertScore(p);
    }

    private void clearLevel(){
        for(int i = 0; i < letters.length; i++){
            ViewGroup dropLayout = (ViewGroup) letters[i].getParent();
            dropLayout.removeView(letters[i]);
            dragContainer.addView(letters[i]);
            letters[i].setVisibility(View.GONE);
            letters[i].setEnabled(true);
            drops[i].setVisibility(View.GONE);
            drops[i].setEnabled(true);

        }
    }

    private void setImage(int i) {
        this.imageQuestion.setImageBitmap(words.get(i).getImage());
    }

    public void loadWord(int pos){
        String n = "";
        int nivel = count+1;
        n = "Nível "+nivel+" de "+ finalLevel;
        this.textCountLevel.setText(n);
        String p = shuffle(words.get(pos).getName());
        while(p.equals(words.get(pos).getName())) {
            p = shuffle(words.get(pos).getName());
        }
        char aux [] = p.toCharArray();
        char aux2 [] = words.get(pos).getName().toCharArray();
        for (int i= 0; i < aux.length; i++){
            String letra = String.valueOf(aux[i]).toUpperCase();
            letters[i].setContentDescription("f");
            letters[i].setEnabled(true);
            drops[i].setTag(aux2[i]);
            drops[i].setEnabled(true);
            switch (letra){
                case "A":
                    letters[i].setImageResource(R.drawable.a);
                    letters[i].setTag("A");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "B":
                    letters[i].setImageResource(R.drawable.b);
                    letters[i].setTag("B");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "C":
                    letters[i].setImageResource(R.drawable.c);
                    letters[i].setTag("C");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "D":
                    letters[i].setImageResource(R.drawable.d);
                    letters[i].setTag("D");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "E":
                    letters[i].setImageResource(R.drawable.e);
                    letters[i].setTag("E");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "F":
                    letters[i].setImageResource(R.drawable.f);
                    letters[i].setTag("F");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "G":
                    letters[i].setImageResource(R.drawable.g);
                    letters[i].setTag("G");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "H":
                    letters[i].setImageResource(R.drawable.h);
                    letters[i].setTag("H");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "I":
                    letters[i].setImageResource(R.drawable.i);
                    letters[i].setTag("I");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "J":
                    letters[i].setImageResource(R.drawable.j);
                    letters[i].setTag("J");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "K":
                    letters[i].setImageResource(R.drawable.k);
                    letters[i].setTag("K");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "L":
                    letters[i].setImageResource(R.drawable.l);
                    letters[i].setTag("L");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "M":
                    letters[i].setImageResource(R.drawable.m);
                    letters[i].setTag("M");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "N":
                    letters[i].setImageResource(R.drawable.n);
                    letters[i].setTag("N");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "O":
                    letters[i].setImageResource(R.drawable.o);
                    letters[i].setTag("O");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "P":
                    letters[i].setImageResource(R.drawable.p);
                    letters[i].setTag("P");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Q":
                    letters[i].setImageResource(R.drawable.q);
                    letters[i].setTag("Q");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "R":
                    letters[i].setImageResource(R.drawable.r);
                    letters[i].setTag("R");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "S":
                    letters[i].setImageResource(R.drawable.s);
                    letters[i].setTag("S");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "T":
                    letters[i].setImageResource(R.drawable.t);
                    letters[i].setTag("T");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "U":
                    letters[i].setImageResource(R.drawable.u);
                    letters[i].setTag("U");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "V":
                    letters[i].setImageResource(R.drawable.v);
                    letters[i].setTag("V");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "W":
                    letters[i].setImageResource(R.drawable.w);
                    letters[i].setTag("W");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "X":
                    letters[i].setImageResource(R.drawable.x);
                    letters[i].setTag("X");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Y":
                    letters[i].setImageResource(R.drawable.y);
                    letters[i].setTag("Y");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Z":
                    letters[i].setImageResource(R.drawable.z);
                    letters[i].setTag("Z");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case "Ç":
                    letters[i].setImageResource(R.drawable.cc);
                    letters[i].setTag("Ç");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Á":
                    letters[i].setImageResource(R.drawable.aa);
                    letters[i].setTag("Á");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Â":
                    letters[i].setImageResource(R.drawable.aaa);
                    letters[i].setTag("Â");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Ã":
                    letters[i].setImageResource(R.drawable.aaaa);
                    letters[i].setTag("Ã");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "É":
                    letters[i].setImageResource(R.drawable.ee);
                    letters[i].setTag("É");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Ê":
                    letters[i].setImageResource(R.drawable.eee);
                    letters[i].setTag("Ê");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Í":
                    letters[i].setImageResource(R.drawable.ii);
                    letters[i].setTag("Í");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Ó":
                    letters[i].setImageResource(R.drawable.oo);
                    letters[i].setTag("Ó");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Ô":
                    letters[i].setImageResource(R.drawable.ooo);
                    letters[i].setTag("Ô");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Õ":
                    letters[i].setImageResource(R.drawable.oooo);
                    letters[i].setTag("Õ");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case "Ú":
                    letters[i].setImageResource(R.drawable.uu);
                    letters[i].setTag("Ú");
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
            }
        }

    }

    private String shuffle(String s) {
        List<String> letters = Arrays.asList(s.split(""));
        Collections.shuffle(letters);
        StringBuilder t = new StringBuilder(s.length());
        for (String k : letters) {
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
            case R.id.op2:
                intent = new Intent(this,SettingsActivity.class);
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

    private void VerifyWord(String s) {
        CharSequence verify = "";
        for (int i = 0; i< s.length();i++){
            if(letters[i].getContentDescription().equals("f")) {
                verify = letters[i].getContentDescription();
                break;
            }else{
                verify = letters[i].getContentDescription();
            }
        }
        if(verify.equals("v")){
            startSoundQuestionCorrect();
            toGetNextWord();
        }else{
            this.score--;
            starSoundQuestionWrong();
            removeLettersWrong();
        }

    }

    private void starSoundQuestionWrong(){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
            mp.seekTo(1000);
            mp.start();
            mp.setVolume(300, 300);
    }

    private void startSoundQuestionCorrect(){
            MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
            mp.start();
            mp.setVolume(200, 200);
    }
}