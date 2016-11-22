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

import java.io.IOException;

public class AdicionarPalavras extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText palavra;
    private ImageView imagem;
    private Bitmap bitmap;
    private ImageButton menubt;
    private ImageButton soundbt;
    private ImageButton camera;
    private ImageButton galeria;
    private ImageButton salvarFoto;
    private BD bd;
    private String picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palavras);
        if(BackgroundSoundService.ISPLAY)
            startService(new Intent(this,BackgroundSoundService.class));
        this.palavra = (EditText) findViewById(R.id.editText);
        this.imagem =(ImageView) findViewById(R.id.imageView1);
        this.menubt = (ImageButton) findViewById(R.id.menuButton);
        this.soundbt = (ImageButton) findViewById(R.id.somButton);
        this.camera = (ImageButton) findViewById(R.id.ativar_camera);
        this.galeria = (ImageButton) findViewById(R.id.ativar_galeria);
        this.salvarFoto = (ImageButton) findViewById(R.id.salvar_foto);
        this.bd = new BD(this);

        if(!BackgroundSoundService.ISPLAY)
            this.soundbt.setBackgroundResource(R.drawable.not_speaker);

        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic(v);

            }
        });
        this.menubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });
        this.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ativarCamera();
            }
        });
        this.galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ativarGaleria();
            }
        });
        this.salvarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarFoto(v);
            }
        });
    }

    public void ativarCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void ativarGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 0 && resultCode == RESULT_OK && null != data){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                this.bitmap = (Bitmap) bundle.get("data");
                imagem.setImageBitmap(bitmap);
            }
        }else if(requestCode == 1 && resultCode == RESULT_OK && null != data){
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
        int targetW = imagem.getWidth();
        int targetH = imagem.getHeight();

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
        imagem.setImageBitmap(bitmap);
    }

    public void salvarFoto(View v) {
        if (this.palavra.getText().toString().length()>10)
            Snackbar.make(v,"Palavra muito grande!",Snackbar.LENGTH_SHORT).setAction("OR",null).show();
        if(this.palavra.getText().toString().length() < 2)
            Snackbar.make(v,"Palavra muito pequena!",Snackbar.LENGTH_SHORT).setAction("OR",null).show();
        if (this.bitmap != null && this.palavra.getText().toString().length() >= 2 && palavra.getText().toString().length()<= 10) {
            bd.inserirPalavra(new Palavra(bitmap, palavra.getText().toString().toUpperCase()));
            startActivity(new Intent(AdicionarPalavras.this,SettingsActivity.class));
            finish();

        }
    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.main_menu);
        popup.show();
    }

    private void stopMusic(View v) {
        if(BackgroundSoundService.ISPLAY){
            this.soundbt.setBackgroundResource(R.drawable.not_sound_button);
            stopService(new Intent(AdicionarPalavras.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.sound_button);
            startService(new Intent(AdicionarPalavras.this, BackgroundSoundService.class));
            BackgroundSoundService.ISPLAY = true;
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
            default:
                return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
