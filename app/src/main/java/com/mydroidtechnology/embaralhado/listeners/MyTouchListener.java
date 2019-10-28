package com.mydroidtechnology.embaralhado.listeners;

import android.content.ClipData;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//This class is responsible for to set event Touch in Characters on ShuffledGameActivity.
 public class MyTouchListener implements View.OnTouchListener {

    //This Method will to capture the event Touch in Characters.
    public boolean onTouch(View view, MotionEvent motionEvent) {
         if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) { //If event touch in character occurred.
             ClipData data = ClipData.newPlainText("", ""); //ClipData to copy the instance of the character.
             View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view); //Create ShadowBuilder for drag.
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                 view.startDragAndDrop(data, shadowBuilder, view, 0); //Start drag and drop in android version Nougat or later.
             } else {
                 view.startDrag(data, shadowBuilder, view, 0); //Start drag and drop in android version ice Cream Sandwich or Kitkat.
             }
             view.setVisibility(View.INVISIBLE); //The Image view related to the character is invisible.
         }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){ //If event Touch finnish.
             view.performClick(); //Run performClick() when click finnish.
         }
         return true;
     }
 }