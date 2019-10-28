package com.mydroidtechnology.embaralhado.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapters.ContextModelAdapter;
import com.mydroidtechnology.embaralhado.adapters.EditContextModelAdapter;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

public class ContextsDataManagementActivity extends GenericDataManagementActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_categories);
        }
        super.maxData = 20;
        super.editDataAdapter = new EditContextModelAdapter(this);
        super.adapter = new ContextModelAdapter(this);

        super.listview.setAdapter(adapter);
        super.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem(position);
            }


        });
    }

    @Override
    protected void startActivityOnBackPressed() {
        if (this.listview.getAdapter().getClass().equals(EditContextModelAdapter.class)) {
            this.setAdapter(toolbar.getMenu().getItem(1));
        } else {
            super.startMainActivity();
        }
    }

    @Override
    protected void startAddDataActivity() {
        if (super.listview.getAdapter().getCount() <= super.maxData) {
            Intent addCategorie = new Intent(ContextsDataManagementActivity.this, InsertNewContextActivity.class);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(addCategorie);
            finish();
        } else {
            Toast.makeText(ContextsDataManagementActivity.this, "Desculpe, a memoria está cheia!", Toast.LENGTH_LONG).show();
        }
    }

    private void clickItem(int position) {
        ContextModelAdapter contextModelAdapter = new ContextModelAdapter(ContextsDataManagementActivity.this);
        ContextModel contextModel = (ContextModel) contextModelAdapter.getItem(position);
        Integer contextID = contextModel.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(ContextsDataManagementActivity.this, WordsDataManagementActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();

    }

    @Override
    protected void setEditDataAdapter(MenuItem item) {
        super.toolbar.setTitle(R.string.edite_categorie);
        super.setEditDataAdapter(item);
    }

    @Override
    protected void setAdapter(MenuItem item) {
        super.toolbar.setTitle(R.string.registered_categories);
        super.setAdapter(item);
    }
}