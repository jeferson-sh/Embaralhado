package estagio3.ufpb.com.br.embaralhando.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import estagio3.ufpb.com.br.embaralhando.util.DatabaseBitmapUtil;

/*
 * Created by Jeferson on 10/11/2016.
 */
public class Word {

    private byte[] imageBytes;
    private String name;
    private Bitmap image;
    private Integer id;
    private Integer context;

    public Integer getContext() {
        return context;
    }

    public void setContext(Integer context) {
        this.context = context;
    }

    public Word(Integer id, byte[] imageBytes, String name, Integer context){
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = DatabaseBitmapUtil.getImage(imageBytes);
        this.context = context;
    }
    public Word(Bitmap image, String name,Integer context){
        this.name = name;
        this.image = image;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
        this.context = context;
    }

    public Word(Drawable drawable, String name, Integer context) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
        this.context = context;
    }

    public int getId() {
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
        setImageBytes(DatabaseBitmapUtil.getBytes(image));
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

    private void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
