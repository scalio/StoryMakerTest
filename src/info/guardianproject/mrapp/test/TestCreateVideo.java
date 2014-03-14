
package info.guardianproject.mrapp.test;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import android.test.ActivityInstrumentationTestCase2;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.slidingmenu.lib.SlidingMenu;

import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withClassName;
import static org.hamcrest.Matchers.containsString;
import info.guardianproject.mrapp.HomeActivity;
import info.guardianproject.mrapp.R;
import info.guardianproject.mrapp.test.actions.OpenSlidingMenuAction;

/**
 * Test creating a quick video recording via the SlidingMenu's
 * btnDrawerQuickCaptureVideo Button
 * 
 * @author davidbrodsky
 */
public class TestCreateVideo extends ActivityInstrumentationTestCase2<HomeActivity> {

    public TestCreateVideo() {
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
        // Reveal SlidingMenu and click the video capture button
        onView(isAssignableFrom(SlidingMenu.class)).perform(new OpenSlidingMenuAction());
        onView(withId(R.id.btnDrawerQuickCaptureVideo)).perform(click());

        // The OverlayCameraActivity appears. Click the overlay ImageView
        onView(withClassName(containsString("ImageView"))).perform(click());

        // The CAPTURE_VIDEO intent resolves. Unfortunately,
        // this ends the Espresso test and we can no longer test.

    }

}
