package info.guardianproject.mrapp.test.actions;

import org.hamcrest.Matcher;

import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.isAssignableFrom;

import android.view.View;
import com.google.android.apps.common.testing.ui.espresso.UiController;
import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import com.slidingmenu.lib.SlidingMenu;

public class OpenSlidingMenuAction implements ViewAction{

	@Override
	public Matcher<View> getConstraints() {
		return isAssignableFrom(SlidingMenu.class);
	}

	@Override
	public String getDescription() {
		return "Open SlidingMenu";
	}

	@Override
	public void perform(UiController uiController, View view) {
		((SlidingMenu)view).showMenu();
	}

}
