package com.mydroidtechnology.embaralhado.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.mydroidtechnology.embaralhado.util.ConvertImageUtil;
// This class has a default data for all register in the app.
public class GenericModel {
    private byte[] imageBytes; //Array bytes of images of the word.
    private String name; //Name assigned to the word.
    private Bitmap image; //Image assigned to the word.
    private Integer id; //id control for Database.

    //This constructor is called when reading data (Words , Contexts or Scores) in database.
    GenericModel(Integer id, byte[] imageBytes, String name) {
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = ConvertImageUtil.getImage(imageBytes);
    }

    //This constructor is called when inserting new data (Words , Contexts or Scores).
    GenericModel(Bitmap image, String name) {
        this.name = name;
        this.image = image;
        this.imageBytes = ConvertImageUtil.getBytes(this.image);
    }

    //This constructor is called when the app runs the first time.
    GenericModel(Drawable drawable, String name) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = ConvertImageUtil.getBytes(this.image);
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    private void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.setImageBytes(ConvertImageUtil.getBytes(image));
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
