package br.com.candalo.recipes.data.datasource;


import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.parceler.Parcels;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import br.com.candalo.recipes.domain.Recipe;

import static br.com.candalo.recipes.data.datasource.RecipesDataSource.RECIPES_BROADCAST_RECEIVER;

public class GetRecipesFromNetworkService extends IntentService {

    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public GetRecipesFromNetworkService() {
        super(GetRecipesFromNetworkService.class.getName());
    }

    public GetRecipesFromNetworkService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Intent localBroadcastIntent = new Intent(RECIPES_BROADCAST_RECEIVER);

        try {
            HttpURLConnection urlConnection = getConnection();
            InputStream inputStream = getRequestInputStream(urlConnection);
            localBroadcastIntent.putExtra(Recipe.class.getName(), Parcels.wrap(getJsonData(inputStream)));
            urlConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            Bundle extras = new Bundle();
            extras.putSerializable(Exception.class.getName(), e);
            localBroadcastIntent.putExtra(Exception.class.getName(), extras);
        } finally {
            LocalBroadcastManager.getInstance(this).sendBroadcast(localBroadcastIntent);
        }
    }

    private HttpURLConnection getConnection() throws IOException {
        URL url = new URL(BASE_URL);
        return (HttpURLConnection) url.openConnection();
    }

    @NonNull
    private InputStream getRequestInputStream(HttpURLConnection connection) throws IOException {
        return new BufferedInputStream(connection.getInputStream());
    }

    private List<Recipe> getJsonData(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while((line = reader.readLine()) != null) {
            result.append(line);
        }

        Type listType = new TypeToken<List<Recipe>>() {}.getType();
        Gson gson = new Gson();

        return gson.fromJson(result.toString(), listType);
    }
}
