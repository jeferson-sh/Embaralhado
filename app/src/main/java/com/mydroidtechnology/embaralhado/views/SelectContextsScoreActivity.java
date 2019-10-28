package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mydroidtechnology.embaralhado.adapters.ContextModelWithScoreAdapter;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

/*
 * Created by Jeferson on 11/08/2017.
 */

public class SelectContextsScoreActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contextos pontuados");
        }
        super.listview.setAdapter(new ContextModelWithScoreAdapter(this, "true"));
        super.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContextModel contextModel = (ContextModel) listview.getAdapter().getItem(position);
                Integer contextID = contextModel.getId();
                startScoreActivity(contextID);

            }
        });
    }

    @Override
    protected void startActivityOnBackPressed() {
        super.startMainActivity();
    }

    private void startScoreActivity(Integer contextID) {
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(SelectContextsScoreActivity.this, ScoreModelActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }
}
