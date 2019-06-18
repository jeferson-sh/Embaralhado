package com.mydroidtechnology.embaralhado.view;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

import com.mydroidtechnology.embaralhado.R;
import com.mydroidtechnology.embaralhado.persistence.DataBase;
import com.mydroidtechnology.embaralhado.service.BackgroundMusicService;

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
    protected ImageButton savePhotobt;
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

        camera = findViewById(R.id.activeCameraButton);
        gallery = findViewById(R.id.activeGalleryButton);
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

        permissionCheck = ContextCompat.checkSelfPermission(InsertNewGenericDataActivity.this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_DENIED)
            RequestRuntimePermission();
    }

    private void RequestRuntimePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Toast.makeText(this, R.string.access_camera_message, Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(InsertNewGenericDataActivity.this, new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);
        }
    }

    public void activeCamera() {
        Intent CamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = new File(Environment.getExternalStorageDirectory(), "file" + System.currentTimeMillis() + ".jpg");
        uri = Uri.fromFile(file);
        CamIntent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        CamIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        CamIntent.putExtra("return-data", true);
        startActivityForResult(CamIntent, 0);
    }

    public void activeGallery() {
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
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 1);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "ActivityNotFound", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RequestPermissionCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permissão Garantida", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permissão Cancelada", Toast.LENGTH_SHORT).show();
        }
    }

    protected boolean isValidatedData(View v) {
        boolean validatedString = isValidatedString();
        if (this.bitmapCaptured != null && this.editText.getText().toString().length() >= 2 && editText.getText().toString().length() <= 10 && validatedString) {
            return true;
        } else if (this.editText.getText().toString().length() > 10) {
            Snackbar.make(v, "Palavra muito grande!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (this.editText.getText().toString().length() < 2) {
            Snackbar.make(v, "Palavra muito pequena!", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (!validatedString) {
            Snackbar.make(v, "Por favor, cadastre palavras apenas com letras sem espaços vazios ou números.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        } else if (this.bitmapCaptured == null) {
            Snackbar.make(v, "Por favor, insira uma foto da galeria ou use a câmera.", Snackbar.LENGTH_LONG).setAction("OR", null).show();
        }
        return false;
    }


    protected boolean isValidatedString() {
        char[] word = this.editText.getText().toString().toCharArray();
        boolean isValidatedString = true;
        for (char aWord : word) {
            if (!Character.isLetter(aWord)) {
                isValidatedString = false;
                break;
            }
        }
        return isValidatedString;

    }

    @Override
    protected abstract void startActivityOnBackPressed();

    protected abstract void saveData(View v);

}
