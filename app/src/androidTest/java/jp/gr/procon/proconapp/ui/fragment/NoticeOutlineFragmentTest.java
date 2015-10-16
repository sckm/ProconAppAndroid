package jp.gr.procon.proconapp.ui.fragment;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.ui.activity.MainActivity;
import jp.gr.procon.proconapp.ui.activity.SplashActivity;
import jp.gr.procon.proconapp.util.AppSharedPreference;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class NoticeOutlineFragmentTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {

        Context context = InstrumentationRegistry.getTargetContext();
        AppSharedPreference.putString(context, AppSharedPreference.PREFERENCE_USER_TOKEN, "user_token");
        mActivityTestRule.launchActivity(MainActivity.createIntent(context));
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnViewCreated() throws Exception {
    }
}