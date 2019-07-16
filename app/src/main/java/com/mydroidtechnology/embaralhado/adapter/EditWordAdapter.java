package com.mydroidtechnology.embaralhado.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Context;
import com.mydroidtechnology.embaralhado.model.Word;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.view.EditWordActivity;

// This class have responsibility of change layout of ListView and allow the edition
// of Word Objects in ListView of WordsDataManagementActivity

public class EditWordAdapter extends WordsAdapter {

    public EditWordAdapter(android.content.Context context, Integer contextID) {
        super(context, contextID);
    }

    // Override method getView in GenericAdapter for show options of edition of the Context object.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        final ImageButton removeWordBtn = layout.findViewById(R.id.delete_data);
        removeWordBtn.setVisibility(View.VISIBLE);
        final ImageButton editWordBtn = layout.findViewById(R.id.edite_data);
        editWordBtn.setVisibility(View.VISIBLE);
        final Word word = (Word) getGenericModels().get(i);
        removeWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWord(word); // Remove Word object.
            }
        });

        editWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWord(word); // Edit Word object.
            }

        });
        return layout;
    }

    private void editWord(Word word) {
        Bundle bundle = new Bundle();
        //ContextID for instance listView in WordDataManagementActivity.
        bundle.putInt("contextID", getContextID());
        //WordID for search in Database and allow Edition.
        bundle.putInt("wordID",word.getId());
        Intent intent = new Intent(getContext(), EditWordActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private void removeWord(final Word word) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Deseja apagar a palavra "+word.getName()+"?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getGenericModels().remove(word); //Remove Word in ListView.
                getDataBase().deleteWord(word); //Remove Word in Database.
                if(getGenericModels().isEmpty()){
                    updateCategoryWithNoElements(getContextID());
                }
                Toast.makeText(getContext(), "Palavra " +word.getName() + " apagada!", Toast.LENGTH_LONG).show();
                EditWordAdapter.super.notifyDataSetChanged();  //Notify ListView that was occurred changes.
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

    //Update Context Object in Database, case not have elements (Words).
    private void updateCategoryWithNoElements(int contextID) {
        Context context = getDataBase().searchContextDatabase(contextID);
        // If getGenericModel is Empty, the Context Object not have elements (Words).
        context.setHaveElements("false");
        getDataBase().updateContext(context);
    }

}
