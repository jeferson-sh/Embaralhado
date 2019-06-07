package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class Word extends GenericModel {

    private Integer contextID;

    public Word(Integer id, byte[] imageBytes, String name, Integer contextID){
        super(id,imageBytes,name);
        this.contextID = contextID;
    }
    public Word(Bitmap image, String name,Integer contextID){
        super(image,name);
        this.contextID = contextID;
    }

    public Word(Drawable drawable, String name, Integer contextID) {
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
