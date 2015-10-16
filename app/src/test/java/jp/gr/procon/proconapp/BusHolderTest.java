package jp.gr.procon.proconapp;

import com.squareup.otto.Bus;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import jp.gr.procon.proconapp.event.BusHolder;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class BusHolderTest {

    @Test
    public void testGetBusHolder() {
        Bus bus = BusHolder.getInstance();
        assertThat(bus, is(notNullValue()));
    }

    @Test
    public void testBusHolderSingleton() {
        Bus bus1 = BusHolder.getInstance();
        Bus bus2 = BusHolder.getInstance();
        assertThat(bus1, is(bus2));
    }
}
