
package info.guardianproject.mrapp.test;

import android.app.Instrumentation.ActivityMonitor;
import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.slidingmenu.lib.SlidingMenu;
import com.squareup.spoon.Spoon;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.closeSoftKeyboard;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.pressBack;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.swipeLeft;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.swipeRight;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withClassName;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import info.guardianproject.mrapp.HomeActivity;
import info.guardianproject.mrapp.ProjectsActivity;
import info.guardianproject.mrapp.R;
import info.guardianproject.mrapp.SceneEditorActivity;
import info.guardianproject.mrapp.StoryNewActivity;
import info.guardianproject.mrapp.StoryTemplateActivity;
import info.guardianproject.mrapp.StoryTemplateChooserActivity;
import info.guardianproject.mrapp.test.actions.OpenSlidingMenuAction;
import info.guardianproject.mrapp.test.actions.SwipeThroughViewPagerAction;
import info.guardianproject.mrapp.test.actions.SwipeThroughViewPagerAction.DIRECTION;

/**
 * Functional test of Creating Stories Exercises MainActivity, ProjectsActivity,
 * StoryNewActivity, SceneEditorActivity, StoryTemplateChooserActivity,
 * StoryTemplateActivity
 * 
 * @author davidbrodsky
 */
public class TestCreateStory extends ActivityInstrumentationTestCase2<HomeActivity> {
    private static final String TAG = "TestCreateStory";

    public enum MEDIUM {
        VIDEO, PHOTO, AUDIO, MULTI_PHOTO
    };

    public enum STORY_TYPE {
        SIMPLE, TEMPLATE
    };

    public enum STORY_TEMPLATES {
        BASIC, EXPERT
    }

    public static final String TEST_STORY_TITLE_PREFIX = "Gerald Ford: ";
    public static final int ACTIVITY_LAUNCH_TIMEOUT_MS = 1000;

