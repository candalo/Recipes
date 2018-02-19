package br.com.candalo.recipes.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeIngredient;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeIngredientsFragment extends Fragment {

    private Unbinder unbinder;
    private List<RecipeIngredient> ingredients;
    @BindView(R.id.rv_recipe_ingredients)
    RecyclerView recipeIngredientsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        injectDependencies(view);
        ingredients = getIngredients();
        setupRecyclerView();

        return view;
    }

    private void injectDependencies(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    private List<RecipeIngredient> getIngredients() {
        if (getArguments() != null) {
            return Parcels.unwrap(getArguments().getParcelable(RecipeIngredient.class.getName()));
        }

        return new ArrayList<>();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void setIngredients(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecipeIngredientsAdapter adapter = new RecipeIngredientsAdapter(ingredients);
        recipeIngredientsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        recipeIngredientsRecyclerView.setHasFixedSize(true);
        recipeIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeIngredientsRecyclerView.setAdapter(adapter);
    }

}
