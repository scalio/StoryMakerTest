package info.guardianproject.mrapp.test;

import static com.google.common.base.Preconditions.checkNotNull;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import android.view.View;
import android.view.ViewParent;

public final class Matchers {

	/**
	 * Matches a view based on it's background drawable
	 * 
	 * @param bgResId
	 *            The resource id of the background drawable,
	 */
	public static Matcher<View> withBackgroundDrawable(final int bgResId) {
		checkNotNull(bgResId);
		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText("has background resource id " + bgResId);
			}

			@Override
			public boolean matchesSafely(View view) {
				if (view.getBackground() == null)
					return false;
				return view.getBackground().equals(
						view.getResources().getDrawable(bgResId));
			}
		};
	}

	/**
	 * Match a view given its id, and its parent id
	 * @param id
	 * @param parentId
	 */
	public static Matcher<View> withIdAndParentId(final int id, final int parentId) {
 		return new TypeSafeMatcher<View>() {
			@Override
			public void describeTo(Description description) {
				description.appendText(String.format("has id %d and parent id %d", id, parentId));
			}

			@Override
			public boolean matchesSafely(View view) {
				if (view.getId() != id)
					return false;
				
				View parent = (View) view.getParent();
				if (parent.getId() != parentId) {
					return false;
				}
				return true;
			}
		};
	}

}