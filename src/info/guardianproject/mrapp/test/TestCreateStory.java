package info.guardianproject.mrapp.test;

import android.support.v4.view.ViewPager;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.slidingmenu.lib.SlidingMenu;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.closeSoftKeyboard;
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
import info.guardianproject.mrapp.R;
import info.guardianproject.mrapp.test.actions.OpenSlidingMenuAction;
import info.guardianproject.mrapp.test.actions.SwipeThroughViewPagerAction;
import info.guardianproject.mrapp.test.actions.SwipeThroughViewPagerAction.DIRECTION;

/**
 * Test creating Stories and swiping
 * through all the resulting template pages
 * @author davidbrodsky
 *
 */
public class TestCreateStory extends ActivityInstrumentationTestCase2<HomeActivity> {
	private static final String TAG = "TestCreateStory";
	
	public enum MEDIUM { VIDEO, PHOTO, AUDIO, MULTI_PHOTO };
	public enum STORY_TYPE { SIMPLE, TEMPLATE };
	
	public static final String mTestStoryTitle = "Test Story";
	
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
	
	public void test(){
		
		// TODO: Loop over all MEDIUMs
		//       and both Story types
		
		MEDIUM testMedium = MEDIUM.VIDEO;
		STORY_TYPE testType = STORY_TYPE.SIMPLE;
		
		//"com.slidingmenu.lib.SlidingMenu"
		// Reveal SlidingMenu and click the Stories button
		onView(isAssignableFrom(SlidingMenu.class)).perform(new OpenSlidingMenuAction());
		onView(withId(R.id.btnDrawerProjects)).perform(click());
		
		// ProjectsActivity appears
		// Click the "New" ActionBar button
		onView(withId(R.id.menu_new_project)).perform(click());
		
		// StoryNewActivity appears
		// Enter a title
		onView(withId(R.id.editTextStoryName)).perform(typeText(mTestStoryTitle));
        onView(withId(R.id.editTextStoryName)).check(matches(withText(mTestStoryTitle)));
        closeSoftKeyboard();
        // Choose a Medium
        onView(Matchers.withIdAndParentId(getRadioButtonIdForMedium(testMedium), R.id.radioGroupStoryType)).perform(click());	// Video
		// Choose Simple / Scene Template
        onView(withText(R.string.new_story_simple_story)).perform(click());	// Simple
        // Click Start My Story
        onView(withId(R.id.buttonStartStory)).perform(click());
        
        if(testType == STORY_TYPE.SIMPLE){
            // When "Simple Story" was selected, SceneEditorActivity appears
        	// TODO: Assert ActionBar "Make" tab is selected
        	onView(isAssignableFrom(ViewPager.class)).perform(new SwipeThroughViewPagerAction(DIRECTION.RIGHT));
        	
        	onView(withText(R.string.tab_order)).perform(click());
        	onView(withText(R.string.tab_publish)).perform(click());

        }else{
            // Else StoryTemplateChooserActivity appears

        }
        
	}
	
	/**
	 * Given a Story's MEDIUM, return the id
	 * of the corresponding "Choose a Medium" Radio Button.
	 */
	public static int getRadioButtonIdForMedium(MEDIUM medium){
		switch(medium){
		case VIDEO:
			return R.id.radioStoryType0;
		case AUDIO:
			return R.id.radioStoryType1;
		case PHOTO:
			return R.id.radioStoryType2;
		case MULTI_PHOTO:
			return R.id.radioStoryType3;
		}
		throw new IllegalArgumentException("Unsupported medium provided");
	}

}
