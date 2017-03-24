package estagio3.ufpb.com.br.embaralhando;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Context {

    private byte[] imageBytes;
    private String name;
    private Bitmap image;
    private long id;

    public Context(long id, byte[] imageBytes, String name){
        this.id = id;
        this.name = name;
        this.imageBytes = imageBytes;
        this.image = DatabaseBitmapUtility.getImage(imageBytes);
    }
    public Context(Bitmap image, String name){
        this.name = name;
        this.image = image;
        this.imageBytes = DatabaseBitmapUtility.getBytes(this.image);
    }

    public Context(Drawable drawable, String name) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.name = name;
        this.imageBytes = DatabaseBitmapUtility.getBytes(this.image);
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
