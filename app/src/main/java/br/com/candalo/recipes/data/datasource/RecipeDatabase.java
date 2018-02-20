package br.com.candalo.recipes.data.datasource;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import br.com.candalo.recipes.base.data.Database;
import br.com.candalo.recipes.domain.Recipe;

public class RecipeDatabase implements Database<List<Recipe>> {

    @Override
    public void save(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            recipe.save();
        }
    }

    @Override
    public List<Recipe> get() {
        return SQLite.select()
                .from(Recipe.class)
                .queryList();
    }
}
