package br.com.candalo.recipes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeStepActivity extends AppCompatActivity {

    private RecipeStep step;
    @BindView(R.id.fl_video)
    FrameLayout videoFrameLayout;
    @BindView(R.id.iv_play)
    ImageView playImageView;
    @BindView(R.id.iv_error)
    ImageView errorImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);
        injectDependencies();
        step = getStep();
        setupVideoVisibility();
        setupActionBar();
        sendRecipeStepToRecipeStepInstructionsFragment();
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
    }

    private RecipeStep getStep() {
        return Parcels.unwrap(getIntent().getParcelableExtra(RecipeStep.class.getName()));
    }

    private void setupVideoVisibility() {
        if (step.getVideoURL().isEmpty()) {
            playImageView.setVisibility(View.INVISIBLE);
            errorImageView.setVisibility(View.VISIBLE);
            videoFrameLayout.setClickable(false);
        }
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(step.getShortDescription());
        }
    }

    private void sendRecipeStepToRecipeStepInstructionsFragment() {
        RecipeStepInstructionsFragment fragment = (RecipeStepInstructionsFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_step_instructions);
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

    @OnClick(R.id.fl_video)
    void onClickVideo() {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra(RecipeStep.class.getName(), Parcels.wrap(step));
        startActivity(intent);
    }
}
