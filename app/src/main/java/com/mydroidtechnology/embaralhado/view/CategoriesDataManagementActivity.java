package com.mydroidtechnology.embaralhado.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapter.CategorieAdapter;
import com.mydroidtechnology.embaralhado.adapter.EditCategorieAdapter;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class CategoriesDataManagementActivity extends GenericDataManagementActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.registered_categories);
        }
        super.maxData = 20;
        super.editDataAdapter = new EditCategorieAdapter(this);
        super.adapter = new CategorieAdapter(this);
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
        if (this.listview.getAdapter().getClass().equals(EditCategorieAdapter.class)) {
            this.setAdapter(toolbar.getMenu().getItem(1));
        } else {
            super.startMainActivity();
        }
    }

    @Override
    protected void startAddDataActivity() {
        if (super.listview.getAdapter().getCount() <= super.maxData) {
            Intent addCategorie = new Intent(CategoriesDataManagementActivity.this, InsertNewCategoriesActivity.class);
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(addCategorie);
            finish();
        } else {
            Toast.makeText(CategoriesDataManagementActivity.this, "Desculpe, a memoria está cheia!", Toast.LENGTH_LONG).show();
        }
    }

    private void clickItem(int position) {
        CategorieAdapter categorieAdapter = new CategorieAdapter(CategoriesDataManagementActivity.this);
        Category category = (Category) categorieAdapter.getItem(position);
        Integer contextID = category.getId();
        Bundle bundle = new Bundle();
        bundle.putInt("contextID", contextID);
        Intent intent = new Intent(CategoriesDataManagementActivity.this, WordsDataManagementActivity.class);
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