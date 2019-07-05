package com.mydroidtechnology.embaralhado.listener;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//This class is responsible for to set event Drag in Letters on ShuffledGameActivity.
public class MyOnDragListener implements View.OnDragListener {

    //This Method will to capture the event Drag in Letters.
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction(); //Capture Action.
        boolean isDroped = false; //Event drop not occurred.
        LinearLayout drop = (LinearLayout)v;
        ImageView letter = (ImageView) event.getLocalState(); //Capture the Letter that was Dragged.
        switch(action) { // Action contains the value of the action occurred.
            case DragEvent.ACTION_DROP:
                //noinspection UnusedAssignment
                isDroped = true;//Drop occurred.
                letter.setEnabled(false); //The Letter can't be moved.
                drop.setEnabled(false); //The drop container can't receive other letters.
                if(letter.getTag().toString().equalsIgnoreCase(drop.getTag().toString())){ // If the letter contains value equals that the tag in drop container.
                    letter.setContentDescription("v"); //Set the letter with mark "v".
                }
                else{
                    letter.setContentDescription("f"); //Otherwise set the letter with mark "f".
                }
                ViewGroup linearLayoutLetter = (ViewGroup) letter.getParent(); //Capture the LinearLayout of the letter that was dragged.
                linearLayoutLetter.removeView(letter); //Remove the letter of the LinearLayout.
                drop.addView(letter); //Drop container receive the letter.
                letter.setVisibility(View.VISIBLE); //The letter is visible in a new drop container.
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                //noinspection ConstantConditions
                if(!isDroped) //If drop don't occurred
                    letter.setVisibility(View.VISIBLE); //ShadowBuilder is discarded and letter returns to be visible.
                break;
            default:
                //noinspection UnusedAssignment
                isDroped = false;
                break;
        }

        return true;
    }
}

