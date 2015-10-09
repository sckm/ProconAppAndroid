package jp.gr.procon.proconapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import jp.gr.procon.proconapp.CustomBuildConfig;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class AppSharedPreferenceTest {
    private static final String FILE_NAME = "procon_pref";
    private static final String KEY = "pref_key";
    private static final String VALUE = "pref_value";

    private Context mContext;

    @Before
    public void setUp() throws Exception {
        mContext = RuntimeEnvironment.application;
    }

    @After
    public void tearDown() throws Exception {
        SharedPreferences pref = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(KEY, null).apply();
    }

    @Test
    public void testPutString() throws Exception {
        AppSharedPreference.putString(mContext, KEY, VALUE);
        SharedPreferences pref = getSharedPreferences();
        String value = pref.getString(KEY, null);
        assertThat(value, is(equalTo(VALUE)));
    }

    @Test
    public void testGetString() throws Exception {
        getSharedPreferences().edit().putString(KEY, VALUE).apply();
        String s = AppSharedPreference.getString(mContext, KEY);
        assertThat(s, is(VALUE));
    }

    @Test
    public void testAppSharedPreferenceString() {
        AppSharedPreference.putString(mContext, KEY, VALUE);
        String value = AppSharedPreference.getString(mContext, KEY);
        assertThat(value, is(equalTo(VALUE)));
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }
}