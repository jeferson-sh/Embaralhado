package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapter.CategoriesWordsAdapter;
import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class SelectContextsToPlayActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.select_categorie);
        }
        super.listview.setAdapter(new CategoriesWordsAdapter(this, "true"));
        super.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position);
            }
        });
    }

    private void itemClicked(int position) {
        Context context = (Context) listview.getAdapter().getItem(position);
        Integer contextID = context.getId();
        startGame(contextID);
    }

    @Override
    protected void startActivityOnBackPressed() {
        Intent intent = new Intent(SelectContextsToPlayActivity.this, MainActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    private void startGame(Integer contextID) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(SelectContextsToPlayActivity.this, ShuffledGameActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }
}