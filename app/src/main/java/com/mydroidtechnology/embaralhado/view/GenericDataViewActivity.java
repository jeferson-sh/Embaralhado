package com.mydroidtechnology.embaralhado.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public abstract class GenericDataViewActivity extends NavigationControlActivity {

    protected ListView listview;
    protected Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_data_view);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        toolbar = findViewById(R.id.toolbar_select_categorie);
        toolbar.inflateMenu(R.menu.main_menu2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        listview = findViewById(R.id.listViewWords);

    }

    protected abstract void startActivityOnBackPressed();

}