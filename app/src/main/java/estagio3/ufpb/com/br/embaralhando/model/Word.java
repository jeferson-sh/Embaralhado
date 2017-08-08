package estagio3.ufpb.com.br.embaralhando.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import estagio3.ufpb.com.br.embaralhando.util.DatabaseBitmapUtil;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Word {

    private byte[] imageBytes;
    private String name;
    private Bitmap image;
    private long id;
    private String context;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Word(long id, byte[] imageBytes, String name, String context){
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = DatabaseBitmapUtil.getImage(imageBytes);
        this.context = context;
    }
    public Word(Bitmap image, String name,String context){
        this.name = name;
        this.image = image;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
        this.context = context;
    }

    public Word(Drawable drawable, String name, String context) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = DatabaseBitmapUtil.getBytes(this.image);
        this.context = context;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
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
