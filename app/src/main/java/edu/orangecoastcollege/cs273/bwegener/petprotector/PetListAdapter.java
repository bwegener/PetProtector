package edu.orangecoastcollege.cs273.bwegener.petprotector;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>PetListAdapter</code> will be a custom
 * list adapter where all the pets in the database will be stored.
 *
 * @author Brian Wegener
 * @version 1.0
 *
 * Created on 10/25/17.
 */

public class PetListAdapter extends ArrayAdapter<Pet>
{
    private Context mContext;
    private int mResourceId;
    private List<Pet> mPetsList = new ArrayList<>();

    /**
     * The <code>petListAdapter</code> creates a special list within the <code>PetListActivity</code>
     * by creating the context, resourceID, and the petsList
     * @param context
     * @param resourceId
     * @param petsList
     */
    public PetListAdapter(Context context, int resourceId, List<Pet> petsList)
    {
        super(context, resourceId, petsList);
        mContext = context;
        mResourceId = resourceId;
        mPetsList = petsList;
    }

    /**
     * This special view inflates the linearLayout within the <code>PetsListActivity</code>.
     * It connects the ImageView, NameTextView, and DetailsTextView to the bottom of the
     * screen.
     * @param position
     * @param convertView
     * @param parent
     * @return the view.
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId, null);

        LinearLayout listItemLinearLayout = (LinearLayout) view.findViewById(R.id.petListLinearLayout);
        ImageView listItemIV = (ImageView) view.findViewById(R.id.petListIV);
        TextView listItemNameTV = (TextView)  view.findViewById(R.id.petListNameTV);
        TextView listItemDetailsTV = (TextView) view.findViewById(R.id.petListDetailsTV);

        Pet selectedPet = mPetsList.get(position);
        listItemIV.setImageURI(selectedPet.getImageURI());
        listItemNameTV.setText(selectedPet.getName());
        listItemDetailsTV.setText(selectedPet.getDetails());

        listItemLinearLayout.setTag(selectedPet);

        return view;
    }
}
