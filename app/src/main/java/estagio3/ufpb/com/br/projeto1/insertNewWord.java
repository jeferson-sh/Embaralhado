package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class InsertNewWord extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText word;
    private ImageView image;
    private Bitmap bitmap;
    private ImageButton soundbt;
    private DataBase dataBase;
    private String picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_word);
        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        this.word = (EditText) findViewById(R.id.editText);
        this.image =(ImageView) findViewById(R.id.imageView);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.dataBase = new DataBase(this);
        ImageButton menubt = (ImageButton) findViewById(R.id.menuButton);
        ImageButton camera = (ImageButton) findViewById(R.id.activeCameraButton);
        ImageButton gallery = (ImageButton) findViewById(R.id.activeGalleryButton);
        ImageButton savePhotobt = (ImageButton) findViewById(R.id.savePhotoButton);

        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);

        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic(v);

            }
        });
        menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeCamera();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activeGallery();
            }
        });
        savePhotobt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePhoto(v);
            }
        });
    }

    public void activeCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            picturePath = getRealPathFromURI(selectedImage);
            setPic();
        }
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = image.getWidth();
        int targetH = image.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    public void savePhoto(View v) {
        boolean verify = verifyWord();
        if (this.bitmap != null && this.word.getText().toString().length() >= 2 && word.getText().toString().length()<= 10 && verify) {
            dataBase.insertWord(new Word(bitmap, word.getText().toString().toUpperCase()));
            startActivity(new Intent(InsertNewWord.this,SettingsActivity.class));
            finish();

        }else if (this.word.getText().toString().length()>10) {
            Snackbar.make(v, "Word muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }else if(this.word.getText().toString().length() < 2) {
            Snackbar.make(v, "Word muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }else if (!verify){
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }

    }

    private boolean verifyWord(){
        char [] word = this.word.getText().toString().toCharArray();
        boolean b = true;
        for (char aWord : word) {
            if(!Character.isLetter(aWord)) {
                b = false;
                break;
            }
        }
        return b;

    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    private void stopMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(InsertNewWord.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(InsertNewWord.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op3:
                intent = new Intent(this,ScoreActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op4:
                finish();
                System.exit(0);
                return true;
            default:
                return false;
        }
    }
}
