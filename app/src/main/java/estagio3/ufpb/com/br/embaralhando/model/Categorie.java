package estagio3.ufpb.com.br.embaralhando.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

import estagio3.ufpb.com.br.embaralhando.util.DatabaseBitmapUtil;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Categorie implements Serializable {

    private byte[] imageBytes;
    private String name;
    private Bitmap image;
    private Integer id;

    public Categorie(Integer id, byte[] imageBytes, String name){
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = DatabaseBitmapUtil.getImage(imageBytes);
    }
    public Categorie(Bitmap image, String name){
        this.name = name;
        this.image = image;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
    }

    public Categorie(Drawable drawable, String name) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
    }

    public Categorie(int id, Drawable drawable, String animais) {
        this.name = null;
        this.image = null;
        this.id = null;
        this.imageBytes = null;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
        this.setImageBytes(DatabaseBitmapUtil.getBytes(image));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
