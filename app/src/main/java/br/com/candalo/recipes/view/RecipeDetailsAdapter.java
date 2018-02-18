package br.com.candalo.recipes.view;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeIngredient;
import br.com.candalo.recipes.domain.RecipeStep;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_INGREDIENTS_VIEW_TYPE = 0;
    private static final int RECIPE_STEPS_VIEW_TYPE = 1;

    private Context context;
    private Recipe recipe;

    public RecipeDetailsAdapter(Context context, Recipe recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view;
        switch (viewType) {
            case RECIPE_INGREDIENTS_VIEW_TYPE:
                view = inflater.inflate(R.layout.item_recipe_ingredients, parent, false);
                return new RecipeIngredientsViewHolder(view);
            case RECIPE_STEPS_VIEW_TYPE:
                view = inflater.inflate(R.layout.item_recipe_step, parent, false);
                return new RecipeStepsViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case RECIPE_INGREDIENTS_VIEW_TYPE:
                RecipeIngredientsViewHolder recipeIngredientsViewHolder = (RecipeIngredientsViewHolder) holder;
                recipeIngredientsViewHolder.ingredients = recipe.getIngredients();
                break;
            case RECIPE_STEPS_VIEW_TYPE:
                String recipeStepText = context.getString(R.string.recipe_step_description, position);
                RecipeStepsViewHolder recipeStepsViewHolder = (RecipeStepsViewHolder) holder;
                recipeStepsViewHolder.recipeStepTextView.setText(recipeStepText);
                recipeStepsViewHolder.step = recipe.getSteps().get(position - 1);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return recipe != null ? recipe.getSteps().size() + 1 : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? RECIPE_INGREDIENTS_VIEW_TYPE : RECIPE_STEPS_VIEW_TYPE;
    }

    class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
        List<RecipeIngredient> ingredients;

        RecipeIngredientsViewHolder(View view) {
            super(view);
        }
    }

    class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
        RecipeStep step;
        @BindView(R.id.tv_recipe_step)
        TextView recipeStepTextView;

        RecipeStepsViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
