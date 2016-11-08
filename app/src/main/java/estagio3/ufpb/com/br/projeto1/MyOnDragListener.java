package estagio3.ufpb.com.br.projeto1;

import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

class MyOnDragListener implements View.OnDragListener {

    private String palavra;

    public MyOnDragListener() {
        this(new String());
    }

    public MyOnDragListener(String palavra) {
        this.palavra = palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public String getPalavra() {
        return palavra;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        boolean isDroped = false;
        LinearLayout drop = (LinearLayout)v;
        ImageView letra = (ImageView) event.getLocalState();

        switch(action) {
            case DragEvent.ACTION_DROP:
                isDroped = true;
                setPalavra(palavra.concat(letra.getTag().toString()));
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

