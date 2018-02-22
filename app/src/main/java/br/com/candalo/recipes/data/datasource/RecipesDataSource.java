package br.com.candalo.recipes.data.datasource;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

import br.com.candalo.recipes.base.data.DataSource;
import br.com.candalo.recipes.domain.Recipe;

public class RecipesDataSource implements DataSource<RecipesDataSource.ResultListener> {

    static final String RECIPES_BROADCAST_RECEIVER =
            RecipesDataSource.class.getName() + ".RecipesBroadcastManager";

    private Context context;
    private ResultListener listener;

    @Inject
    public RecipesDataSource(Context context) {
        this.context = context;
    }

    @Override
    public void execute(ResultListener listener) {
        this.listener = listener;

        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
        localBroadcastManager.registerReceiver(receiver, new IntentFilter(RECIPES_BROADCAST_RECEIVER));

        Intent intent = new Intent(context, GetRecipesFromNetworkService.class);
        context.startService(intent);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LocalBroadcastManager.getInstance(context).unregisterReceiver(this);

            if (intent != null) {
                List<Recipe> recipes = Parcels.unwrap(intent.getParcelableExtra(Recipe.class.getName()));

                if (recipes != null) {
                    passDataToListener(recipes);
                    return;
                }

                passErrorToListener(intent);
            }
        }

        private void passDataToListener(List<Recipe> recipes) {
            listener.onResult(recipes);
        }

        private void passErrorToListener(Intent intent) {
            Bundle bundle = intent.getParcelableExtra(Exception.class.getName());
            Throwable error = (Throwable) bundle.getSerializable(Exception.class.getName());
            if (error != null) {
                listener.onError(error);
            }
        }
    };

    public interface ResultListener {
        void onResult(List<Recipe> recipes);
        void onError(Throwable e);
    }
}
