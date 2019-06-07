package com.mydroidtechnology.embaralhado.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Category;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.view.EditWordActivity;

public class EditWordAdapter extends WordsAdapter {

    public EditWordAdapter(Context context, Integer contextID) {
        super(context, contextID);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        final ImageButton removeWord = layout.findViewById(R.id.delete_data);
        removeWord.setVisibility(View.VISIBLE);
        final ImageButton editeWord = layout.findViewById(R.id.edite_data);
        editeWord.setVisibility(View.VISIBLE);
        final Word word = (Word) getGenericModels().get(i);
        removeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setCancelable(true);
                builder.setTitle("Deseja apagar a palavra "+word.getName()+"?");
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getGenericModels().remove(word);
                        getDataBase().deleteWord(word);
                        if(getGenericModels().isEmpty()){
                            updateCategorieWithNoElements(getContextID());
                        }
                        Toast.makeText(getContext(), "Palavra " +word.getName() + " apagada!", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }



        });

        editeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("contextID", getContextID());
                bundle.putInt("wordID",word.getId());
                Intent intent = new Intent(getContext(), EditWordActivity.class);
                intent.putExtras(bundle);
                BackgroundMusicService.setStopBackgroundMusicEnable(false);
                getContext().startActivity(intent);
                ((Activity) getContext()).finish();
            }

        });
        return layout;
    }

    private void updateCategorieWithNoElements(int contextID) {
        Category category = getDataBase().searchCategoryDatabase(contextID);
        category.setHaveElements("false");
        getDataBase().updateCategory(category);
    }

}
