package com.example.alexandre.bar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class AddToDoActivity extends AppCompatActivity {

    private android.widget.EditText editTitle;
    private android.widget.EditText editDescription;
    private ImageView imageView;

    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        this.editDescription = (EditText) findViewById(R.id.editDescription);
        this.editTitle = (EditText) findViewById(R.id.editTitle);
        imageView = (ImageView) findViewById(R.id.imageView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (data.getData() != null && requestCode == 0) {
                Uri selectedImageUri = data.getData();
                imageView.setImageURI(selectedImageUri);
            }
            else {
                File file = new File(imagePath);
                Glide.with(this).load(file).into(imageView);
//                stream = getActivity().getContentResolver().openInputStream(Uri.fromFile(file));
            }
        }
    }

    public void saveOnParse(View view) {

        String title = editTitle.getText().toString();
        String description = editDescription.getText().toString();

        if (title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "VAZIO", Toast.LENGTH_LONG).show();
        }
        else {
            ParseObject object = new ParseObject("TODO");
            object.put("title", title);
            object.put("description", description);
            object.put("author", ParseUser.getCurrentUser());

            try {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream ous = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, ous);

                ParseFile file = new ParseFile("image.jpg",  ous.toByteArray());
                object.put("image", file);

            }catch (Exception e) {}

            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null)
                        finish();
                    else
                        Toast.makeText(getBaseContext(), "VAZIO", Toast.LENGTH_LONG).show();
                }
            });

        }

    }

    public void openGallery(View view) {

        Intent it = new Intent();
        it.setType("image/*");
        it.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(it, 0);
    }

    public void openCamera(View view) {

        File file = new File(getExternalFilesDir("imagem"), "image.jpg");

        imagePath = file.getAbsolutePath();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(file));

        startActivityForResult(takePictureIntent, 1);


    }
}
