package estagio3.ufpb.com.br.projeto1;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

class MyOnDragListener implements View.OnDragListener {


    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        boolean isDroped = false;
        LinearLayout drop = (LinearLayout)v;
        ImageView letra = (ImageView) event.getLocalState();

        switch(action) {
            case DragEvent.ACTION_DROP:
                isDroped = true;
                letra.setEnabled(false);
                drop.setEnabled(false);
                if(letra.getTag().toString().equalsIgnoreCase(drop.getTag().toString())){
                    letra.setContentDescription("v");
                }
                else{
                    letra.setContentDescription("f");
                }
                ViewGroup dragLayout = (ViewGroup) letra.getParent();
                dragLayout.removeView(letra);
                drop.addView(letra);
                letra.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                if(!isDroped)
                    letra.setVisibility(View.VISIBLE);
                break;
        }

        return true;
    }
}

