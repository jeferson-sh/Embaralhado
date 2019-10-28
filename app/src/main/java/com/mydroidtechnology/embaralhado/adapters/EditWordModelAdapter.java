package com.mydroidtechnology.embaralhado.adapters;

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
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.models.ContextModel;
import com.mydroidtechnology.embaralhado.models.WordModel;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;
import com.mydroidtechnology.embaralhado.views.EditWordModelActivity;

// This class have responsibility of change layout of ListView and allow the edition
// of WordModel Objects in ListView of WordsDataManagementActivity

public class EditWordModelAdapter extends WordsModelAdapter {

    public EditWordModelAdapter(android.content.Context context, Integer contextID) {
        super(context, contextID);
    }

    // Override method getView in GenericModelAdapter for show options of edition of the ContextModel object.
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View layout = super.getView(i,view,viewGroup);
        final ImageButton removeWordBtn = layout.findViewById(R.id.delete_data);
        removeWordBtn.setVisibility(View.VISIBLE);
        final ImageButton editWordBtn = layout.findViewById(R.id.edite_data);
        editWordBtn.setVisibility(View.VISIBLE);
        final WordModel wordModel = (WordModel) getGenericModels().get(i);
        removeWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeWord(wordModel); // Remove WordModel object.
            }
        });

        editWordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editWord(wordModel); // Edit WordModel object.
            }

        });
        return layout;
    }

    private void editWord(WordModel wordModel) {
        Bundle bundle = new Bundle();
        //ContextID for instance listView in WordDataManagementActivity.
        bundle.putInt("contextID", getContextID());
        //WordID for search in Database and allow Edition.
        bundle.putInt("wordID", wordModel.getId());
        Intent intent = new Intent(getContext(), EditWordModelActivity.class);
        intent.putExtras(bundle);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }

    private void removeWord(final WordModel wordModel) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle("Deseja apagar a palavra "+ wordModel.getName()+"?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getGenericModels().remove(wordModel); //Remove WordModel in ListView.
                try {
                    getDataBase().deleteWord(wordModel); //Remove WordModel in Database.
                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
                if(getGenericModels().isEmpty()){
                    updateCategoryWithNoElements(getContextID());
                }
                Toast.makeText(getContext(), "Palavra " + wordModel.getName() + " apagada!", Toast.LENGTH_LONG).show();
                EditWordModelAdapter.super.notifyDataSetChanged();  //Notify ListView that was occurred changes.
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

    //Update ContextModel Object in Database, case not have elements (Words).
    private void updateCategoryWithNoElements(int contextID) {
        try {
            ContextModel contextModel = getDataBase().searchContextDatabase(contextID);
            // If getGenericModel is Empty, the ContextModel Object not have elements (Words).
            contextModel.setHaveElements("false");
            getDataBase().updateContext(contextModel);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }

    }

}
