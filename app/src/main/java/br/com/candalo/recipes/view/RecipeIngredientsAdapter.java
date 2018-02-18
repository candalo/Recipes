package br.com.candalo.recipes.view;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.RecipeIngredient;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsViewHolder> {

    private List<RecipeIngredient> ingredients;

    public RecipeIngredientsAdapter(List<RecipeIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public RecipeIngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_recipe_ingredient_details, parent, false);

        return new RecipeIngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsViewHolder holder, int position) {
        RecipeIngredient ingredient = ingredients.get(position);
        holder.quantityTextView.setText(String.valueOf(ingredient.getQuantity()));
        holder.measureTextView.setText(ingredient.getMeasure());
        holder.nameTextView.setText(ingredient.getName());
    }

    @Override
    public int getItemCount() {
        return ingredients != null ? ingredients.size() : 0;
    }

    class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_quantity)
        TextView quantityTextView;
        @BindView(R.id.tv_measure)
        TextView measureTextView;
        @BindView(R.id.tv_ingredient_name)
        TextView nameTextView;

        RecipeIngredientsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
