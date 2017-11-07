package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

/**
 * The <code>DBHelper</code> creates a link to the
 * SQLite database and allows for the database to be added to,
 * upgraded, deleted, or get all elements from the database
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 10/25/17.
 */

class DBHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "PetProtector";
    private static final String DATABASE_TABLE = "Pets";
    private static final int DATABASE_VERSION = 1;

    private static final String KEY_FIELD_ID = "_id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_DETAILS = "details";
    private static final String FIELD_PHONE = "phone";
    private static final String FIELD_IMAGE_URI = "imageURI";


    /**
     * This sets up the Database.
     * @param context
     */
    public DBHelper(Context context) { super (context, DATABASE_NAME, null, DATABASE_VERSION); }

    /**
     * The onCreate sets up the table, with the key, name, details, phone, and image uri
     * @param database
     */
    @Override
    public void onCreate(SQLiteDatabase database) {
        String table = "CREATE TABLE " + DATABASE_TABLE + "(" +
                KEY_FIELD_ID + " INTEGER PRIMARY KEY, " +
                FIELD_NAME + " TEXT, " +
                FIELD_DETAILS + " TEXT, " +
                FIELD_PHONE + " TEXT, " +
                FIELD_IMAGE_URI + " TEXT" + ")";
        database.execSQL(table);
    }

    /**
     * This upgrades the database table, by first dropping it
     * then calling the onCreate
     * @param database
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    /**
     * This adds a pet to the database
     * @param pet
     */
    public int addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_NAME, pet.getName());
        values.put(FIELD_DETAILS, pet.getDetails());
        values.put(FIELD_PHONE, pet.getPhone());
        values.put(FIELD_IMAGE_URI, pet.getImageURI().toString());

        long id = db.insert(DATABASE_TABLE, null, values);
        pet.setId((int) id);
        db.close();

        return (int) id;
    }

    /**
     * This gets all the pets from the database
     * @return
     */
    public ArrayList<Pet> getAllPets() {
        ArrayList<Pet> petList = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(
                DATABASE_TABLE, new String[]{KEY_FIELD_ID, FIELD_NAME,
                FIELD_DETAILS, FIELD_PHONE, FIELD_IMAGE_URI},
                null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getString(3),
                        Uri.parse(cursor.getString(4)));
                petList.add(pet);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return petList;
    }

    /**
     * This deletes all the pets from the database
     */
    public void deleteAllPets()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }
}
