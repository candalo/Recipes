package br.com.candalo.recipes.domain;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Recipe {

    private int id;
    private String name;
    private int servings;
    private List<RecipeIngredient> ingredients;
    private List<RecipeStep> steps;

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

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public List<RecipeStep> getSteps() {
        return steps;
    }
}
