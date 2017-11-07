package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PetListActivity</code> handles the activity where the pets
 * are kept in a list.
 * It stores their names, details, and associated phone numbers.
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

    private List<Pet> petList;
    private PetListAdapter petListAdapter;
    private ListView petListView;

    private DBHelper db;

    // Constant for permissions:
    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final int DENIED = PackageManager.PERMISSION_DENIED;


    /**
     * The onCreate sets up the default pet image, calls the xml file,
     * and sets the image URI
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_list);

        petImageView = (ImageView) findViewById(R.id.petImageView);

        // Deletes Pets
        // db.deleteAllPets();

        petImageView.setImageURI(getUriFromResource(this, R.drawable.none));

        db = new DBHelper(this);

        petImageView = (ImageView) findViewById(R.id.petImageView);
        nameET = (EditText) findViewById(R.id.nameET);
        detailsET = (EditText) findViewById(R.id.detailsET);
        phoneET = (EditText) findViewById(R.id.phoneET);
        petListView = (ListView) findViewById(R.id.petListView);

        petList = db.getAllPets();
        petListAdapter = new PetListAdapter(this, R.layout.pet_list_item, petList);
        petListView.setAdapter(petListAdapter);

        imageUri = getUriFromResource(this, R.drawable.none);
        petImageView.setImageURI(imageUri);
    }


    /**
     * This adds a pet to the list and to the database.
     * @param v
     */
    public void addPet(View v)
    {
        String name = nameET.getText().toString();
        String details = detailsET.getText().toString();
        String phone = PhoneNumberUtils.formatNumber(phoneET.getText().toString());

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(details) || TextUtils.isEmpty(phone))
            Toast.makeText(this, "All information must be provided.", Toast.LENGTH_SHORT).show();
        else
        {
            int newPetId = db.addPet(new Pet(name, details, phone, imageUri));
            petList.add(new Pet(newPetId, name, details, phone, imageUri));
            petListAdapter.notifyDataSetChanged();

            nameET.setText("");
            detailsET.setText("");
            phoneET.setText("");
            imageUri = getUriFromResource(this, R.drawable.none);
            petImageView.setImageURI(imageUri);
        }
    }

    /**
     * This allows the user to view the pet details.
     * @param v
     */
    public void viewPetDetails(View v)
    {
        // Retrieves a pet object in tag of selected item
        LinearLayout selectedItem = (LinearLayout) v;
        Pet selectedPet = (Pet) selectedItem.getTag();

        Intent detailsIntent = new Intent(this, PetDetailsActivity.class);
        detailsIntent.putExtra("Name", selectedPet.getName());
        detailsIntent.putExtra("Details", selectedPet.getDetails());
        detailsIntent.putExtra("Phone", selectedPet.getPhone());
        detailsIntent.putExtra("ImageURI", selectedPet.getImageURI().toString());

        startActivity(detailsIntent);
    }


    /**
     * This allows the user to create a custom pet image.
     * Permissions need to check first if the user will allow the camera or galleries to be
     * accessed.
     * @param v
     */
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

    /**
     * This is the result if the user allowed permissions for camera/gallery
     * @param requestCode
     * @param resultCode
     * @param data
     */
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

    /**
     * The static <code>getUriFromResource</code> gets the uri
     * from the package, type, and name
     * @param context
     * @param resId
     * @return
     */
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
}
