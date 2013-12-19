package info.guardianproject.mrapp.test;

import android.test.ActivityInstrumentationTestCase2;
import com.google.android.apps.common.testing.ui.espresso.Espresso;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;


import info.guardianproject.mrapp.HomeActivity;
import info.guardianproject.mrapp.R;

public class TestSimpleVideo extends ActivityInstrumentationTestCase2<HomeActivity> {

	public TestSimpleVideo() {
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
		//Espresso.onView(withId(R.id.newButton)).perform(click());
	}

}
