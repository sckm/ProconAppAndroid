package jp.gr.procon.proconapp.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class DateUtilTest {
    private static long curTime;

    @Before
    public void setup() {
        curTime = System.currentTimeMillis();
    }

    @Test
    public void testTimeToPostDate() throws Exception {
        // timeToPostDate呼び出し時のタイミングによってテストが失敗したり成功したりする可能性がある
        // 引数に基準の時間をとるようにしたほうがいいかも
        long futureTime = curTime + 1 * 1000;
        String futureTimeStr = DateUtil.timeToPostDate(futureTime);
        assertThat(futureTimeStr, is(""));

        long pastSec = curTime - 1 * 1000;
        String pastSecStr = DateUtil.timeToPostDate(pastSec);
        assertThat(pastSecStr, is("1秒前"));

        long pastMin = curTime - 1 * 60 * 1000;
        String pastMinStr = DateUtil.timeToPostDate(pastMin);
        assertThat(pastMinStr, is("1分前"));

        long pastHour = curTime - 1 * 60 * 60 * 1000;
        String pastHourStr = DateUtil.timeToPostDate(pastHour);
        assertThat(pastHourStr, is("1時間前"));

        long pastDay = curTime - 1 * 24 * 60 * 60 * 1000;
        String pastDayStr = DateUtil.timeToPostDate(pastDay);
        assertThat(pastDayStr, is("1日前"));

        long past10000Day = curTime - 10000L * 24 * 60 * 60 * 1000;
        String past10000DayStr = DateUtil.timeToPostDate(past10000Day);
        assertThat(past10000DayStr, is("10000日前"));
    }
}