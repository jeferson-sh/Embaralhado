package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

public class ShuffleGameActivity extends AppCompatActivity {

    private ImageButton soundbt;
    private ImageView imageQuestion;
    private LinearLayout [] drops;
    private LinearLayout dragContainer;
    private ImageView [] letters;
    private int count;
    private Integer randomLevel;
    private List<Word> words;
    private DataBase dataBase;
    private int score;
    private TextView textCountLevel;
    private int finalLevel;

    private List<Integer> levelsIndex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle_game_mode);

        MyOnDragListener myOnDragListener = new MyOnDragListener();
        MyTouchListener myTouchListener = new MyTouchListener();
        this.dataBase = new DataBase(this);
        Bundle bundle = getIntent().getExtras();
        this.words = dataBase.searchWordsDatabase(bundle.getString("nameContext"));
        this.finalLevel = 10;

        if(words.size() < finalLevel){
            finalLevel = words.size();
        }
        this.score = 10;
        count = 0;
        this.levelsIndex = shuffleLevelIndex();
        randomLevel = levelsIndex.get(count);



        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);
        ImageButton restartButton = (ImageButton) findViewById(R.id.restart_button);
        ImageButton checkButon = (ImageButton) findViewById(R.id.checkButton);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.textCountLevel =(TextView) findViewById(R.id.textCountNivel);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon,getTheme()));
        }else{
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShuffleGameActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);

        ImageView letter0 = (ImageView) findViewById(R.id.letter0);
        ImageView letter1 = (ImageView) findViewById(R.id.letter1);
        ImageView letter2 = (ImageView) findViewById(R.id.letter2);
        ImageView letter3 = (ImageView) findViewById(R.id.letter3);
        ImageView letter4 = (ImageView) findViewById(R.id.letter4);
        ImageView letter5 = (ImageView) findViewById(R.id.letter5);
        ImageView letter6 = (ImageView) findViewById(R.id.letter6);
        ImageView letter7 = (ImageView) findViewById(R.id.letter7);
        ImageView letter8 = (ImageView) findViewById(R.id.letter8);
        ImageView letter9 = (ImageView) findViewById(R.id.letter9);
        this.letters = new ImageView[]{letter0, letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9};

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

        letter0.setOnTouchListener(myTouchListener);
        letter1.setOnTouchListener(myTouchListener);
        letter2.setOnTouchListener(myTouchListener);
        letter3.setOnTouchListener(myTouchListener);
        letter4.setOnTouchListener(myTouchListener);
        letter5.setOnTouchListener(myTouchListener);
        letter6.setOnTouchListener(myTouchListener);
        letter7.setOnTouchListener(myTouchListener);
        letter8.setOnTouchListener(myTouchListener);
        letter9.setOnTouchListener(myTouchListener);

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
                controlMusic();

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
            randomLevel = levelsIndex.get(count);
            loadWord(randomLevel);
            setImage(randomLevel);
        }
    }

    private List<Integer> shuffleLevelIndex(){
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

    private void controlMusic() {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);
            stopService(new Intent(ShuffleGameActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white);
            startService(new Intent(ShuffleGameActivity.this, BackgroundSoundService.class));
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
            if(this.score < 0){
                this.score = 0;
            }
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
        Intent intent = new Intent(ShuffleGameActivity.this,CongratulationsMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image",drawId);
        bundle.putInt("score", score);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private void insertScore(Score p){
        ScoreAdapter scoreAdapter = new ScoreAdapter(this);
        if(scoreAdapter.getCount() >= 10) {
            dataBase.deleteScore((Score) scoreAdapter.getItem(0));
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
            String letter = String.valueOf(aux[i]).toUpperCase();
            letters[i].setContentDescription("f");
            letters[i].setEnabled(true);
            drops[i].setTag(aux2[i]);
            drops[i].setEnabled(true);
            switch (letter){
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                intent = new Intent(this, ContextsActivity.class);
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

    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }
}