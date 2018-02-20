package br.com.candalo.recipes.widget;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.view.RecipeIngredientsActivity;

public class WidgetProvider extends AppWidgetProvider {

    private static final String ITEM_CLICK_ACTION_ID = "br.com.candalo.recipes.widget.list.item.click";
    static final String EXTRA_ITEM = WidgetProvider.class.getName() + ".extraItem";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        ComponentName name = new ComponentName(context, WidgetProvider.class);
        int[] appWidgetIds = AppWidgetManager.getInstance(context).getAppWidgetIds(name);
        if (appWidgetIds == null || appWidgetIds.length == 0) {
            return;
        }

        if (intent.getAction() != null && intent.getAction().equals(ITEM_CLICK_ACTION_ID)) {
            int simpleDecidingFactor =  intent.getIntExtra(EXTRA_ITEM, 0);

            Intent recipeIngredientsIntent = new Intent(context, RecipeIngredientsActivity.class);
            recipeIngredientsIntent.putExtra(RecipeIngredientsActivity.WIDGET_ITEM, simpleDecidingFactor);
            context.startActivity(recipeIngredientsIntent);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews remoteView = updateWidgetListView(context, appWidgetId);

            Intent listItemClickIntent = new Intent(context, WidgetProvider.class);
            listItemClickIntent.setAction(ITEM_CLICK_ACTION_ID);
            listItemClickIntent.setData(Uri.parse(listItemClickIntent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context, 0, listItemClickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteView.setPendingIntentTemplate(R.id.lv_recipes, clickPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteView);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent serviceIntent = new Intent(context, WidgetService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(widgetId, R.id.lv_recipes, serviceIntent);
        remoteViews.setEmptyView(R.id.lv_recipes, R.id.tv_empty);

        return remoteViews;
    }
}

