package br.com.candalo.recipes.domain;

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
public class RecipeStep extends BaseModel {

    @PrimaryKey
    @Column
    String shortDescription;
    @Column
    String description;
    @Column
    String videoURL;
    @Column
    String thumbnailURL;
    @Transient
    @ForeignKey()
    Recipe recipe;

    public RecipeStep() {
    }

    @ParcelConstructor
    public RecipeStep(String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
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
