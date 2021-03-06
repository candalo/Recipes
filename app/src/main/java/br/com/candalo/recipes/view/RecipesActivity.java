package br.com.candalo.recipes.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.data.di.DaggerRecipesComponent;
import br.com.candalo.recipes.data.di.RecipesModule;
import br.com.candalo.recipes.domain.Recipe;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipesActivity extends AppCompatActivity implements RecipesView, RecipesAdapter.RecipeItemListener {

    @Inject
    RecipesPresenter presenter;
    @BindView(R.id.rv_recipes)
    RecyclerView recipesRecyclerView;
    @BindView(R.id.pb_recipes)
    ProgressBar recipesProgressBar;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;

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
        if (isTablet) {
            recipesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recipesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipeSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(Recipe.class.getName(), Parcels.wrap(recipe));
        startActivity(intent);
    }
}
