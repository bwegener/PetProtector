package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.net.Uri;

import java.net.URI;

/**
 * Created by Brandy on 10/25/17.
 */

public class Pet {

    private int mId;
    private String mName;
    private String mDetails;
    private String mPhone;
    private Uri mImageURI;

    public Pet() {
        this(-1, "", "", "", "");
    }

    public Pet(String name, String details, String phone, Uri imageURI) {
        this(-1, name, details, phone, imageURI);
    }

    public Pet(String name, String details, String phone, Uri imageURI) {
        mName = name;
        mDetails = details;
        mPhone = phone;
        mImageURI = imageURI;
    }

    public int getId() { return mId; }

    public void setId(int id) { mId = id;}

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public String getDetails() { return mDetails; }

    public void setDetails(String details) { mDetails = details; }

    public String getPhone() { return mPhone; }

    public void setPhone(String phone) { mPhone = phone; }

    public Uri getImageURI() { return mImageURI; }

    public void setImageURI(Uri imageURI) { mImageURI = imageURI; }

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
