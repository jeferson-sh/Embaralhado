package estagio3.ufpb.com.br.projeto1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AdicionarPalavras extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private EditText palavra;
    private PalavrasApplication palavrasApplication;
    private ImageView imagem;
    private byte [] imagemByte;
    private ImageButton menubt;
    private ImageButton soundbt;


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
    }

    public void tirarFoto(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data != null){
            Bundle bundle = data.getExtras();
            if(bundle != null){
                Bitmap img = (Bitmap) bundle.get("data");
                imagemByte = DbBitmapUtility.getBytes(img);
                imagem.setImageBitmap(img);
            }
        }
    }

    public void salvarFoto(View view) {
            palavrasApplication.addPalavras(imagemByte,palavra.getText().toString().toUpperCase());

    }

    private void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
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
