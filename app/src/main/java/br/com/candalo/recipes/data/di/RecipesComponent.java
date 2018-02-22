package br.com.candalo.recipes.data.di;

import br.com.candalo.recipes.view.RecipesActivity;
import dagger.Component;

@Component(modules = RecipesModule.class)
public interface RecipesComponent {
    void inject(RecipesActivity activity);
}
