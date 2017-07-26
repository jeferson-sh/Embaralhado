package estagio3.ufpb.com.br.embaralhando;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class InsertNewContextActivity extends AppCompatActivity{

    private EditText contexrtName;
    private ImageView image;
    private Bitmap bitmap;
    private ImageButton soundbt;
    private DataBase dataBase;
    private String picturePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_new_context);
        if(BackgroundSoundService.PLAYING)
            startService(new Intent(this,BackgroundSoundService.class));
        this.contexrtName = (EditText) findViewById(R.id.editText);
        this.image =(ImageView) findViewById(R.id.imageView);
        this.soundbt = (ImageButton) findViewById(R.id.soundButton);
        this.dataBase = new DataBase(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.inflateMenu(R.menu.main_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon,getTheme()));
        }else{
            toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.menu_button_icon));
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertNewContextActivity.this,CategorieActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ImageButton camera = (ImageButton) findViewById(R.id.activeCameraButton);
        ImageButton gallery = (ImageButton) findViewById(R.id.activeGalleryButton);
        ImageButton savePhotobt = (ImageButton) findViewById(R.id.savePhotoButton);



        if(!BackgroundSoundService.PLAYING)
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);

        this.soundbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlMusic(v);

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
                saveContext(v);
            }
        });
    }

    public void activeCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    private void activeGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
        float targetW = getResources().getDimension(R.dimen.newImageWidth);
        float targetH = getResources().getDimension(R.dimen.newImageHeight);

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = (int) Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(picturePath, bmOptions);
        image.setImageBitmap(bitmap);
    }

    public void saveContext(View v) {
        boolean verify = verifyWord();
        if (this.bitmap != null && this.contexrtName.getText().toString().length() >= 2 && contexrtName.getText().toString().length()<= 10 && verify) {
            dataBase.insertContext(new Categorie(bitmap, contexrtName.getText().toString().toUpperCase()));
            startActivity(new Intent(InsertNewContextActivity.this,CategorieActivity.class));
            finish();
        }
    }

    private boolean verifyWord(){
        char [] word = this.contexrtName.getText().toString().toCharArray();
        boolean b = true;
        for (char aWord : word) {
            if(!Character.isLetter(aWord)) {
                b = false;
                break;
            }
        }
        return b;

    }
    private void controlMusic(View v) {
        if(BackgroundSoundService.PLAYING){
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_mute_white);
            stopService(new Intent(InsertNewContextActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = false;
        }else{
            this.soundbt.setBackgroundResource(R.drawable.ic_volume_up_white);
            startService(new Intent(InsertNewContextActivity.this, BackgroundSoundService.class));
            BackgroundSoundService.PLAYING = true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.op1:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.op2:
                intent = new Intent(this,CategorieActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.moveTaskToBack(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
