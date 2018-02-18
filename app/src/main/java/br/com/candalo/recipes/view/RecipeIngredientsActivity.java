package br.com.candalo.recipes.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeIngredient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsActivity extends AppCompatActivity {

    private List<RecipeIngredient> ingredients;
    @BindView(R.id.rv_recipe_ingredients)
    RecyclerView recipeIngredientsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_ingredients);
        injectDependencies();
        ingredients = getIngredients();
        setupActionBar();
        setupRecyclerView();
    }

    private void injectDependencies() {
        ButterKnife.bind(this);
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

    private void setupRecyclerView() {
        RecipeIngredientsAdapter adapter = new RecipeIngredientsAdapter(ingredients);
        recipeIngredientsRecyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recipeIngredientsRecyclerView.setHasFixedSize(true);
        recipeIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeIngredientsRecyclerView.setAdapter(adapter);
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
