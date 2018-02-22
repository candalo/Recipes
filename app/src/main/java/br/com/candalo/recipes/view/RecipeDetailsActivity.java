package br.com.candalo.recipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.parceler.Parcels;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeIngredient;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindBool;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnRecipeItemSelectedListener {

    private Recipe recipe;
    private SimpleExoPlayerView playerView;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        injectDependencies();
        setupFragmentsForTablet(savedInstanceState);
        playerView = getPlayerView();
        recipe = getRecipe();
        setupActionBar();
        setupPlayerViewForTablets();
        sendRecipeToFragment();
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
    }

    private void setupFragmentsForTablet(Bundle bundle) {
        if (isTablet) {
            if (bundle == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_recipe_ingredients, new RecipeIngredientsFragment())
                        .commit();
            }
        }
    }

    private Recipe getRecipe() {
        return Parcels.unwrap(getIntent().getParcelableExtra(Recipe.class.getName()));
    }

    private SimpleExoPlayerView getPlayerView() {
        if (isTablet) {
            View view =  LayoutInflater.from(this).inflate(R.layout.fragment_video, null);
            return view.findViewById(R.id.player);
        }
        return null;
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(recipe.getName());
        }
    }

    private void setupPlayerViewForTablets() {
        if (isTablet) {
            playerView.setVisibility(View.INVISIBLE);
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

    @Override
    public void onRecipeStepSelected(RecipeStep step) {
        if (isTablet) {
            if (playerView.getVisibility() == View.INVISIBLE) {
                playerView.setVisibility(View.VISIBLE);
            }

            removeRecipeIngredientsFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeStep.class.getName(), Parcels.wrap(step));

            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            RecipeStepInstructionsFragment recipeStepInstructionsFragment = new RecipeStepInstructionsFragment();
            recipeStepInstructionsFragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_video, videoFragment)
                    .replace(R.id.fragment_recipe_step_instructions, recipeStepInstructionsFragment)
                    .commit();
        }
    }

    @Override
    public void onRecipeIngredientsSelected(List<RecipeIngredient> ingredients) {
        if (isTablet) {
            removeRecipeVideoFragment();
            removeRecipeStepInstructionsFragment();

            Bundle bundle = new Bundle();
            bundle.putParcelable(RecipeIngredient.class.getName(), Parcels.wrap(ingredients));

            RecipeIngredientsFragment fragment = new RecipeIngredientsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_recipe_ingredients, fragment)
                    .commit();
        }
    }

    private void removeRecipeIngredientsFragment() {
        RecipeIngredientsFragment fragment = (RecipeIngredientsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_ingredients);

        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    private void removeRecipeVideoFragment() {
        VideoFragment fragment = (VideoFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_video);

        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    private void removeRecipeStepInstructionsFragment() {
        RecipeStepInstructionsFragment fragment = (RecipeStepInstructionsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_step_instructions);

        if (fragment != null && fragment.isAdded()) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }
}
