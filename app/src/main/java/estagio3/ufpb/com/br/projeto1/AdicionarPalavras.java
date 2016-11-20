package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

public class AdicionarPalavras extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText palavra;
    private PalavrasApplication palavrasApplication;
    private ImageView imagem;
    private byte [] imagemByte;
    private ImageButton menubt;
    private ImageButton soundbt;
    private ImageButton camera;
    private ImageButton galeria;
    private ImageButton salvarFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_palavras);
        if(BackgroundSoundService.ISPLAY)
            startService(new Intent(this,BackgroundSoundService.class));
        palavrasApplication = (PalavrasApplication) AdicionarPalavras.this.getApplicationContext();
        this.palavra = (EditText) findViewById(R.id.editText);
        this.imagem =(ImageView) findViewById(R.id.imageView1);
        this.menubt = (ImageButton) findViewById(R.id.menuButton);
        this.soundbt = (ImageButton) findViewById(R.id.somButton);
        this.camera = (ImageButton) findViewById(R.id.ativar_camera);
        this.galeria = (ImageButton) findViewById(R.id.ativar_galeria);
        this.salvarFoto = (ImageButton) findViewById(R.id.salvar_foto);

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
                salvarFoto();
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
                Bitmap img = (Bitmap) bundle.get("data");
                imagemByte = DbBitmapUtility.getBytes(img);
                imagem.setImageBitmap(img);
            }
        }else if(requestCode == 1 && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            try {
                Bitmap img = MediaStore.Images.Media.getBitmap(getContentResolver(),selectedImage);
                if (img != null) {
                    imagemByte = DbBitmapUtility.getBytes(img);
                    imagem.setImageBitmap(img);
                }
            } catch (IOException e) {e.printStackTrace();}

        }
    }

    public void salvarFoto() {
        palavrasApplication.addPalavras(imagemByte,palavra.getText().toString().toUpperCase());

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
