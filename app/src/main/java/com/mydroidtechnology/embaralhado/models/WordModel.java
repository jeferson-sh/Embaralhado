package com.mydroidtechnology.embaralhado.models;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class WordModel extends GenericModel {

    private Integer contextID; // ContextModel id that the word is save.

    //This constructor is called in the Database.
    public WordModel(Integer id, byte[] imageBytes, String name, Integer contextID){
        super(id,imageBytes,name);
        this.contextID = contextID;
    }

    //This constructor is called when inserting new Words.
    public WordModel(Bitmap image, String name, Integer contextID){
        super(image,name);
        this.contextID = contextID;
    }

    //This constructor is called when the app runs the first time.
    public WordModel(Drawable drawable, String name, Integer contextID) {
        super(drawable,name);
        this.contextID = contextID;
    }

    public Integer getContextID() {
        return contextID;
    }

    public void setContextID(Integer contextID) {
        this.contextID = contextID;
    }
}
