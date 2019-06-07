package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.mydroidtechnology.embaralhado.adapter.CategoriesScoresAdapter;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

/*
 * Created by Jeferson on 11/08/2017.
 */

public class SelectCategorieScoreActivity extends GenericDataViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Contextos pontuados");
        }
        super.listview.setAdapter(new CategoriesScoresAdapter(this, "true"));
        super.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Category category = (Category) listview.getAdapter().getItem(position);
                Integer contextID = category.getId();
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
        Intent intent = new Intent(SelectCategorieScoreActivity.this, ScoreActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }
}
