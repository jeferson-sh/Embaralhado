package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mydroidtechnology.embaralhado.util.ConvertImageUtil;

/*
 * Created by Jeferson on 10/11/2016.
 */
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
