package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapters.ContextsModelWithWordsAdapter;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

public class SelectContextsToPlayActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.select_categorie);
        }
        super.listview.setAdapter(new ContextsModelWithWordsAdapter(this, "true"));
        super.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemClicked(position);
            }
        });
    }

    private void itemClicked(int position) {
        ContextModel contextModel = (ContextModel) listview.getAdapter().getItem(position);
        Integer contextID = contextModel.getId();
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