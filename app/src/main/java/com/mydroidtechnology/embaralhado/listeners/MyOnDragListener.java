package com.mydroidtechnology.embaralhado.listeners;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

//This class is responsible for to set event Drag in Character on ShuffledGameActivity.
public class MyOnDragListener implements View.OnDragListener {

    //This Method will to capture the event Drag in Character.
    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction(); //Capture Action.
        boolean isDroped = false; //Event drop not occurred.
        LinearLayout drop = (LinearLayout)v;
        ImageView character = (ImageView) event.getLocalState(); //Capture the character that was Dragged.
        switch(action) { // Action contains the value of the action occurred.
            case DragEvent.ACTION_DROP:
                isDroped = true;//Drop occurred.
                character.setEnabled(false); //The character can't be moved.
                drop.setEnabled(false); //The drop container can't receive other characters.
                if(character.getTag().toString().equalsIgnoreCase(drop.getTag().toString())){ // If the character contains value equals that the tag in drop container.
                    character.setContentDescription("v"); //Set the character with mark "v".
                }
                else{
                    character.setContentDescription("f"); //Otherwise set the character with mark "f".
                }
                ViewGroup linearLayoutCharacter = (ViewGroup) character.getParent(); //Capture the LinearLayout of the character that was dragged.
                linearLayoutCharacter.removeView(character); //Remove the character of the LinearLayout.
                drop.addView(character); //Drop container receive the character.
                character.setVisibility(View.VISIBLE); //The character is visible in a new drop container.
                return isDroped;
            case DragEvent.ACTION_DRAG_ENDED:
                //noinspection ConstantConditions
                if(!isDroped) //If drop don't occurred
                    character.setVisibility(View.VISIBLE); //ShadowBuilder is discarded and character returns to be visible.
                break;
        }
        return true;
    }
}

