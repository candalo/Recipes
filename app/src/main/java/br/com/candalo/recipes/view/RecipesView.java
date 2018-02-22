package br.com.candalo.recipes.view;


import java.util.List;

import br.com.candalo.recipes.domain.Recipe;

public interface RecipesView {
    void showLoading();
    void hideLoading();
    void showErrorMessage(String errorMessage);
    void showRecipes(List<Recipe> recipes);
}
