package jp.gr.procon.proconapp;

import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import jp.gr.procon.proconapp.ui.activity.MainActivity;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void testCreateIntent() throws Exception {
        Intent intent = MainActivity.createIntent(RuntimeEnvironment.application);
        assertThat(intent, is(notNullValue()));
        assertThat(intent.getExtras().getBoolean("arg_from_notification"), is(false));
    }

    @Test
    public void testCreateIntent1() throws Exception {
        Intent intent = MainActivity.createIntent(RuntimeEnvironment.application, true);
        assertThat(intent, is(notNullValue()));
        assertThat(intent.getExtras().getBoolean("arg_from_notification"), is(true));
    }
}
