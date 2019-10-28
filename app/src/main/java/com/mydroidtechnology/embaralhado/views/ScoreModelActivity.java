package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.mydroidtechnology.embaralhado.adapters.ScoreModelAdapter;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

import java.util.Objects;

public class ScoreModelActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Últimas pontuações");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            super.listview.setAdapter(new ScoreModelAdapter(this, Objects.requireNonNull(getIntent().getExtras()).getInt("contextID")));
        }else{
            super.listview.setAdapter(new ScoreModelAdapter(this,getIntent().getExtras().getInt("contextID")));
        }
    }

    @Override
    protected void startActivityOnBackPressed() {
        Intent intent = new Intent(ScoreModelActivity.this, SelectContextsScoreActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }
}

