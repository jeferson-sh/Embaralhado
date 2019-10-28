package com.mydroidtechnology.embaralhado.views;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.exceptions.DatabaseException;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.services.BackgroundMusicService;

import java.io.File;

public abstract class InsertNewGenericDataActivity extends NavigationControlActivity {

    protected EditText editText;
    protected ImageView image;
    protected Bitmap bitmapCaptured;
    protected DataBase dataBase;
    protected Toolbar toolbar;
    protected File file;
    protected Uri uri;
    protected final int RequestPermissionCode = 1;
    protected ImageButton savePhotoBt;
    protected ImageButton camera;
    protected ImageButton gallery;
    protected int permissionCheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_generic_data);
        BackgroundMusicService.setStopBackgroundMusicEnable(true);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        this.editText = findViewById(R.id.editText);
        this.image = findViewById(R.id.imageView);
        this.dataBase = new DataBase(this);
        this.toolbar = findViewById(R.id.toolbar_new_categorie);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.main_menu2);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toolbar_home);
        }
        this.camera = findViewById(R.id.activeCameraButton);
        this.gallery = findViewById(R.id.activeGalleryButton);
        this.savePhotoBt = findViewById(R.id.savePhotoButton);
        this.permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        this.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                    takePhoto();
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    activeGallery();
            }
        });
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();
    }

    private void RequestRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    RequestPermissionCode);
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestPermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                this.permissionCheck = PackageManager.PERMISSION_GRANTED;
            else
                this.permissionCheck = PackageManager.PERMISSION_DENIED;
        }
    }

    public void takePhoto() {
        try {
            Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            this.file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "file" + System.currentTimeMillis() + ".jpg");
            this.uri = Uri.fromFile(file);
            CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            CamIntent.putExtra("return-data", true);
            startActivityForResult(CamIntent, 0);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }

    public void activeGallery() {
        try {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(galleryIntent, "Select Image from Gallery"), 2);
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK)
            CropImage();
        else if (requestCode == 2 && resultCode == RESULT_OK) {
            if (data != null) {
                this.uri = data.getData();
                CropImage();
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    this.bitmapCaptured = bundle.getParcelable("data");
                }
                this.image.setImageBitmap(bitmapCaptured);
                galleryAddPic();
            }
        }
    }

    private void CropImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(uri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 1);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }

    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(this.uri);
        this.sendBroadcast(mediaScanIntent);
    }

    protected void validData(View v) throws DatabaseException {
        if (isInvalidString()) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços vazios ou números.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
            throw new DatabaseException("It's inserting a invalid character.");
        } else if (this.editText.getText().toString().length() > 10) {
            Snackbar.make(v, "Desculpe a palavra muito grande.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
            throw new DatabaseException("It's inserting a word too long.");
        } else if (this.editText.getText().toString().length() < 2) {
            Snackbar.make(v, "Desculpe a palavra muito pequena.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
            throw new DatabaseException("It's inserting a word too short.");
        } else if (this.bitmapCaptured == null) {
            Snackbar.make(v, "Por favor, insira uma foto da galeria ou use a câmera.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
            throw new DatabaseException("It's inserting a empty image.");
        }
    }


    protected boolean isInvalidString() {
        char[] chars = this.editText.getText().toString().toCharArray();
        boolean isValidated = false;
        for (char c : chars) {
            if (!Character.isLetter(c)) {
                isValidated = true;
                break;
            }
        }
        return isValidated;

    }

    @Override
    protected abstract void startActivityOnBackPressed();

    protected abstract void saveData(View v);

}
