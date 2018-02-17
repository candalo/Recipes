package br.com.candalo.recipes.domain;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel(Parcel.Serialization.BEAN)
public class Recipe {

    private int id;
    private String name;
    private int servings;

    @ParcelConstructor
    public Recipe(int id, String name, int servings) {
        this.id = id;
        this.name = name;
        this.servings = servings;
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
}
