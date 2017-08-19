package com.mydroidtechnology.embaralhado.listener;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MyOnDragListener implements View.OnDragListener {


    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        boolean isDroped = false;
        LinearLayout drop = (LinearLayout)v;
        ImageView letter = (ImageView) event.getLocalState();

        switch(action) {
            case DragEvent.ACTION_DROP:
                isDroped = true;
                letter.setEnabled(false);
                drop.setEnabled(false);
                if(letter.getTag().toString().equalsIgnoreCase(drop.getTag().toString())){
                    letter.setContentDescription("v");
                }
                else{
                    letter.setContentDescription("f");
                }
                ViewGroup dragLayout = (ViewGroup) letter.getParent();
                dragLayout.removeView(letter);
                drop.addView(letter);
                letter.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //noinspection ConstantConditions
                if(!isDroped)
                    letter.setVisibility(View.VISIBLE);
                break;
            default:
                isDroped = false;
                break;
        }

        return true;
    }
}

