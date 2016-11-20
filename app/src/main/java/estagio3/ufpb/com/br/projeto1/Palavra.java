package estagio3.ufpb.com.br.projeto1;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

/**
 * Created by Jeferson on 10/11/2016.
 */
public class Palavra {

    private byte[] imageBytes;
    private String palavra;
    private Bitmap image;
    private long id;

    public Palavra (long id, byte[] imageBytes, String palavra){
        this.id = id;
        this.palavra = palavra;
        this.imageBytes = imageBytes;
        this.image = DbBitmapUtility.getImage(imageBytes);
    }
    public Palavra (byte[] imageBytes, String palavra){
        this.palavra = palavra;
        this.imageBytes = imageBytes;
        this.image = DbBitmapUtility.getImage(imageBytes);
    }
    public Palavra(Bitmap image, String palavra){
        this.palavra = palavra;
        this.image = image;
        this.imageBytes = DbBitmapUtility.getBytes(this.image);
    }

    public Palavra(Drawable drawable,String palavra) {
        this.image = ((BitmapDrawable) drawable).getBitmap();
        this.palavra = palavra;
        this.imageBytes = DbBitmapUtility.getBytes(this.image);
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

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
