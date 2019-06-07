package com.mydroidtechnology.embaralhado.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapter.ScoreAdapter;
import com.mydroidtechnology.embaralhado.listener.MyOnDragListener;
import com.mydroidtechnology.embaralhado.listener.MyTouchListener;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.model.Letters;
import com.mydroidtechnology.embaralhado.model.Score;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffleGameActivity extends NavigationControlActivity {

    private ImageView imageQuestion;
    private LinearLayout[] drops;
    private LinearLayout dragContainer;
    private ImageView[] letters;
    private int count;
    private Integer randomLevel;
    private List<Word> words;
    private DataBase dataBase;
    private Double score;
    private double correctCount;
    private TextView textCountLevel;
    private int finalChallenge;
    private boolean verifyWord;
    private List<Integer> levelsIndex;
    private MediaPlayer soundWrong;
    private MediaPlayer soundCorrect;




    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle_game_mode);

        BackgroundMusicService.setStopBackgroundMusicEnable(true);

        MyOnDragListener myOnDragListener = new MyOnDragListener();
        MyTouchListener myTouchListener = new MyTouchListener();
        this.dataBase = new DataBase(this);
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        this.words = dataBase.searchWordsDatabase(bundle.getInt("contextID"));
        this.finalChallenge = 10;

        if (words.size() < finalChallenge) {
            finalChallenge = words.size();
        }
        this.score = (double) words.size();
        this.correctCount = 0;
        count = 0;
        this.levelsIndex = shuffleLevelIndex();
        randomLevel = levelsIndex.get(count);
        this.verifyWord = false;


        this.imageQuestion = findViewById(R.id.imageQuestion);
        ImageButton restartButton = findViewById(R.id.restart_button);
        ImageButton checkWordButton = findViewById(R.id.checkButton);
        this.textCountLevel = findViewById(R.id.textCountNivel);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_shuffler_game_mode);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Organize as letras");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }

        ImageView letter0 = findViewById(R.id.letter0);
        ImageView letter1 = findViewById(R.id.letter1);
        ImageView letter2 = findViewById(R.id.letter2);
        ImageView letter3 = findViewById(R.id.letter3);
        ImageView letter4 = findViewById(R.id.letter4);
        ImageView letter5 = findViewById(R.id.letter5);
        ImageView letter6 = findViewById(R.id.letter6);
        ImageView letter7 = findViewById(R.id.letter7);
        ImageView letter8 = findViewById(R.id.letter8);
        ImageView letter9 = findViewById(R.id.letter9);
        this.letters = new ImageView[]{letter0, letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9};

        LinearLayout drop0 = findViewById(R.id.drop0);
        LinearLayout drop1 = findViewById(R.id.drop1);
        LinearLayout drop2 = findViewById(R.id.drop2);
        LinearLayout drop3 = findViewById(R.id.drop3);
        LinearLayout drop4 = findViewById(R.id.drop4);
        LinearLayout drop5 = findViewById(R.id.drop5);
        LinearLayout drop6 = findViewById(R.id.drop6);
        LinearLayout drop7 = findViewById(R.id.drop7);
        LinearLayout drop8 = findViewById(R.id.drop8);
        LinearLayout drop9 = findViewById(R.id.drop9);
        this.drops = new LinearLayout[]{drop0, drop1, drop2, drop3, drop4, drop5, drop6, drop7, drop8, drop9};

        this.dragContainer = findViewById(R.id.drag);


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

        this.soundWrong = MediaPlayer.create(this, R.raw.wrong);
        this.soundCorrect = MediaPlayer.create(this, R.raw.correct);



        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeLettersWrong();
            }
        });

        checkWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyWord(words.get(randomLevel).getName());
            }
        });

        loadWord(randomLevel);
        setImage(randomLevel);
    }

    private void toGetNextWord() {
        setCount();
        if (count == finalChallenge) {
            inputName();
        } else {
            clearLevel();
            randomLevel = levelsIndex.get(count);
            loadWord(randomLevel);
            setImage(randomLevel);
        }
    }

    private void inputName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        builder.setCancelable(true);
        builder.setTitle("Qual é o seu nome?");
        builder.setView(editText);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startCongratulationMessage(editText.getText().toString());
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private List<Integer> shuffleLevelIndex() {
        List<Integer> aux = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            aux.add(i);
        }
        Collections.shuffle(aux);
        return aux;
    }

    private void removeLettersWrong() {
        for (int i = 0; i < words.get(randomLevel).getName().length(); i++) {
            if (letters[i].getContentDescription().equals("f")) {
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

    private void setCount() {
        if (count < finalChallenge) {
            this.count++;
        }
    }

    public void startCongratulationMessage(String user) {
        int drawId = getIdImageViewScore();
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(drawId);
        Bundle bundle1 = getIntent().getExtras();
        assert bundle1 != null;
        Integer contextID = bundle1.getInt("contextID");
        Score p = new Score(imageView.getDrawable(), this.score, user, contextID, (int) correctCount, this.finalChallenge);
        insertScore(p, contextID);
        Intent intent = new Intent(ShuffleGameActivity.this, CongratulationsMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image", drawId);
        bundle.putDouble("score", this.score);
        bundle.putString("name", user);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private int getIdImageViewScore() {
        setScore(this.correctCount / this.finalChallenge * 100);
        if (score < 50.0) {
            return R.drawable.low_score;
        } else if (this.score >= 50.0 && this.score <= 70.0) {
            return R.drawable.medium_score;
        } else {
            return R.drawable.hight_score;
        }
    }

    private void insertScore(Score p, Integer contextID) {
        ScoreAdapter scoreAdapter = new ScoreAdapter(this, contextID);
        if (scoreAdapter.getCount() >= 10) {
            dataBase.deleteScore((Score) scoreAdapter.getItem(scoreAdapter.getCount() - 1));
        }
        dataBase.insertScore(p);
        Category category = dataBase.searchCategoryDatabase(contextID);
        category.setScores("true");
        dataBase.updateCategory(category);
    }

    private void clearLevel() {
        for (int i = 0; i < letters.length; i++) {
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

    private void loadLetter(ImageView letterImageView, int drawableCodID, String letter, LinearLayout drop ){
        letterImageView.setImageResource(drawableCodID);
        letterImageView.setTag(letter);
        letterImageView.setVisibility(View.VISIBLE);
        drop.setVisibility(View.VISIBLE);
    }

    public void loadWord(int pos) {
        setVerifyWord(false);
        int challenge = count + 1;
        String n = "Desafio " + challenge + " de " + finalChallenge;
        this.textCountLevel.setText(n);
        String p = shuffle(words.get(pos).getName());
        if (p.equals(words.get(pos).getName())) {
            p = shuffle(p);
        }
        char[] aux = p.toCharArray();
        char[] aux2 = words.get(pos).getName().toCharArray();
        for (int i = 0; i < aux.length; i++) {
            String letter = String.valueOf(aux[i]).toUpperCase();
            letters[i].setContentDescription("f");
            letters[i].setEnabled(true);
            drops[i].setTag(aux2[i]);
            drops[i].setEnabled(true);
            switch (letter) {
                case Letters.A:
                    loadLetter(letters[i],R.drawable.a,Letters.A,drops[i]);
                    continue;
                case Letters.B:
                    loadLetter(letters[i],R.drawable.b,Letters.B,drops[i]);
                    continue;
                case Letters.C:
                    loadLetter(letters[i],R.drawable.c,Letters.C,drops[i]);
                    continue;
                case Letters.D:
                    loadLetter(letters[i],R.drawable.d,Letters.D,drops[i]);
                    continue;
                case Letters.E:
                    loadLetter(letters[i],R.drawable.e,Letters.E,drops[i]);
                    continue;
                case Letters.F:
                    loadLetter(letters[i],R.drawable.f,Letters.F,drops[i]);
                    continue;
                case Letters.G:
                    loadLetter(letters[i],R.drawable.g,Letters.G,drops[i]);
                    continue;
                case Letters.H:
                    loadLetter(letters[i],R.drawable.h,Letters.H,drops[i]);
                    continue;
                case Letters.I:
                    loadLetter(letters[i],R.drawable.i,Letters.I,drops[i]);
                    continue;
                case Letters.J:
                    loadLetter(letters[i],R.drawable.j,Letters.J,drops[i]);
                    continue;
                case Letters.K:
                    loadLetter(letters[i],R.drawable.k,Letters.K,drops[i]);
                    continue;
                case Letters.L:
                    loadLetter(letters[i],R.drawable.l,Letters.L,drops[i]);
                    continue;
                case Letters.M:
                    loadLetter(letters[i],R.drawable.m,Letters.M,drops[i]);
                    continue;
                case Letters.N:
                    loadLetter(letters[i],R.drawable.n,Letters.N,drops[i]);
                    continue;
                case Letters.O:
                    loadLetter(letters[i],R.drawable.o,Letters.O,drops[i]);
                    continue;
                case Letters.P:
                    loadLetter(letters[i],R.drawable.p,Letters.P,drops[i]);
                    continue;
                case Letters.Q:
                    loadLetter(letters[i],R.drawable.q,Letters.Q,drops[i]);
                    continue;
                case Letters.R:
                    loadLetter(letters[i],R.drawable.r,Letters.R,drops[i]);
                    continue;
                case Letters.S:
                    loadLetter(letters[i],R.drawable.s,Letters.S,drops[i]);
                    continue;
                case Letters.T:
                    loadLetter(letters[i],R.drawable.t,Letters.T,drops[i]);
                    continue;
                case Letters.U:
                    loadLetter(letters[i],R.drawable.u,Letters.U,drops[i]);
                    continue;
                case Letters.V:
                    loadLetter(letters[i],R.drawable.v,Letters.V,drops[i]);
                    continue;
                case Letters.W:
                    loadLetter(letters[i],R.drawable.w,Letters.W,drops[i]);
                    continue;
                case Letters.X:
                    loadLetter(letters[i],R.drawable.x,Letters.X,drops[i]);
                    continue;
                case Letters.Y:
                    loadLetter(letters[i],R.drawable.y,Letters.Y,drops[i]);
                    continue;
                case Letters.Z:
                    loadLetter(letters[i],R.drawable.z,Letters.Z,drops[i]);
                    continue;
                case Letters.Ç:
                    loadLetter(letters[i],R.drawable.cc,Letters.Ç,drops[i]);
                    continue;
                case Letters.Á:
                    loadLetter(letters[i],R.drawable.aa,Letters.Á,drops[i]);
                    continue;
                case Letters.Â:
                    loadLetter(letters[i],R.drawable.aaa,Letters.Â,drops[i]);
                    continue;
                case Letters.Ã:
                    loadLetter(letters[i],R.drawable.aaaa,Letters.Ã,drops[i]);
                    continue;
                case Letters.É:
                    loadLetter(letters[i],R.drawable.ee,Letters.É,drops[i]);
                    continue;
                case Letters.Ê:
                    loadLetter(letters[i],R.drawable.eee,Letters.Ê,drops[i]);
                    continue;
                case Letters.Í:
                    loadLetter(letters[i],R.drawable.ii,Letters.Í,drops[i]);
                    continue;
                case Letters.Ó:
                    loadLetter(letters[i],R.drawable.oo,Letters.Ó,drops[i]);
                    continue;
                case Letters.Ô:
                    loadLetter(letters[i],R.drawable.ooo,Letters.Ô,drops[i]);
                    continue;
                case Letters.Õ:
                    loadLetter(letters[i],R.drawable.oooo,Letters.Õ,drops[i]);
                    continue;
                case Letters.Ú:
                    loadLetter(letters[i],R.drawable.uu,Letters.Ú,drops[i]);
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



    private void verifyWord(String s) {
        CharSequence verifyWordValue = "";
        boolean isAllDropedLetter = true;
        for (int i = 0; i < s.length(); i++) {
            if(letters[i].isEnabled()){
                isAllDropedLetter = false;
                break;
            }
        }
        for (int i = 0; i < s.length(); i++) {
            if (letters[i].getContentDescription().equals("f")) {
                verifyWordValue = letters[i].getContentDescription();
                break;
            } else {
                verifyWordValue = letters[i].getContentDescription();
            }
        }
        if (verifyWordValue.equals("v")) {
            if (!verifyWord) {
                this.correctCount = correctCount + 1;
                setVerifyWord(true);
            }
            startSoundQuestionCorrect();
            toGetNextWord();

        } else {
            if(isAllDropedLetter) {
                setVerifyWord(true);
                starSoundQuestionWrong();
                removeLettersWrong();
            }
        }

    }

    private void starSoundQuestionWrong() {
        this.soundWrong.seekTo(1000);
        this.soundWrong.start();
        this.soundWrong.setVolume(300, 300);
        this.soundWrong.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void startSoundQuestionCorrect() {
        this.soundCorrect.start();
        this.soundCorrect.setVolume(200, 200);
        this.soundCorrect.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void setVerifyWord(boolean verifyWord) {
        this.verifyWord = verifyWord;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    protected void startActivityOnBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair?");
        builder.setMessage("Todo o progresso será perdido.");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ShuffleGameActivity.this, MainActivity.class);
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                startActivity(intent);
                finish();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void startMainActivity(){
        this.startActivityOnBackPressed();
    }
}