    public TestCreateStory() {
        super(HomeActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        getActivity();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void test() {

        STORY_TYPE[] types = STORY_TYPE.values();
        MEDIUM[] mediums = MEDIUM.values();

        for (int typeIndex = 0; typeIndex < types.length; typeIndex++) {
            for (int mediumIndex = 0; mediumIndex < mediums.length; mediumIndex++) {
                MEDIUM testMedium = mediums[mediumIndex];
                STORY_TYPE testType = types[typeIndex];

                Spoon.screenshot(getActivity(),
                        describeTestState("initial_state", testMedium, testType));

                // Reveal SlidingMenu and click the Stories button
                onView(isAssignableFrom(SlidingMenu.class)).perform(new OpenSlidingMenuAction());

                // Setup monitor in anticipation of ProjectsActivity launch
                ActivityMonitor projectsActivityMonitor =
                        getInstrumentation().addMonitor(ProjectsActivity.class.getName(),
                                null, false);

                // Clicking the "Stories" SlidingMenu button should launch
                // ProjectsActivity
                onView(withId(R.id.btnDrawerProjects)).perform(click());

                // ProjectsActivity appears
                ProjectsActivity projectsActivity = (ProjectsActivity)
                        projectsActivityMonitor
                                .waitForActivityWithTimeout(ACTIVITY_LAUNCH_TIMEOUT_MS);
                assertNotNull("ProjectsActivity did not launch as expected", projectsActivity);
                assertEquals("Monitor for ProjectsActivity has not been called",
                        1, projectsActivityMonitor.getHits());
                getInstrumentation().removeMonitor(projectsActivityMonitor);
                Spoon.screenshot(projectsActivity,
                        describeTestState("projects_activity", testMedium, testType));

                // Setup monitor in anticipation of StoryNewActivity
                ActivityMonitor storyNewActivityMonitor =
                        getInstrumentation().addMonitor(StoryNewActivity.class.getName(),
                                null, false);

                // Click the "New" ActionBar button
                onView(withId(R.id.menu_new_project)).perform(click());

                // StoryNewActivity appears
                StoryNewActivity storyNewActivity = (StoryNewActivity)
                        storyNewActivityMonitor
                                .waitForActivityWithTimeout(ACTIVITY_LAUNCH_TIMEOUT_MS);
                assertNotNull("StoryNewActivity did not launch as expected. state: " + testMedium
                        + " " + testType, storyNewActivity);
                assertEquals("Monitor for StoryNewActivity has not been called",
                        1, storyNewActivityMonitor.getHits());
                getInstrumentation().removeMonitor(storyNewActivityMonitor);
                Spoon.screenshot(storyNewActivity,
                        describeTestState("story_new_activity", testMedium, testType));

                // Enter a title
                String testTitle = createStoryTitle(TEST_STORY_TITLE_PREFIX, testMedium, testType);
                onView(withId(R.id.editTextStoryName)).perform(typeText(testTitle));
                onView(withId(R.id.editTextStoryName)).check(matches(withText(testTitle)));
                closeSoftKeyboard();
                // Choose a Medium
                onView(Matchers.withIdAndParentId(getRadioButtonIdForMedium(testMedium),
                        R.id.radioGroupStoryType)).perform(click());
                // Choose Simple / Scene Template
                onView(Matchers.withIdAndParentId(getRadioButtonIdForStoryType(testType),
                        R.id.radioGroupStoryLevel)).perform(click());

                // StoryNewActivity form input complete
                Spoon.screenshot(storyNewActivity,
                        describeTestState("story_new_activity_complete", testMedium, testType));

                if (testType == STORY_TYPE.SIMPLE) {
                    // Setup monitor in anticipation of StoryNewActivity
                    ActivityMonitor sceneEditorActivityMonitor =
                            getInstrumentation().addMonitor(SceneEditorActivity.class.getName(),
                                    null, false);

                    // Click Start My Story
                    onView(withId(R.id.buttonStartStory)).perform(click());

                    // SceneEditorActivity appears
                    // TODO: Assert ActionBar "Make" tab is selected
                    SceneEditorActivity sceneEditorActivity = (SceneEditorActivity)
                            sceneEditorActivityMonitor
                                    .waitForActivityWithTimeout(ACTIVITY_LAUNCH_TIMEOUT_MS);
                    assertNotNull("SceneEditorActivity did not launch as expected. state: "
                            + testMedium + " " + testType,
                            storyNewActivity);
                    assertEquals("Monitor for SceneEditorActivity has not been called",
                            1, storyNewActivityMonitor.getHits());
                    getInstrumentation().removeMonitor(storyNewActivityMonitor);
                    Spoon.screenshot(sceneEditorActivity,
                            describeTestState("scene_editor_activity", testMedium, testType));

                    // Swipe through scene ViewPager
                    onView(isAssignableFrom(ViewPager.class)).perform(
                            new SwipeThroughViewPagerAction(DIRECTION.RIGHT));

                    onView(withText(R.string.tab_order)).perform(click());
                    onView(withText(R.string.tab_publish)).perform(click());

                } else if (testType == STORY_TYPE.TEMPLATE) {

                    // Setup monitor in anticipation of
                    // StoryTemplateChooserActivity
                    ActivityMonitor storyTemplateChooserActivityMonitor =
                            getInstrumentation().addMonitor(
                                    StoryTemplateChooserActivity.class.getName(),
                                    null, false);

                    // Click Start My Story
                    onView(withId(R.id.buttonStartStory)).perform(click());

                    // StoryTemplateChooserActivity appears
                    StoryTemplateChooserActivity storyTemplateChooserActivity = (StoryTemplateChooserActivity)
                            storyTemplateChooserActivityMonitor
                                    .waitForActivityWithTimeout(ACTIVITY_LAUNCH_TIMEOUT_MS);
                    assertNotNull(
                            "StoryTemplateChooserActivity did not launch as expected. state: "
                                    + testMedium + " " + testType,
                            storyTemplateChooserActivity);
                    assertEquals("Monitor for StoryTemplateChooserActivity has not been called",
                            1, storyNewActivityMonitor.getHits());
                    getInstrumentation().removeMonitor(storyTemplateChooserActivityMonitor);
                    Spoon.screenshot(
                            storyTemplateChooserActivity,
                            describeTestState("story_template_chooser_activity", testMedium,
                                    testType));

                    // Swipe through Story Type ViewPager
                    // TODO: Loop through every story type
                    onView(isAssignableFrom(ViewPager.class)).perform(
                            new SwipeThroughViewPagerAction(DIRECTION.RIGHT));

                    // Setup monitor in anticipation of
                    // StoryTemplateActivity
                    ActivityMonitor storyTemplateActivityMonitor =
                            getInstrumentation().addMonitor(StoryTemplateActivity.class.getName(),
                                    null, false);

                    // Click the ">>" ActionBar button
                    onView(withId(R.id.itemForward)).perform(click());

                    // StoryTemplateActivity appears
                    StoryTemplateActivity storyTemplateActivity = (StoryTemplateActivity)
                            storyTemplateActivityMonitor
                                    .waitForActivityWithTimeout(ACTIVITY_LAUNCH_TIMEOUT_MS);
                    assertNotNull("StoryTemplateActivity did not launch as expected. state: "
                            + testMedium + " " + testType,
                            storyTemplateActivity);
                    assertEquals("Monitor for StoryTemplateActivity has not been called", 1,
                            storyTemplateActivityMonitor.getHits());
                    getInstrumentation().removeMonitor(storyTemplateActivityMonitor);
                    Spoon.screenshot(storyTemplateActivity,
                            describeTestState("story_template_activity", testMedium, testType));

                    // TODO: Assert "Make" ActionBar tab selected
                    // TODO: Dynamically traverse StoryTemplateActivity's Fragment contained ListView
                    // e.g Overview, Impact etc.

                    // Swipe through the MAKE - REVIEW - Publish ViewPager
                    onView(isAssignableFrom(ViewPager.class)).perform(
                            new SwipeThroughViewPagerAction(DIRECTION.RIGHT));

                }

                // Return to ProjectsActivity
                pressBack();

                // Return to MainActivity
                pressBack();

            }
        }

    }

    /**
     * Generate a title for Spoon's screenshot function
     * 
     * @return
     */
    private static String describeTestState(String state, MEDIUM medium, STORY_TYPE storyType) {
        return state + "_" + medium.name() + "_" + storyType.name();
    }

    /**
     * Generate a test Story title given the test state
     * 
     * @param prefix
     * @param medium
     * @param storyType
     * @return
     */
    private static String createStoryTitle(String prefix, MEDIUM medium, STORY_TYPE storyType) {
        return prefix + String.format("A %s %s tale.", storyType.toString(), medium.toString());
    }

    /**
     * Given a Story's MEDIUM, return the id of the corresponding
     * "Choose a Medium" Radio Button in StoryNewActivity
     */
    private static int getRadioButtonIdForMedium(MEDIUM medium) {
        switch (medium) {
            case VIDEO:
                return R.id.radioStoryType0;
            case PHOTO:
                return R.id.radioStoryType1;
            case AUDIO:
                return R.id.radioStoryType2;
            case MULTI_PHOTO:
                return R.id.radioStoryType3;
        }
        throw new IllegalArgumentException("Unsupported medium provided");
    }

    /**
     * Given a Story's STORY_TYPE, return the id of the corresponding
     * "Simple Story" / "Scene Template" Radio Button in StoryNewActivity
     * 
     * @param type
     * @return
     */
    private static int getRadioButtonIdForStoryType(STORY_TYPE type) {
        switch (type) {
            case SIMPLE:
                return R.id.radioStoryType0;
            case TEMPLATE:
                return R.id.radioStoryType1;
        }
        throw new IllegalArgumentException("Unsupported story type provided");
    }

}
