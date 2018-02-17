package br.com.candalo.recipes.view;


import java.util.List;

import javax.inject.Inject;

import br.com.candalo.recipes.base.data.DataSource;
import br.com.candalo.recipes.base.view.Presenter;
import br.com.candalo.recipes.data.datasource.RecipesDataSource;
import br.com.candalo.recipes.domain.Recipe;

public class RecipesPresenter implements Presenter<RecipesView>, RecipesDataSource.ResultListener {

    private RecipesView view;
    private DataSource<RecipesDataSource.ResultListener> recipesDataSource;

    @Inject
    public RecipesPresenter(DataSource<RecipesDataSource.ResultListener> recipesDataSource) {
        this.recipesDataSource = recipesDataSource;
    }

    @Override
    public void attachTo(RecipesView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    void getRecipes() {
        view.showLoading();
        recipesDataSource.execute(this);
    }

    @Override
    public void onResult(List<Recipe> recipes) {
        view.hideLoading();
        view.showRecipes(recipes);
    }

    @Override
    public void onError(Throwable e) {
        view.hideLoading();
        // TODO: Show error message
    }
}
