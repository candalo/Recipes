package br.com.candalo.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import org.parceler.Parcels;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeStep;

public class VideoActivity extends AppCompatActivity {

    private RecipeStep step;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        setupFullscreen();
        step = getStep();
        sendStepToFragment();
    }

    private void setupFullscreen() {
        this.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private RecipeStep getStep() {
        return Parcels.unwrap(getIntent().getParcelableExtra(RecipeStep.class.getName()));
    }

    private void sendStepToFragment() {
        VideoFragment fragment = (VideoFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_video);
        fragment.setStep(step);
    }
}
