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

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class RecipeDetailsFragment extends Fragment {

    private Unbinder unbinder;
    private Recipe recipe;
    @BindView(R.id.rv_recipe_details)
    RecyclerView recipeDetailsRecyclerView;

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
        RecipeDetailsAdapter adapter = new RecipeDetailsAdapter(getContext(), recipe);
        recipeDetailsRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        recipeDetailsRecyclerView.setHasFixedSize(true);
        recipeDetailsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeDetailsRecyclerView.setAdapter(adapter);
    }
}
