package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity
{

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
 /*
    @BindView(R.id.image_iv) ImageView ingredientsIv;
    @BindView(R.id.also_known_tv) TextView alsoKnownAs_tv;
    @BindView(R.id.origin_tv) TextView plceOfOrigin_tv;
    @BindView(R.id.description_tv) TextView decription_tv;
    @BindView(R.id.ingredients_tv) TextView ingredients_tv;
    @BindString(R.string.detail_error_message) String errorMessage;
 */
    TextView alsoKnownAs_tv, plceOfOrigin_tv, decription_tv, ingredients_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
       // ButterKnife.bind(this);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAs_tv= findViewById(R.id.also_known_tv);
        plceOfOrigin_tv = findViewById(R.id.origin_tv);
        decription_tv = findViewById(R.id.description_tv);
        ingredients_tv = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null)
        {
            closeOnError();
            return;
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION)
        {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null)
        {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError()
    {
        finish();
        Toast.makeText(this,R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich)
    {
        //set text to AlsoKnownAs_Tv
        List<String> alsoKnownAs_List = sandwich.getAlsoKnownAs();
        String alsoKnownAs;
        if(alsoKnownAs_List.isEmpty())
        {
            alsoKnownAs = getString(R.string.no_alias);

        }else {
            StringBuilder stringBuilder_also = new StringBuilder();

            for (int i =0;i <alsoKnownAs_List.size(); i++)
            {
                stringBuilder_also.append("\u2022 "); // for disply Bullet Points
                stringBuilder_also.append(sandwich.getAlsoKnownAs().get(i));
                if(i!= alsoKnownAs_List.size() -1)
                {
                    stringBuilder_also.append("\n");
                }
            }
            alsoKnownAs = stringBuilder_also.toString();
        }
        alsoKnownAs_tv.setText(alsoKnownAs);
        //Place of Origin of Sandwich
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin ==  null || placeOfOrigin.equals(""))
        {
            placeOfOrigin = getString(R.string.unKnown_origin);
        }
        plceOfOrigin_tv.setText(placeOfOrigin);

        //Description of Sandwich
        String description = sandwich.getDescription();
        if (description == null || description.equals(""))
        {
            description =getString(R.string.unKnown_description);
        }
        decription_tv.setText(description);

        //Ingredients of Sandwich
        String ingredients;
        List<String> ingredients_List = sandwich.getIngredients();
        if (ingredients_List.isEmpty())
        {
            ingredients = getString(R.string.unKnown_ingredients);
        } else {
           StringBuilder sb_ingredients = new StringBuilder();
           for (int i=0; i < ingredients_List.size(); i++)
           {
               sb_ingredients.append("\u2022 ") // for disply Bullet Point
                       .append(ingredients_List.get(i));
               //No Line Break for Last Ingredient
               if (i !=ingredients_List.size() - 1)
                   sb_ingredients.append("\n");
           }
            ingredients = sb_ingredients.toString();
        }
        ingredients_tv.setText(ingredients);

    }
}
