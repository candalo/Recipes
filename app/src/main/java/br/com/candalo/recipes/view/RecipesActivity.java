package br.com.candalo.recipes.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.data.di.DaggerRecipesComponent;
import br.com.candalo.recipes.data.di.RecipesModule;
import br.com.candalo.recipes.domain.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesView {

    @Inject
    RecipesPresenter presenter;
    @BindView(R.id.rv_recipes)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.pb_recipes)
    ProgressBar recipesProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        injectDependencies();
        presenter.attachTo(this);
        presenter.getRecipes();
    }

    private void injectDependencies() {
        ButterKnife.bind(this);

        DaggerRecipesComponent
                .builder()
                .recipesModule(new RecipesModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void showLoading() {
        recipesProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        recipesProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showErrorMessage(String errorMessage) {

    }

    @Override
    public void showRecipes(List<Recipe> recipes) {
        RecipesAdapter adapter = new RecipesAdapter(this, recipes);
        recipesRecyclerView.setHasFixedSize(true);
        recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipesRecyclerView.setAdapter(adapter);
    }
}
