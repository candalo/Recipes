package br.com.candalo.recipes.domain;


import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel(Parcel.Serialization.BEAN)
public class RecipeIngredient {

    private float quantity;
    private String measure;
    @SerializedName("ingredient")
    private String name;

    @ParcelConstructor
    public RecipeIngredient(float quantity, String measure, String name) {
        this.quantity = quantity;
        this.measure = measure;
        this.name = name;
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }
}
