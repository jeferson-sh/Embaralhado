package estagio3.ufpb.com.br.embaralhando.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import estagio3.ufpb.com.br.embaralhando.R;
import estagio3.ufpb.com.br.embaralhando.model.Categorie;
import estagio3.ufpb.com.br.embaralhando.persistence.DataBase;
import estagio3.ufpb.com.br.embaralhando.util.BackgroundSoundServiceUtil;

public class InsertNewContextActivity extends AppCompatActivity {

    private EditText contexrtName;
    private ImageView image;
    private Bitmap bitmap;
    private DataBase dataBase;
    private String picturePath;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_context);
        if (BackgroundSoundServiceUtil.PLAYING)
            startService(new Intent(this, BackgroundSoundServiceUtil.class));
        this.contexrtName = (EditText) findViewById(R.id.editText);
        this.image = (ImageView) findViewById(R.id.imageView);
        this.dataBase = new DataBase(this);

        this.toolbar = (Toolbar) findViewById(R.id.toolbar_new_categorie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_categorie);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ImageButton camera = (ImageButton) findViewById(R.id.activeCameraButton);
        ImageButton gallery = (ImageButton) findViewById(R.id.activeGalleryButton);
        ImageButton savePhotobt = (ImageButton) findViewById(R.id.savePhotoButton);

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
                saveContext(v);
            }
        });
    }

    private void startCategorieActivity() {
        Intent intent = new Intent(InsertNewContextActivity.this, CategoriesActivity.class);
        startActivity(intent);
        finish();
    }

    public void activeCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK && null != data) {
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
        float targetW = getResources().getDimension(R.dimen.newImageWidth);
        float targetH = getResources().getDimension(R.dimen.newImageHeight);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = (int) Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        //bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (this.bitmap != null && this.contexrtName.getText().toString().length() >= 2 && contexrtName.getText().toString().length() <= 10 && verify) {
            dataBase.insertContext(new Categorie(bitmap, contexrtName.getText().toString().toUpperCase()));
            startActivity(new Intent(InsertNewContextActivity.this, CategoriesActivity.class));
            finish();
        } else if (this.contexrtName.getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (this.contexrtName.getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }else if(this.bitmap==null){
            Snackbar.make(v, "Por favor, insira uma foto da galeria ou use a câmera!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
    }

    private boolean verifyWord() {
        char[] word = this.contexrtName.getText().toString().toCharArray();
        boolean b = true;
        for (char aWord : word) {
            if (!Character.isLetter(aWord)) {
                b = false;
                break;
            }
        }
        return b;

    }

    private void controlMusic(MenuItem item) {
        if (BackgroundSoundServiceUtil.PLAYING) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            stopService(new Intent(InsertNewContextActivity.this, BackgroundSoundServiceUtil.class));
            BackgroundSoundServiceUtil.PLAYING = false;
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            startService(new Intent(InsertNewContextActivity.this, BackgroundSoundServiceUtil.class));
            BackgroundSoundServiceUtil.PLAYING = true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        if (!BackgroundSoundServiceUtil.PLAYING) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startCategorieActivity();
                return true;
            case R.id.soundControl:
                controlMusic(item);
                return true;
            case R.id.exitGame:
                exitApp();
                return true;
            default:
                return false;
        }
    }
    private void exitApp() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Deseja sair do jogo?");
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public void onBackPressed() {
        startCategorieActivity();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
