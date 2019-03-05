package com.udacity.sandwichclub.utils;

//import android.app.Application;
//import android.content.res.Resources;
import android.util.Log;

import com.udacity.sandwichclub.DetailActivity;
//import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility functions to handle Sandwich JSON data
 */
public class JsonUtils extends DetailActivity {


    /**
     * This method parses JSON from strings.xml and returns Sandwich object
     * describing the information about the sandwich.
     *
     * @param json It's the JSON string from Strings.xml
     * @return Returns a Sandwich object
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static Sandwich parseSandwichJson(String json) throws JSONException {
        /* Sandwich information. Each type of sandwich info is an element of the "list" array */
        final String OWN_LIST = "list";

        /* mainName and alsoKnownAs are children of name object */
        final String SW_NAME_OBJECT = "name";

        /* main name of the sandwich */
        final String SW_MAIN_NAME = "mainName";
        /* other names for the same sandwich */
        final String SW_ALSO_KNOWN_AS_ARRAY = "alsoKnownAs";

        /* place of origin of the sandwich */
        final String SW_PLACE_OF_ORIGIN = "placeOfOrigin";

        /* description of the sandwich */
        final String SW_DESCRIPTION = "description";

        /* image url of the sandwich */
        final String SW_IMAGE = "image";

        /* an array list for the ingredients of the sandwich */
        final String SW_INGREDIENTS_ARRAY = "ingredients";


        /* Get sandwich object representing all info from a specific sandwich */
        JSONObject sandwichInfo = new JSONObject(json);

        /* Get the JSON object representing the sandwich name */
        JSONObject sandwichName = sandwichInfo.getJSONObject(SW_NAME_OBJECT);

        /* Get the JSON string for the name */
        String mainName = sandwichName.getString(SW_MAIN_NAME);

        /* Get the other names as an array */
        JSONArray alsoKnownAsArray = sandwichName.getJSONArray(SW_ALSO_KNOWN_AS_ARRAY);

        /* get the list of other names from the json array */
        List<String> alsoKnownAsList = new ArrayList<>();
        if (alsoKnownAsArray.length() == 0) {
            alsoKnownAsList.add(0, "");
        } else {
            for (int j = 0; j < alsoKnownAsArray.length(); j++) {
                String otherName = alsoKnownAsArray.getString(j);
                alsoKnownAsList.add(j, otherName);
            }
        }

        /* Get sandwich's place of origin String */
        String placeOfOrigin = sandwichInfo.getString(SW_PLACE_OF_ORIGIN);
        if (placeOfOrigin.equals("")) {
            placeOfOrigin = "Unknown";
        }

        /* Get sandwich's description String */
        String sandwichDescription = sandwichInfo.getString(SW_DESCRIPTION);

        /* Get sandwich's image URL in a String */
        String sandwichImageUrl = sandwichInfo.getString(SW_IMAGE);

        /* Get ingredients array */
        JSONArray sandwichIngredientsArray = sandwichInfo.getJSONArray(SW_INGREDIENTS_ARRAY);

        /* Transform array of ingredients to a list of ingredients */
        List<String> sandwichIngredientsList = new ArrayList<>();
        if (sandwichIngredientsArray.length() == 0) {
            sandwichIngredientsList.add(0, "");
        } else {
            for (int k = 0; k < sandwichIngredientsArray.length(); k++) {
                String ingredient = sandwichIngredientsArray.getString(k);
                sandwichIngredientsList.add(k, ingredient);
            }
        }

        /* return a new sandwich type data */
        return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, sandwichDescription, sandwichImageUrl, sandwichIngredientsList);
    }
}
