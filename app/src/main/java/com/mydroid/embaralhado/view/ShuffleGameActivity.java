package com.mydroid.embaralhado.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.mydroid.embaralhado.listener.MyOnDragListener;
import com.mydroid.embaralhado.listener.MyTouchListener;
import com.mydroid.embaralhado.R;
import com.mydroid.embaralhado.adapter.ScoreAdapter;
import com.mydroid.embaralhado.model.Categorie;
import com.mydroid.embaralhado.model.Letters;
import com.mydroid.embaralhado.model.Score;
import com.mydroid.embaralhado.model.Word;
import com.mydroid.embaralhado.persistence.DataBase;
import com.mydroid.embaralhado.service.BackgroundMusicService;

public class ShuffleGameActivity extends AppCompatActivity {

    private ImageView imageQuestion;
    private LinearLayout[] drops;
    private LinearLayout dragContainer;
    private ImageView[] letters;
    private int count;
    private Integer randomLevel;
    private List<Word> words;
    private DataBase dataBase;
    private double score;
    private double correctCount;
    private TextView textCountLevel;
    private int finalChallenge;
    private boolean verifyWord;

    private List<Integer> levelsIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle_game_mode);

        BackgroundMusicService.setStopBackgroundMusicEnable(true);

        MyOnDragListener myOnDragListener = new MyOnDragListener();
        MyTouchListener myTouchListener = new MyTouchListener();
        this.dataBase = new DataBase(this);
        Bundle bundle = getIntent().getExtras();
        this.words = dataBase.searchWordsDatabase(bundle.getInt("contextID"));
        this.finalChallenge = 10;

        if (words.size() < finalChallenge) {
            finalChallenge = words.size();
        }
        this.score = words.size();
        this.correctCount = 0;
        count = 0;
        this.levelsIndex = shuffleLevelIndex();
        randomLevel = levelsIndex.get(count);
        this.verifyWord = false;


        this.imageQuestion = (ImageView) findViewById(R.id.imageQuestion);
        ImageButton restartButton = (ImageButton) findViewById(R.id.restart_button);
        ImageButton checkWordButton = (ImageButton) findViewById(R.id.checkButton);
        this.textCountLevel = (TextView) findViewById(R.id.textCountNivel);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_shuffler_game_mode);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Organize as letras");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }

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

        this.dragContainer = (LinearLayout) findViewById(R.id.drag);

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
            imputName();
        } else {
            clearLevel();
            randomLevel = levelsIndex.get(count);
            loadWord(randomLevel);
            setImage(randomLevel);
        }
    }

    private String imputName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        final String[] youEditTextValue = {""};
        builder.setCancelable(true);
        builder.setTitle("Insira seu nome");
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
        return youEditTextValue[0];
    }

    private void startMainActivity() {
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

    private void controlMusic(MenuItem item) {
        if (BackgroundMusicService.isPlaying()) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            BackgroundMusicService.getMediaPlayer().pause();
            BackgroundMusicService.setIsPlaying(false);
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            BackgroundMusicService.getMediaPlayer().start();
            BackgroundMusicService.setIsPlaying(true);
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
        Integer contextID = getIntent().getExtras().getInt("contextID");
        Score p = new Score(imageView.getDrawable(), (int) this.score, user, contextID, (int) correctCount, words.size());
        insertScore(p, contextID);
        Intent intent = new Intent(ShuffleGameActivity.this, CongratulationsMessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("image", drawId);
        bundle.putInt("score", (int) this.score);
        bundle.putString("name", user);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private int getIdImageViewScore() {
        setScore((this.correctCount / this.words.size()) * 100);
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
        Categorie categorie = dataBase.searchCategorieDatabase(contextID);
        categorie.setScores("true");
        dataBase.updateCategorie(categorie);
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


    public void loadWord(int pos) {
        setVerifyWord(false);
        int challenge = count + 1;
        String n = "Desafio " + challenge + " de " + finalChallenge;
        this.textCountLevel.setText(n);
        String p = shuffle(words.get(pos).getName());
        if (p.equals(words.get(pos).getName())) {
            p = shuffle(p);
        }
        char aux[] = p.toCharArray();
        char aux2[] = words.get(pos).getName().toCharArray();
        for (int i = 0; i < aux.length; i++) {
            String letter = String.valueOf(aux[i]).toUpperCase();
            letters[i].setContentDescription("f");
            letters[i].setEnabled(true);
            drops[i].setTag(aux2[i]);
            drops[i].setEnabled(true);
            switch (letter) {
                case Letters.A:
                    letters[i].setImageResource(R.drawable.a);
                    letters[i].setTag(Letters.A);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.B:
                    letters[i].setImageResource(R.drawable.b);
                    letters[i].setTag(Letters.B);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.C:
                    letters[i].setImageResource(R.drawable.c);
                    letters[i].setTag(Letters.C);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.D:
                    letters[i].setImageResource(R.drawable.d);
                    letters[i].setTag(Letters.D);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.E:
                    letters[i].setImageResource(R.drawable.e);
                    letters[i].setTag(Letters.E);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.F:
                    letters[i].setImageResource(R.drawable.f);
                    letters[i].setTag(Letters.F);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.G:
                    letters[i].setImageResource(R.drawable.g);
                    letters[i].setTag(Letters.G);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.H:
                    letters[i].setImageResource(R.drawable.h);
                    letters[i].setTag(Letters.H);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.I:
                    letters[i].setImageResource(R.drawable.i);
                    letters[i].setTag(Letters.I);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.J:
                    letters[i].setImageResource(R.drawable.j);
                    letters[i].setTag(Letters.J);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.K:
                    letters[i].setImageResource(R.drawable.k);
                    letters[i].setTag(Letters.K);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.L:
                    letters[i].setImageResource(R.drawable.l);
                    letters[i].setTag(Letters.L);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.M:
                    letters[i].setImageResource(R.drawable.m);
                    letters[i].setTag(Letters.M);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.N:
                    letters[i].setImageResource(R.drawable.n);
                    letters[i].setTag(Letters.N);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.O:
                    letters[i].setImageResource(R.drawable.o);
                    letters[i].setTag(Letters.O);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.P:
                    letters[i].setImageResource(R.drawable.p);
                    letters[i].setTag(Letters.P);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.Q:
                    letters[i].setImageResource(R.drawable.q);
                    letters[i].setTag(Letters.Q);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.R:
                    letters[i].setImageResource(R.drawable.r);
                    letters[i].setTag(Letters.R);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.S:
                    letters[i].setImageResource(R.drawable.s);
                    letters[i].setTag(Letters.S);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.T:
                    letters[i].setImageResource(R.drawable.t);
                    letters[i].setTag(Letters.T);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.U:
                    letters[i].setImageResource(R.drawable.u);
                    letters[i].setTag(Letters.U);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.V:
                    letters[i].setImageResource(R.drawable.v);
                    letters[i].setTag(Letters.V);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.W:
                    letters[i].setImageResource(R.drawable.w);
                    letters[i].setTag(Letters.W);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.X:
                    letters[i].setImageResource(R.drawable.x);
                    letters[i].setTag(Letters.X);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.Y:
                    letters[i].setImageResource(R.drawable.y);
                    letters[i].setTag(Letters.Y);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.Z:
                    letters[i].setImageResource(R.drawable.z);
                    letters[i].setTag(Letters.Z);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;

                case Letters.Ç:
                    letters[i].setImageResource(R.drawable.cc);
                    letters[i].setTag(Letters.Ç);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Á:
                    letters[i].setImageResource(R.drawable.aa);
                    letters[i].setTag(Letters.Á);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Â:
                    letters[i].setImageResource(R.drawable.aaa);
                    letters[i].setTag(Letters.Â);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Ã:
                    letters[i].setImageResource(R.drawable.aaaa);
                    letters[i].setTag(Letters.Ã);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.É:
                    letters[i].setImageResource(R.drawable.ee);
                    letters[i].setTag(Letters.É);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Ê:
                    letters[i].setImageResource(R.drawable.eee);
                    letters[i].setTag(Letters.Ê);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Í:
                    letters[i].setImageResource(R.drawable.ii);
                    letters[i].setTag(Letters.Í);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Ó:
                    letters[i].setImageResource(R.drawable.oo);
                    letters[i].setTag(Letters.Ó);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Ô:
                    letters[i].setImageResource(R.drawable.ooo);
                    letters[i].setTag(Letters.Ô);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Õ:
                    letters[i].setImageResource(R.drawable.oooo);
                    letters[i].setTag(Letters.Õ);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
                    continue;
                case Letters.Ú:
                    letters[i].setImageResource(R.drawable.uu);
                    letters[i].setTag(Letters.Ú);
                    letters[i].setVisibility(View.VISIBLE);
                    drops[i].setVisibility(View.VISIBLE);
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
        if (!BackgroundMusicService.isPlaying()) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startMainActivity();
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.exitGame:
                exitApp();
                return true;
            default:
                return false;
        }
    }

    private void exitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair do jogo?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(ShuffleGameActivity.this,BackgroundMusicService.class));
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void verifyWord(String s) {
        CharSequence verify = "";
        for (int i = 0; i < s.length(); i++) {
            if (letters[i].getContentDescription().equals("f")) {
                verify = letters[i].getContentDescription();
                break;
            } else {
                verify = letters[i].getContentDescription();
            }
        }
        if (verify.equals("v")) {
            if (!verifyWord()) {
                this.correctCount = correctCount + 1;
            }
            startSoundQuestionCorrect();
            toGetNextWord();
        } else {
            setVerifyWord(true);
            starSoundQuestionWrong();
            removeLettersWrong();
        }

    }

    private void starSoundQuestionWrong() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.wrong);
        mp.seekTo(1000);
        mp.start();
        mp.setVolume(300, 300);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    private void startSoundQuestionCorrect() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.correct);
        mp.start();
        mp.setVolume(200, 200);
        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean verifyWord() {
        return verifyWord;
    }

    public void setVerifyWord(boolean verifyWord) {
        this.verifyWord = verifyWord;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isStopBackgroundMusicEnable())
            BackgroundMusicService.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isPlaying())
            BackgroundMusicService.getMediaPlayer().start();
    }
}