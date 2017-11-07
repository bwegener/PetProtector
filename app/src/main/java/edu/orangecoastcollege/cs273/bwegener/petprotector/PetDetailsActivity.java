package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * The <code>PetDetailsActivity</code> will store the pet's name,
 * details, and phone number, as well as custom image URI
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 10/26/2017
 */
public class PetDetailsActivity extends AppCompatActivity {

    /**
     * The <code>onCreate</code> sets up the details activity
     * and connects it to the xml getting the image, name, details,
     * and phone and sending them to the pet details activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);


        // Connect all the widgets
        ImageView petIV = (ImageView) findViewById(R.id.petImageView);
        TextView petNameTV = (TextView) findViewById(R.id.nameTV);
        TextView petDetailsTV = (TextView) findViewById(R.id.detailsTV);
        TextView petPhoneTV = (TextView) findViewById(R.id.phoneTV);

        Intent detailsIntent = getIntent();
        Uri imageURI = Uri.parse(detailsIntent.getStringExtra("ImageURI"));
        String name = detailsIntent.getStringExtra("Name");
        String details = detailsIntent.getStringExtra("Details");
        String phone = detailsIntent.getStringExtra("Phone");

        petIV.setImageURI(imageURI);
        petNameTV.setText(name);
        petDetailsTV.setText(details);
        petPhoneTV.setText(phone);
    }
}
