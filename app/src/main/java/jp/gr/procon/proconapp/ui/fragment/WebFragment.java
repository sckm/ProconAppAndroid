package jp.gr.procon.proconapp.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.gr.procon.proconapp.R;

public class WebFragment extends BaseFragment {
    private static final String ARG_DATA = "arg_data";
    private static final String MINE_TYPE_TEXT_PLAIN = "text/html";
    private static final String ENCODING_UTF8 = "utf8";


    private WebView mWebView;
    private String mDataString;

    public static WebFragment newInstanceWithData(String data) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DATA, data);
        fragment.setArguments(args);
        return fragment;
    }

    public WebFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDataString = getArguments().getString(ARG_DATA);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mWebView = (WebView) view.findViewById(R.id.web_view);
        // TODO progress
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        if (!TextUtils.isEmpty(mDataString)) {
            mWebView.loadDataWithBaseURL(null, mDataString, MINE_TYPE_TEXT_PLAIN, ENCODING_UTF8, null);
        }

    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
