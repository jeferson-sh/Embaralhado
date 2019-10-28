package com.mydroidtechnology.embaralhado.views;

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
import com.mydroidtechnology.embaralhado.adapters.ScoreModelAdapter;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.listeners.MyOnDragListener;
import com.mydroidtechnology.embaralhado.listeners.MyTouchListener;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.models.Character;
import com.mydroidtechnology.embaralhado.models.ScoreModel;
import com.mydroidtechnology.embaralhado.models.WordModel;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ShuffledGameActivity extends NavigationControlActivity {

    private ImageView imageChallenge;
    private LinearLayout[] drops;
    private LinearLayout dragContainer;
    private ImageView[] letters;
    private int count;
    private Integer randomChallenge;
    private List<WordModel> wordModels;
    private DataBase dataBase;
    private Double score;
    private double correctCount;
    private TextView textCountLevel;
    private int finalChallengeNumber;
    private boolean verifyWord;
    private List<Integer> challengeIndex;
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
        try {
            this.wordModels = dataBase.searchWordsDatabase(bundle.getInt("contextID"));
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        this.finalChallengeNumber = 10;

        if (wordModels.size() < finalChallengeNumber) {
            finalChallengeNumber = wordModels.size();
        }
        this.score = (double) wordModels.size();
        this.correctCount = 0;
        count = 0;
        this.challengeIndex = shuffleLevelIndex();
        randomChallenge = challengeIndex.get(count);
        this.verifyWord = false;


        this.imageChallenge = findViewById(R.id.imageQuestion);
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
                verifyWord(wordModels.get(randomChallenge).getName());
            }
        });

        loadWord(randomChallenge);
        setImageChallenge(randomChallenge);
    }

    private void toGetNextWord() {
        setCount();
        if (count == finalChallengeNumber) {
            inputName();
        } else {
            clearChallenge();
            randomChallenge = challengeIndex.get(count);
            loadWord(randomChallenge);
            setImageChallenge(randomChallenge);
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
        for (int i = 0; i < wordModels.size(); i++) {
            aux.add(i);
        }
        Collections.shuffle(aux);
        return aux;
    }

    private void removeLettersWrong() {
        for (int i = 0; i < wordModels.get(randomChallenge).getName().length(); i++) {
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
        if (count < finalChallengeNumber) {
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
        ScoreModel scoreModel = new ScoreModel(imageView.getDrawable(), this.score, user, contextID, (int) correctCount, this.finalChallengeNumber);
        this.insertScore(scoreModel, contextID);
        Intent intent = new Intent(ShuffledGameActivity.this, CongratulationsMessageActivity.class);
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
        setScore(this.correctCount / this.finalChallengeNumber * 100);
        if (score < 50.0) {
            return R.drawable.low_score;
        } else if (this.score >= 50.0 && this.score <= 70.0) {
            return R.drawable.medium_score;
        } else {
            return R.drawable.hight_score;
        }
    }

    private void insertScore(ScoreModel p, Integer contextID) {
        ScoreModelAdapter scoreAdapter = new ScoreModelAdapter(this, contextID);
        try {
            if (scoreAdapter.getCount() >= 10) {
                dataBase.deleteScore((ScoreModel) scoreAdapter.getItem(scoreAdapter.getCount() - 1));
            }
            dataBase.insertScore(p);
            ContextModel contextModel = dataBase.searchContextDatabase(contextID);
            if (contextModel.getScores().equalsIgnoreCase("false")) {
                contextModel.setScores("true");
                dataBase.updateContext(contextModel);
            }
        } catch (DatabaseException ex) {
            ex.printStackTrace();

        }

    }

    private void clearChallenge() {
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

    private void setImageChallenge(int i) {
        this.imageChallenge.setImageBitmap(wordModels.get(i).getImage());
    }

    private void loadCharacter(ImageView characterImageView, int drawableCodID, String letter, LinearLayout drop) {
        characterImageView.setImageResource(drawableCodID);
        characterImageView.setTag(letter);
        characterImageView.setVisibility(View.VISIBLE);
        drop.setVisibility(View.VISIBLE);
    }

    public void loadWord(int pos) {
        setVerifyWord(false);
        int challenge = count + 1;
        String n = "Desafio " + challenge + " de " + finalChallengeNumber;
        this.textCountLevel.setText(n);
        String p = shuffleWord(wordModels.get(pos).getName());
        if (p.equals(wordModels.get(pos).getName())) {
            p = shuffleWord(p);
        }
        char[] aux = p.toCharArray();
        char[] aux2 = wordModels.get(pos).getName().toCharArray();
        for (int i = 0; i < aux.length; i++) {
            String letter = String.valueOf(aux[i]).toUpperCase();
            letters[i].setContentDescription("f");
            letters[i].setEnabled(true);
            drops[i].setTag(aux2[i]);
            drops[i].setEnabled(true);
            switch (letter) {
                case Character.A:
                    loadCharacter(letters[i], R.drawable.a, Character.A, drops[i]);
                    continue;
                case Character.B:
                    loadCharacter(letters[i], R.drawable.b, Character.B, drops[i]);
                    continue;
                case Character.C:
                    loadCharacter(letters[i], R.drawable.c, Character.C, drops[i]);
                    continue;
                case Character.D:
                    loadCharacter(letters[i], R.drawable.d, Character.D, drops[i]);
                    continue;
                case Character.E:
                    loadCharacter(letters[i], R.drawable.e, Character.E, drops[i]);
                    continue;
                case Character.F:
                    loadCharacter(letters[i], R.drawable.f, Character.F, drops[i]);
                    continue;
                case Character.G:
                    loadCharacter(letters[i], R.drawable.g, Character.G, drops[i]);
                    continue;
                case Character.H:
                    loadCharacter(letters[i], R.drawable.h, Character.H, drops[i]);
                    continue;
                case Character.I:
                    loadCharacter(letters[i], R.drawable.i, Character.I, drops[i]);
                    continue;
                case Character.J:
                    loadCharacter(letters[i], R.drawable.j, Character.J, drops[i]);
                    continue;
                case Character.K:
                    loadCharacter(letters[i], R.drawable.k, Character.K, drops[i]);
                    continue;
                case Character.L:
                    loadCharacter(letters[i], R.drawable.l, Character.L, drops[i]);
                    continue;
                case Character.M:
                    loadCharacter(letters[i], R.drawable.m, Character.M, drops[i]);
                    continue;
                case Character.N:
                    loadCharacter(letters[i], R.drawable.n, Character.N, drops[i]);
                    continue;
                case Character.O:
                    loadCharacter(letters[i], R.drawable.o, Character.O, drops[i]);
                    continue;
                case Character.P:
                    loadCharacter(letters[i], R.drawable.p, Character.P, drops[i]);
                    continue;
                case Character.Q:
                    loadCharacter(letters[i], R.drawable.q, Character.Q, drops[i]);
                    continue;
                case Character.R:
                    loadCharacter(letters[i], R.drawable.r, Character.R, drops[i]);
                    continue;
                case Character.S:
                    loadCharacter(letters[i], R.drawable.s, Character.S, drops[i]);
                    continue;
                case Character.T:
                    loadCharacter(letters[i], R.drawable.t, Character.T, drops[i]);
                    continue;
                case Character.U:
                    loadCharacter(letters[i], R.drawable.u, Character.U, drops[i]);
                    continue;
                case Character.V:
                    loadCharacter(letters[i], R.drawable.v, Character.V, drops[i]);
                    continue;
                case Character.W:
                    loadCharacter(letters[i], R.drawable.w, Character.W, drops[i]);
                    continue;
                case Character.X:
                    loadCharacter(letters[i], R.drawable.x, Character.X, drops[i]);
                    continue;
                case Character.Y:
                    loadCharacter(letters[i], R.drawable.y, Character.Y, drops[i]);
                    continue;
                case Character.Z:
                    loadCharacter(letters[i], R.drawable.z, Character.Z, drops[i]);
                    continue;
                case Character.Ç:
                    loadCharacter(letters[i], R.drawable.cc, Character.Ç, drops[i]);
                    continue;
                case Character.Á:
                    loadCharacter(letters[i], R.drawable.aa, Character.Á, drops[i]);
                    continue;
                case Character.Â:
                    loadCharacter(letters[i], R.drawable.aaa, Character.Â, drops[i]);
                    continue;
                case Character.Ã:
                    loadCharacter(letters[i], R.drawable.aaaa, Character.Ã, drops[i]);
                    continue;
                case Character.É:
                    loadCharacter(letters[i], R.drawable.ee, Character.É, drops[i]);
                    continue;
                case Character.Ê:
                    loadCharacter(letters[i], R.drawable.eee, Character.Ê, drops[i]);
                    continue;
                case Character.Í:
                    loadCharacter(letters[i], R.drawable.ii, Character.Í, drops[i]);
                    continue;
                case Character.Ó:
                    loadCharacter(letters[i], R.drawable.oo, Character.Ó, drops[i]);
                    continue;
                case Character.Ô:
                    loadCharacter(letters[i], R.drawable.ooo, Character.Ô, drops[i]);
                    continue;
                case Character.Õ:
                    loadCharacter(letters[i], R.drawable.oooo, Character.Õ, drops[i]);
                    continue;
                case Character.Ú:
                    loadCharacter(letters[i], R.drawable.uu, Character.Ú, drops[i]);
            }
        }
    }

    private String shuffleWord(String word) {
        List<String> characters = Arrays.asList(word.split(""));
        Collections.shuffle(characters);
        StringBuilder wordShuffled = new StringBuilder(word.length());
        for (String k : characters) {
            wordShuffled.append(k);
        }
        return wordShuffled.toString();
    }

    private void verifyWord(String s) {
        if (isAllDroppedLetter(s)) {
            if (checkCharacters(s).equals("v")) {
                if (!verifyWord) {
                    this.correctCount = correctCount + 1;
                }
                startSoundQuestionCorrect();
                toGetNextWord();
            } else {
                starSoundQuestionWrong();
                removeLettersWrong();
                setVerifyWord(true);
            }
        }
    }

    private CharSequence checkCharacters(String s) {
        CharSequence verifyWordValue = "v";
        for (int i = 0; i < s.length(); i++) {
            if (letters[i].getContentDescription().equals("f")) {
                verifyWordValue = "f";
                break;
            }
        }
        return verifyWordValue;
    }

    private boolean isAllDroppedLetter(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (letters[i].isEnabled()) {
                return false;
            }
        }
        return true;
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
        this.startMainActivity();
    }

    @Override
    protected void startMainActivity() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair?");
        builder.setMessage("Todo o progresso será perdido.");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ShuffledGameActivity.this, MainActivity.class);
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
}