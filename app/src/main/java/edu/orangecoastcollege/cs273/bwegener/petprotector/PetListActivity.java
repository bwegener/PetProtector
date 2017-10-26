package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 10/26/2017
 */
public class PetListActivity extends AppCompatActivity {

    private ImageView petImageView;

    private Uri imageUri;

    private EditText nameET;
    private EditText detailsET;
    private EditText phoneET;

    SQLiteDatabase db;

    // Constant for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));
    }


    public void selectPetImage(View v)
    {
        List<String> permsList = new ArrayList<>();

        // Check each permission individually
        int hasCameraPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if(hasCameraPerm == DENIED)
            permsList.add(Manifest.permission.CAMERA);

        int hasReadPerm = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(hasReadPerm == DENIED)
            permsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);

        int hasWritePerm = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(hasWritePerm == DENIED)
            permsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // Some of the permissions have not been granted
        if(permsList.size() > 0)
        {
            // Convert the permsList into an array:
            String[] permsArray = new String[permsList.size()];
            permsList.toArray(permsArray);

            // Ask user for them:
            ActivityCompat.requestPermissions(this, permsArray, 1337);
        }

        // Let's make sure we have all the permissions, then start up the Image Gallery:
        if(hasCameraPerm == GRANTED && hasReadPerm == GRANTED && hasWritePerm == GRANTED)
        {
            // Let's open up the image gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            // Start activity for a result (picture)
            startActivityForResult(galleryIntent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null)
        {
            // data = data from galleryIntent (the URI of some image)
            imageUri = data.getData();

            // sets the image to the imageUri
            petImageView.setImageURI(imageUri);
        }

    }

    // This Code never changes
    public static Uri getUriFromResource(Context context, int resId)
    {
        Resources res = context.getResources();

        // Build a String in the form:
        // android.resource://edu.orangecoastcollege.cs273.petprotector/drawable/none
        String uri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                res.getResourcePackageName(resId) + "/" +
                res.getResourceTypeName(resId) + "/" +
                res.getResourceEntryName(resId);

        // Parse the String in order to construct a URI
        return Uri.parse(uri);

    }

    public void addPet(View v)
    {
        nameET = (EditText) findViewById(R.id.nameET);
        detailsET = (EditText) findViewById(R.id.detailsET);
        phoneET = (EditText) findViewById(R.id.phoneET);

        String name = nameET.getText().toString();
        String details = detailsET.getText().toString();
        String phone = phoneET.getText().toString();


        Pet pet = new Pet(name, details, phone, imageUri);

        db.addPet(pet);


    }
}
