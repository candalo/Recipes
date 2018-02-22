package br.com.candalo.recipes.widget;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.base.data.Database;
import br.com.candalo.recipes.data.datasource.RecipeDatabase;
import br.com.candalo.recipes.domain.Recipe;

import static br.com.candalo.recipes.widget.WidgetProvider.EXTRA_ITEM;

public class WidgetRecipesProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private Database<Recipe> database;
    private List<Recipe> recipes;

    public WidgetRecipesProvider(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        database = new RecipeDatabase();
        recipes = database.getList();
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
        database = null;
        recipes = null;
    }

    @Override
    public int getCount() {
        return recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Recipe recipe = recipes.get(position);

        RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_layout_item);
        view.setTextViewText(R.id.tv_recipe_name, recipe.getName());

        Bundle extras = new Bundle();
        extras.putInt(EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(extras);
        view.setOnClickFillInIntent(R.id.widget_item, intent);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
