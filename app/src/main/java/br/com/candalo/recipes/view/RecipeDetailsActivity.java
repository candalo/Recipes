package br.com.candalo.recipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnRecipeStepSelectedListener {

    private Recipe recipe;
    @BindView(R.id.player)
    SimpleExoPlayerView playerView;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        injectDependencies();
        recipe = getRecipe();
        setupActionBar();
        setupPlayerViewForTablets();
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
        if (playerView.getVisibility() == View.INVISIBLE) {
            playerView.setVisibility(View.VISIBLE);
        }

        VideoFragment videoFragment = (VideoFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_video);
        RecipeStepInstructionsFragment recipeStepInstructionsFragment = (RecipeStepInstructionsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_step_instructions);

        videoFragment.setStep(step);
        recipeStepInstructionsFragment.setStep(step);
    }
}
