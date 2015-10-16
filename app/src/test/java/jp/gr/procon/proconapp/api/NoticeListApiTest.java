package jp.gr.procon.proconapp.api;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.net.HttpURLConnection;

import jp.gr.procon.proconapp.CustomBuildConfig;
import jp.gr.procon.proconapp.dummymodel.DummyNotice;
import jp.gr.procon.proconapp.model.NoticeList;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = CustomBuildConfig.class, sdk = 21)
public class NoticeListApiTest {
    @Rule
    public MockWebServer mServer = new MockWebServer();

    protected String USER_TOKEN = "user_token";
    private NoticeListApi.GetRequest mApi;

    @Before
    public void setup() {
        mApi = spy(new NoticeListApi.GetRequest(USER_TOKEN));
        mockDefaultHttpUrl(mApi);
    }

    @After
    public void teraDown() {
        mApi = null;
    }

    @Test
    public void validRequest() {

    }

    @Test
    public void getListSuccess() {
        mServer.setDispatcher(mDispatcher);

        NoticeListApi.GetRequest result = mApi.get("0", "5");
        NoticeList noticeList = result.getResponseObj();

        assertThat(noticeList, is(notNullValue()));
        assertThat(noticeList.size(), is(5));
    }

    @Test
    public void getListNotExistPage() {
        mServer.setDispatcher(mDispatcher);

        NoticeListApi.GetRequest result = mApi.get("2", "5");
        NoticeList noticeList = result.getResponseObj();
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getResponseStatusCode(), is(404));
        assertThat(noticeList, is(nullValue()));
    }

    @Test
    public void getListFailWith503() {
        mServer.enqueue(new MockResponse()
                .setResponseCode(503));

        NoticeListApi.GetRequest result = mApi.get("0", "5");
        NoticeList noticeList = result.getResponseObj();
        assertThat(result.isSuccessful(), is(false));
        assertThat(result.getResponseStatusCode(), is(503));
        assertThat(noticeList, is(nullValue()));
    }


    public void mockDefaultHttpUrl(BaseApi api) {
        HttpUrl defaultUrl = mServer.url("/");
        HttpUrl.Builder builder = defaultUrl.newBuilder()
                .addEncodedPathSegment("api");
        doReturn(builder).when(api).getDefaultUrlBuilder();
    }

    final Dispatcher mDispatcher = new Dispatcher() {
        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            System.out.println(request.getPath());
            switch (request.getPath()) {
                case "/api/notices/list?page=0&count=5":
                    return new MockResponse()
                            .setResponseCode(HttpURLConnection.HTTP_OK)
                            .setBody(DummyNotice.getDummyNoticeList());

                default:
                    break;
            }

            return new MockResponse()
                    .setResponseCode(404);
        }
    };
}