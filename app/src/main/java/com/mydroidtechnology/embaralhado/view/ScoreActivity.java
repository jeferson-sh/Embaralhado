package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.mydroidtechnology.embaralhado.adapter.ScoreAdapter;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

import java.util.Objects;

public class ScoreActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Últimas pontuações");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            super.listview.setAdapter(new ScoreAdapter(this, Objects.requireNonNull(getIntent().getExtras()).getInt("contextID")));
        }else{
            super.listview.setAdapter(new ScoreAdapter(this,getIntent().getExtras().getInt("contextID")));
        }
    }

    @Override
    protected void startActivityOnBackPressed() {
        Intent intent = new Intent(ScoreActivity.this, SelectCategorieScoreActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }
}

