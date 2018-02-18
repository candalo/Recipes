package br.com.candalo.recipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeStep;

public class RecipeStepActivity extends AppCompatActivity {

    private RecipeStep step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        step = getStep();
        setupActionBar();
        sendStepToFragment();
    }

    private RecipeStep getStep() {
        return Parcels.unwrap(getIntent().getParcelableExtra(RecipeStep.class.getName()));
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(step.getShortDescription());
        }
    }

    private void sendStepToFragment() {
        RecipeStepFragment fragment =
                (RecipeStepFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_step);
        fragment.setStep(step);
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
