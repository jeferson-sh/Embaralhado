package com.mydroidtechnology.embaralhado.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.adapter.GenericAdapter;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public abstract class GenericDataManagementActivity extends GenericDataViewActivity {

    protected ImageButton addDataBt;
    protected int maxData;
    protected GenericAdapter editDataAdapter;
    protected GenericAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.toolbar.inflateMenu(R.menu.edit_menu);
        this.addDataBt = createFloatingActionButton();
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        this.addDataBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAddDataActivity();
            }
        });
    }

    private FloatingActionButton createFloatingActionButton(){
        FloatingActionButton floatingActionButton = new FloatingActionButton(this);
        floatingActionButton.setImageResource(R.drawable.ic_add_white);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.BOTTOM|Gravity.END);
        layoutParams.setMargins(32,32,32,32);
        floatingActionButton.setLayoutParams(layoutParams);
        frameLayout.addView(floatingActionButton);
        return floatingActionButton;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        if (!BackgroundMusicService.isPlaying()) {
            menu.getItem(2).setIcon(R.drawable.ic_volume_mute_white);
        }
        menu.getItem(1).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edite_categories:
                setEditDataAdapter(item);
                return true;
            case R.id.finish_edite:
                setAdapter(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setEditDataAdapter(MenuItem item) {
        item.setVisible(false);
        this.addDataBt.setVisibility(View.GONE);
        this.addDataBt.setEnabled(false);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        super.toolbar.getMenu().getItem(1).setVisible(true);
        super.listview.setAdapter(editDataAdapter);
    }

    protected void setAdapter(MenuItem item) {
        item.setVisible(false);
        this.addDataBt.setVisibility(View.VISIBLE);
        this.addDataBt.setEnabled(true);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        super.toolbar.getMenu().getItem(0).setVisible(true);
        super.listview.setAdapter(adapter);
    }

    protected abstract void startAddDataActivity();

    protected abstract void startActivityOnBackPressed();

}