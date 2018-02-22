package br.com.candalo.recipes.view;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.parceler.Parcels;

import java.util.Collections;

import br.com.candalo.recipes.R;
import br.com.candalo.recipes.domain.Recipe;
import br.com.candalo.recipes.domain.RecipeIngredient;
import br.com.candalo.recipes.domain.RecipeStep;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipeDetailsActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailsActivity> mActivityRule = new ActivityTestRule<RecipeDetailsActivity>(RecipeDetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            RecipeIngredient ingredient = new RecipeIngredient(1.5F, "Test", "Test");
            RecipeStep step = new RecipeStep("Test", "Test", "Test", "Test");
            Recipe recipe = new Recipe(1, "Test", 10, Collections.singletonList(ingredient), Collections.singletonList(step));

            Context targetContext = InstrumentationRegistry.getInstrumentation()
                    .getTargetContext();
            Intent result = new Intent(targetContext, RecipeDetailsActivity.class);
            result.putExtra(Recipe.class.getName(), Parcels.wrap(recipe));
            return result;
        }
    };

    @Test
    public void testIngredientsSelection() {
        onView(withId(R.id.rv_recipe_details)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_details)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testStepSelection() {
        onView(withId(R.id.rv_recipe_details)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipe_details)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
    }
}