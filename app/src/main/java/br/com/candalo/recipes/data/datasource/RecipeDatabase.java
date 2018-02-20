package br.com.candalo.recipes.data.datasource;


import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.List;

import br.com.candalo.recipes.base.data.Database;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.Recipe_Table;

public class RecipeDatabase implements Database<Recipe> {

    @Override
    public void saveAll(List<Recipe> recipes) {
        for (Recipe recipe : recipes) {
            recipe.save();
        }
    }

    @Override
    public List<Recipe> getList() {
        return SQLite.select()
                .from(Recipe.class)
                .queryList();
    }

    @Override
    public Recipe get(int id) {
        return SQLite.select()
                .from(Recipe.class)
                .where(Recipe_Table.id.eq(id))
                .querySingle();
    }
}
