package com.mydroidtechnology.embaralhado.view;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Method;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.model.Categorie;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

public class InsertNewCategoriesActivity extends AppCompatActivity {

    private EditText editText;
    private ImageView image;
    private Bitmap bitmapCaptured;
    private DataBase dataBase;
    private Toolbar toolbar;
    private File file;
    private Uri uri;
    private final int RequestPermissionCode = 1;
    private ImageButton savePhotobt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_context);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);

        this.editText = findViewById(R.id.editText);
        this.image = findViewById(R.id.imageView);
        this.dataBase = new DataBase(this);

        this.toolbar = findViewById(R.id.toolbar_new_categorie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.register_new_categorie);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }

        ImageButton camera = findViewById(R.id.activeCameraButton);
        ImageButton gallery = findViewById(R.id.activeGalleryButton);
        savePhotobt = findViewById(R.id.savePhotoButton);

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

        int permissionCheck = ContextCompat.checkSelfPermission(InsertNewCategoriesActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();
    }

    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(InsertNewCategoriesActivity.this, Manifest.permission.CAMERA))
            Toast.makeText(this, "CAMERA permission allows us to access CAMERA app", Toast.LENGTH_SHORT).show();
        else {
            ActivityCompat.requestPermissions(InsertNewCategoriesActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    protected void startCategorieActivity() {
        Intent intent = new Intent(InsertNewCategoriesActivity.this, CategoriesActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    protected void startMainActivity() {
        Intent intent = new Intent(InsertNewCategoriesActivity.this, MainActivity.class);
        BackgroundMusicService.setStopBackgroundMusicEnable(false);
        startActivity(intent);
        finish();
    }

    public void activeCamera() {
        Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(), "file" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", true);
        if(Build.VERSION.SDK_INT>=24) {
            try {
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        startActivityForResult(CamIntent, 0);
    }

    private void activeGallery() {
        Intent GalIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(GalIntent, "Select Image from Gallery"), 2);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK)
            CropImage();
        else if (requestCode == 2) {
            if (data != null) {
                uri = data.getData();
                CropImage();
            }
        } else if (requestCode == 1) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                bitmapCaptured = bundle.getParcelable("data");
                image.setImageBitmap(bitmapCaptured);
            }
        }
    }

    private void CropImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");

            cropIntent.putExtra("crop", "true");
            //cropIntent.putExtra("outputX", 180);
            //cropIntent.putExtra("outputY", 180);
            //cropIntent.putExtra("aspectX", 4);
            //cropIntent.putExtra("aspectY", 4);
            //cropIntent.putExtra("scaleUpIfNeeded", false);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 1);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "ActivityNotFound", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "Permission Canceled", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (this.bitmapCaptured != null && this.editText.getText().toString().length() >= 2 && editText.getText().toString().length() <= 10 && verify) {
            dataBase.insertCategorie(new Categorie(bitmapCaptured, editText.getText().toString().toUpperCase()));
            BackgroundMusicService.setStopBackgroundMusicEnable(false);
            startActivity(new Intent(InsertNewCategoriesActivity.this, CategoriesActivity.class));
            finish();
        } else if (this.editText.getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (this.editText.getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!verify) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços ou números!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (this.bitmapCaptured == null) {
            Snackbar.make(v, "Por favor, insira uma foto da galeria ou use a câmera!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
    }

    protected boolean verifyWord() {
        char[] word = this.editText.getText().toString().toCharArray();
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
        if (BackgroundMusicService.isPlaying()) {
            item.setIcon(R.drawable.ic_volume_mute_white);
            BackgroundMusicService.getMediaPlayer().pause();
            BackgroundMusicService.setIsPlaying(false);
        } else {
            item.setIcon(R.drawable.ic_volume_up_white);
            BackgroundMusicService.getMediaPlayer().start();
            BackgroundMusicService.setIsPlaying(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu2, menu);
        if (!BackgroundMusicService.isPlaying()) {
            menu.getItem(0).setIcon(R.drawable.ic_volume_mute_white);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startMainActivity();
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
                stopService(new Intent(InsertNewCategoriesActivity.this,BackgroundMusicService.class));
                finish();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    public EditText getEditText() {
        return editText;
    }

    public ImageView getImageView() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public Bitmap getBitmapCaptured() {
        return bitmapCaptured;
    }

    public void setBitmapCaptured(Bitmap bitmapCaptured) {
        this.bitmapCaptured = bitmapCaptured;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    public void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ImageButton getSavePhotobt() {
        return savePhotobt;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isStopBackgroundMusicEnable())
            BackgroundMusicService.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BackgroundMusicService.getMediaPlayer() != null && BackgroundMusicService.isPlaying())
            BackgroundMusicService.getMediaPlayer().start();
    }

}
