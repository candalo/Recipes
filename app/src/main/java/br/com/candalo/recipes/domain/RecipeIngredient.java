package br.com.candalo.recipes.domain;


import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;
import org.parceler.Transient;

import br.com.candalo.recipes.base.data.AppDatabase;

@Table(database = AppDatabase.class)
@Parcel(Parcel.Serialization.BEAN)
public class RecipeIngredient extends BaseModel {

    @Column
    float quantity;
    @Column
    String measure;
    @Column
    @PrimaryKey
    @SerializedName("ingredient")
    String name;
    @Transient
    @ForeignKey(stubbedRelationship = true)
    Recipe recipe;

    public RecipeIngredient() {
    }

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

    @Transient
    public Recipe getRecipe() {
        return recipe;
    }

    @Transient
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
