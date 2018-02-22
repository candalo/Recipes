package br.com.candalo.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.data.datasource.RecipeDatabase;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeIngredient;

public class RecipeIngredientsActivity extends AppCompatActivity {

    public static final String WIDGET_ITEM = RecipeIngredientsActivity.class.getName() + ".widgetItem";
    private List<RecipeIngredient> ingredients;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        recipeId = getRecipeIdFromWidget();
        ingredients = getIngredients(recipeId);
        setupActionBar();
        sendIngredientsToFragment();
    }

    private int getRecipeIdFromWidget() {
        return getIntent().getIntExtra(WIDGET_ITEM, -1);
    }

    private List<RecipeIngredient> getIngredients(int recipeId) {
        if (recipeId != -1) {
            return getIngredientsByRecipeId(recipeId);
        }

        return Parcels.unwrap(getIntent().getParcelableExtra(RecipeIngredient.class.getName()));
    }

    private List<RecipeIngredient> getIngredientsByRecipeId(int recipeId) {
        RecipeDatabase database = new RecipeDatabase();
        Recipe recipe = database.get(recipeId + 1);

        return recipe.getIngredients();
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
