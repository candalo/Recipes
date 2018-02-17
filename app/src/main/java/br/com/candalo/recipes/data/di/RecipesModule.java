package br.com.candalo.recipes.data.di;

import android.content.Context;

import br.com.candalo.recipes.base.data.DataSource;
import br.com.candalo.recipes.data.datasource.RecipesDataSource;
import br.com.candalo.recipes.view.RecipesPresenter;
import dagger.Module;
import dagger.Provides;

@Module
public class RecipesModule {

    private Context context;

    public RecipesModule(Context context) {
        this.context = context;
    }

    @Provides
    DataSource<RecipesDataSource.ResultListener> provideRecipeDataSource() {
        return new RecipesDataSource(context);
    }

    @Provides
    RecipesPresenter provideRecipesPresenter(DataSource<RecipesDataSource.ResultListener> recipesDataSource) {
        return new RecipesPresenter(recipesDataSource);
    }
}
