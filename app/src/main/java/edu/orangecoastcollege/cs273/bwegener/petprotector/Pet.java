package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.net.Uri;

/**
 * The <code>Pet</code> class sets up a new pet
 * with an ID, name, details, phone number, and image URI.
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 10/25/17.
 */

public class Pet {

    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    /**
     * This default pet constructor, creates a pet with an ID of -1
     * and all of the remaining fields being left blank.
     */
    public Pet() {
        this(-1, "", "", "", Uri.parse(""));
    }

    /**
     * This constructor creates a pet with a name, details, phone, and imageURI,
     * however the id is set at -1.
     * @param name
     * @param details
     * @param phone
     * @param imageURI
     */
    public Pet(String name, String details, String phone, Uri imageURI) {
        this(-1, name, details, phone, imageURI);
    }

    /**
     * This standard constructor creates a pet with an id, name, details, phone number,
     * and imageURI.
     * @param id
     * @param name
     * @param details
     * @param phone
     * @param imageURI
     */
    public Pet(int id, String name, String details, String phone, Uri imageURI) {
        mId = id;
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    /**
     * This gets the ID of the pet
     * @return the id of the pet
     */
    public int getId() { return mId; }

    /**
     * This sets the id of the pet
     * @param id the id of the pet
     */
    public void setId(int id) { mId = id;}

    /**
     * This gets the name of the pet
     * @return the name of the pet
     */
    public String getName() { return mName; }

    /**
     * This sets the name of the pet
     * @param name the name of the pet
     */
    public void setName(String name) { mName = name; }

    /**
     * This gets the details of the pet
     * @return the details of the pet
     */
    public String getDetails() { return mDetails; }

    /**
     * This sets the details of the pet
     * @param details the details of the pet
     */
    public void setDetails(String details) { mDetails = details; }

    /**
     * This gets the phone number for the owner of the pet
     * @return the phone number for the owner of the pet
     */
    public String getPhone() { return mPhone; }

    /**
     * This sets the phone number for the owner of the pet
     * @param phone the phone number for the owner of the pet
     */
    public void setPhone(String phone) { mPhone = phone; }

    /**
     * This gets the image URI for the pet
     * @return the image URI for the pet
     */
    public Uri getImageURI() { return mImageURI; }

    /**
     * This sets the image URI for the pet
     * @param imageURI the image URI for the pet
     */
    public void setImageURI(Uri imageURI) { mImageURI = imageURI; }

    /**
     * This prints the pet with an id, name, details, phone number, and image URI
     * @return
     */
    @Override
    public String toString() {
        return "Pet{" +
                "mId=" + mId +
                ", mName='" + mName + '\'' +
                ", mDetails='" + mDetails + '\'' +
                ", mPhone='" + mPhone + '\'' +
                ", mImageURI=" + mImageURI +
                '}';
    }
}
