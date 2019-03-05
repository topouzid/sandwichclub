package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);


        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
        } catch (JSONException e) {

        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String mDescription = sandwich.getDescription();
        List<String> mOtherNamesList = sandwich.getAlsoKnownAs();
        String mOtherNamesString = "";
        /* Create a string with other known names for the sandwich */
        for (int i=0; i<mOtherNamesList.size(); i++) {
            mOtherNamesString += mOtherNamesList.get(i);
            /*Add a comma (,) if there are more names, or a period (.) if it was the last known name */
            if (i<mOtherNamesList.size()-1) {
                mOtherNamesString += ", ";
            } else {
                mOtherNamesString += "";
            }
        }
        if (mOtherNamesString == "") {
            mOtherNamesString = getString(R.string.unknown_names);
        }
        String mOrigin = sandwich.getPlaceOfOrigin();
        List<String> mIngredientsList = sandwich.getIngredients();
        String mIngredientsString = "";
        /* Create a string with the ingredients */
        for (int i=0; i<(mIngredientsList.size()); i++) {
            mIngredientsString += mIngredientsList.get(i);
            /* Add a comma (,) if there are more ingredients, or a period (.) if it was the last ingredient */
            if (i<mIngredientsList.size()-1) {
                mIngredientsString += ", ";
            } else {
                mIngredientsString += ".";
            }
        }
        if (mIngredientsString == ".") {
            mIngredientsString = getString(R.string.unknown_recipe);
        }

        /* populate the views */
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        TextView alsoKnownTextView = (TextView) findViewById(R.id.also_known_tv);
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        TextView originTextView = (TextView) findViewById(R.id.origin_tv);

        /* set text to the views */
        descriptionTextView.setText(mDescription);
        originTextView.setText(mOrigin);
        ingredientsTextView.setText(mIngredientsString);
        alsoKnownTextView.setText(mOtherNamesString);
    }
}
