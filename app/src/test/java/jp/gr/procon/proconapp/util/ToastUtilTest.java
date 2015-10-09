package jp.gr.procon.proconapp.util;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowToast;

import jp.gr.procon.proconapp.CustomBuildConfig;
import jp.gr.procon.proconapp.R;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class ToastUtilTest {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testShowWithString() throws Exception {
        String toastString = "toast string";
        ToastUtil.show(RuntimeEnvironment.application, toastString);
        assertThat(toastString, is(ShadowToast.getTextOfLatestToast()));
    }

    @Test
    public void testShowWithResourceId() throws Exception {
        String toastString = RuntimeEnvironment.application.getString(R.string.app_name);
        ToastUtil.show(RuntimeEnvironment.application, R.string.app_name);
        assertThat(toastString, is(ShadowToast.getTextOfLatestToast()));
        System.out.println(toastString);
    }

    @Test
    public void testShowWithNullString() throws Exception {
        String toastString = "pre string";
        ToastUtil.show(RuntimeEnvironment.application, toastString);

        String nullString = null;
        ToastUtil.show(RuntimeEnvironment.application, nullString);
        assertThat(toastString, is(ShadowToast.getTextOfLatestToast()));
    }
}