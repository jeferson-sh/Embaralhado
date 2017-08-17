package estagio3.ufpb.com.br.embaralhando.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import estagio3.ufpb.com.br.embaralhando.util.MyCountDownTimerUtil;
import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundServiceUtil;

public class MainActivity extends AppCompatActivity {

    private DataBase dataBase;
    private MyCountDownTimerUtil myCountDownTimerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentService = new Intent(this, BackgroundSoundServiceUtil.class);
        if (BackgroundSoundServiceUtil.isStopBackgroundMusicEnable())
            startService(intentService);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(true);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        ImageButton playButton = (ImageButton) findViewById(R.id.playButton);
        ImageButton settingsButton = (ImageButton) findViewById(R.id.settingsButton);
        ImageButton scoreButton = (ImageButton) findViewById(R.id.scoreButton);
        ImageButton aboutButton = (ImageButton) findViewById(R.id.about_button);
        this.dataBase = new DataBase(this);
        this.myCountDownTimerUtil = new MyCountDownTimerUtil(MainActivity.this, 2000, 1000);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abouMessage();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBase.searchMyCategoriesDatabase().isEmpty()) {
                    Snackbar.make(view, "Não existem Contextos cadastrados!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                } else {
                    selectContext();
                }

            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScoreActivity();
            }
        });
    }

    @SuppressLint("InflateParams")
    private void abouMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Informações sobre o aplicativo");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.about_dialog);
        } else {
            builder.setView(getLayoutInflater().inflate(R.layout.about_dialog, null));
        }
        builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss dialog
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!BackgroundSoundServiceUtil.isPlaying()) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundServiceUtil.isPlaying()) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            BackgroundSoundServiceUtil.getMediaPlayer().pause();
            BackgroundSoundServiceUtil.setIsPlaying(false);
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            BackgroundSoundServiceUtil.getMediaPlayer().start();
            BackgroundSoundServiceUtil.setIsPlaying(true);
        }
    }

    private void selectContext() {
        Intent intent = new Intent(this, SelectCategoriesToPlayActivity.class);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, CategoriesActivity.class);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startScoreActivity() {
        Intent intent = new Intent(this, SelectCategorieScoreActivity.class);
        BackgroundSoundServiceUtil.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!myCountDownTimerUtil.isStart()) {
            myCountDownTimerUtil.start();
        } else {
            finish();
            System.exit(0);
        }
    }

    private void exitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair do jogo?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                stopService(new Intent(MainActivity.this,BackgroundSoundServiceUtil.class));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isStopBackgroundMusicEnable())
            BackgroundSoundServiceUtil.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundSoundServiceUtil.getMediaPlayer() != null && BackgroundSoundServiceUtil.isPlaying())
            BackgroundSoundServiceUtil.getMediaPlayer().start();
    }
}
