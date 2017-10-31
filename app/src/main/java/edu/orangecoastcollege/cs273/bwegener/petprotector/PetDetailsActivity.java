package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
    }
}
