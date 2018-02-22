package br.com.candalo.recipes.domain;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.list.FlowCursorList;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import br.com.candalo.recipes.base.data.AppDatabase;

@Table(database = AppDatabase.class)
@Parcel(Parcel.Serialization.BEAN)
public class Recipe extends BaseModel {

    @PrimaryKey
    int id;
    @Column
    String name;
    @Column
    int servings;
    List<RecipeIngredient> ingredients;
    List<RecipeStep> steps;

    public Recipe() {
    }

    @ParcelConstructor
    public Recipe(int id, String name, int servings, List<RecipeIngredient> ingredients, List<RecipeStep> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "ingredients")
    public List<RecipeIngredient> getIngredients() {
        if (ingredients == null || ingredients.isEmpty()) {
            FlowCursorList<RecipeIngredient> cursorList = SQLite.select()
                    .from(RecipeIngredient.class)
                    .where(RecipeIngredient_Table.recipe_id.eq(id))
                    .cursorList();

            ingredients = cursorList.getAll();
            cursorList.close();
        }

        return ingredients;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "steps")
    public List<RecipeStep> getSteps() {
        if (steps == null || steps.isEmpty()) {
            FlowCursorList<RecipeStep> cursorList = SQLite.select()
                    .from(RecipeStep.class)
                    .where(RecipeStep_Table.recipe_id.eq(id))
                    .cursorList();

            steps = cursorList.getAll();
            cursorList.close();
        }

        return steps;
    }

    @Override
    public boolean save() {
        saveIngredients();
        saveSteps();

        return super.save();
    }

    private void saveIngredients() {
        if (ingredients != null) {
            for (RecipeIngredient ingredient : ingredients) {
                ingredient.setRecipe(this);
                ingredient.save();
            }
        }
    }

    private void saveSteps() {
        if (steps != null) {
            for (RecipeStep step : steps) {
                step.setRecipe(this);
                step.save();
            }
        }
    }
}
