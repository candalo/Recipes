package br.com.candalo.recipes.view;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import br.com.candalo.recipes.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipesActivityTest {

    @Rule
    public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<RecipesActivity>(RecipesActivity.class);

    @Test
    public void testRecipeChoice() {
        onView(withId(R.id.rv_recipes)).check(matches(isDisplayed()));
        onView(withId(R.id.rv_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}