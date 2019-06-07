package com.mydroidtechnology.embaralhado.listener;

import android.content.ClipData;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;

 public class MyTouchListener implements View.OnTouchListener {

     public boolean onTouch(View view, MotionEvent motionEvent) {
         if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
             ClipData data = ClipData.newPlainText("", "");
             View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 view.startDragAndDrop(data, shadowBuilder, view, 0);
             } else {
                 view.startDrag(data, shadowBuilder, view, 0);
             }
             view.setVisibility(View.INVISIBLE);
             return true;
         }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
             view.performClick();
             return true;
         }else {
             return false;
         }
     }
 }