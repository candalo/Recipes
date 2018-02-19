package br.com.candalo.recipes.view;


import android.content.Context;
import android.content.Intent;
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

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeIngredient;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment implements RecipeDetailsAdapter.RecipeDetailsItemListener {

    private OnRecipeItemSelectedListener onRecipeItemSelectedListener;
    private Unbinder unbinder;
    private Recipe recipe;
    @BindView(R.id.rv_recipe_details)
    RecyclerView recipeDetailsRecyclerView;
    @BindBool(R.bool.is_tablet)
    boolean isTablet;

    public interface OnRecipeItemSelectedListener {
        void onRecipeStepSelected(RecipeStep step);
        void onRecipeIngredientsSelected(List<RecipeIngredient> ingredients);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            onRecipeItemSelectedListener = (OnRecipeItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        injectDependencies(view);

        return view;
    }

    private void injectDependencies(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecipeDetailsAdapter adapter = new RecipeDetailsAdapter(getContext(), this, recipe);
        recipeDetailsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recipeDetailsRecyclerView.setHasFixedSize(true);
        recipeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeDetailsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickIngredientsItem(List<RecipeIngredient> ingredients) {
        if (isTablet) {
            onRecipeItemSelectedListener.onRecipeIngredientsSelected(ingredients);
        } else {
            Intent intent = new Intent(getContext(), RecipeIngredientsActivity.class);
            intent.putExtra(RecipeIngredient.class.getName(), Parcels.wrap(ingredients));
            startActivity(intent);
        }
    }

    @Override
    public void onClickStepItem(RecipeStep step) {
        if (isTablet) {
            onRecipeItemSelectedListener.onRecipeStepSelected(step);
        } else {
            Intent intent = new Intent(getContext(), RecipeStepActivity.class);
            intent.putExtra(RecipeStep.class.getName(), Parcels.wrap(step));
            startActivity(intent);
        }
    }
}
