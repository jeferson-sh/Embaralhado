package estagio3.ufpb.com.br.projeto1;

import android.content.ClipData;
import android.view.View;

class MyOnLongClickListener implements View.OnLongClickListener {
    @Override
    public boolean onLongClick(View v) {
        ClipData data = ClipData.newPlainText("Letra",(CharSequence) v.getTag());
        View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
        v.startDrag(data, shadow, v, 0);
        v.setVisibility(View.INVISIBLE);
        return(true);
    }
}