package br.com.candalo.recipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        injectDependencies();
        recipe = getRecipe();
        setupActionBar();
        sendRecipeToFragment();
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
    }

    private Recipe getRecipe() {
        return Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getName()));
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(recipe.getName());
        }
    }

    private void sendRecipeToFragment() {
        RecipeDetailsFragment fragment =
                (RecipeDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.setRecipe(recipe);
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
