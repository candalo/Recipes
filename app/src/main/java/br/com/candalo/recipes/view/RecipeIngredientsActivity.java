package br.com.candalo.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeIngredient;

public class RecipeIngredientsActivity extends AppCompatActivity {

    private List<RecipeIngredient> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        ingredients = getIngredients();
        setupActionBar();
        sendIngredientsToFragment();
    }

    private List<RecipeIngredient> getIngredients() {
        return Parcels.unwrap(getIntent().getParcelableExtra(RecipeIngredient.class.getName()));
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(getString(R.string.recipe_ingredients));
        }
    }

    private void sendIngredientsToFragment() {
        RecipeIngredientsFragment fragment =
                (RecipeIngredientsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_ingredients);
        fragment.setIngredients(ingredients);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
