package info.guardianproject.mrapp.test.actions;

import org.hamcrest.Matcher;

import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.swipeLeft;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.swipeRight;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.UiController;
import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import com.slidingmenu.lib.SlidingMenu;

public class SwipeThroughViewPagerAction implements ViewAction{
	
	public static enum DIRECTION { LEFT, RIGHT };
	
	private DIRECTION mDirection;
	
	/**
	 * Swipe through a ViewPager towards the 
	 * indicated direction. e.g LEFT will
	 * mimic swipes going from left to right screen edge.
	 * @param direction direction to transit through ViewPager
	 */
	public SwipeThroughViewPagerAction(DIRECTION direction){
		super();
		mDirection = direction;
	}

	@Override
	public Matcher<View> getConstraints() {
		return isAssignableFrom(ViewPager.class);
	}

	@Override
	public String getDescription() {
		return "Page through ViewPager";
	}

	@Override
	public void perform(UiController uiController, View view) {
		ViewPager pager = (ViewPager) view;
		int pagerLength = pager.getAdapter().getCount();
		for(int x = 1; x < pagerLength; x++){
			switch(mDirection){
				case LEFT:
					pager.setCurrentItem(pagerLength - (x+1), true);
					uiController.loopMainThreadForAtLeast(200);
					break;
				case RIGHT:
					pager.setCurrentItem(x, true);
					uiController.loopMainThreadForAtLeast(200);
					break;
			}
		}
	}

}
