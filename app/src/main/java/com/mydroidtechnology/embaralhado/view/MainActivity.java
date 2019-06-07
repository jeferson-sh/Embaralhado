package com.mydroidtechnology.embaralhado.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.util.MyCountDownTimerUtil;

public class MainActivity extends NavigationControlActivity {

    private DataBase dataBase;
    private MyCountDownTimerUtil myCountDownTimerUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intentService = new Intent(this, BackgroundMusicService.class);
        if (BackgroundMusicService.isStopBackgroundMusicEnable())
            startService(intentService);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);

        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton settingsButton = findViewById(R.id.settingsButton);
        ImageButton scoreButton = findViewById(R.id.scoreButton);
        this.dataBase = new DataBase(this);
        this.myCountDownTimerUtil = new MyCountDownTimerUtil(MainActivity.this, 2000, 1000);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataBase.searchMyCategoriesDatabase().isEmpty()) {
                    Snackbar.make(view, "Não existem Contextos cadastrados!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
                } else {
                    startSelectCategoriesToPlayActivity();
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
            case R.id.pp:
                startPrivacyPolicy();
                return true;
            case R.id.about:
                abouMessage();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!BackgroundMusicService.isPlaying()) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    private void startPrivacyPolicy() {
        Intent intent = new Intent(MainActivity.this, PrivacyPolicyActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startSelectCategoriesToPlayActivity() {
        Intent intent = new Intent(this, SelectCategoriesToPlayActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startSettingsActivity() {
        Intent intent = new Intent(this, CategoriesDataManagementActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startScoreActivity() {
        Intent intent = new Intent(this, SelectCategorieScoreActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void exitAppWithCountDown(){
        if (!myCountDownTimerUtil.isStart()) {
            myCountDownTimerUtil.start();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    protected void startActivityOnBackPressed() {
        exitAppWithCountDown();
    }
}
