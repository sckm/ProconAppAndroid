package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.gr.procon.proconapp.R;

public class WebFragment extends BaseFragment {
    private static final String ARG_URL = "arg_url";
    private static final String ARG_DATA = "arg_data";
    private static final String MINE_TYPE_TEXT_PLAIN = "text/html";
    private static final String ENCODING_UTF8 = "utf8";

    public interface OnLoadUrlListener {
        boolean onLoadUrl(String url);
    }

    private WebView mWebView;
    private String mUrlString;
    private String mDataString;
    private OnLoadUrlListener mOnLoadUrlListener;

    public static WebFragment newInstance(String url) {
        return newInstance(url, null);
    }

    public static WebFragment newInstanceWithData(String data) {
        return newInstance(null, data);
    }

    /**
     * @param url  loadUrlで開くページurl
     * @param data loadDataWithBaseUrlで表示するhtmlデータ
     * @return WebFragment
     */
    private static WebFragment newInstance(String url, String data) {
        WebFragment fragment = new WebFragment();
        Bundle args = new Bundle();
        args.putString(ARG_URL, url);
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
            mUrlString = getArguments().getString(ARG_URL);
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
        final View loadingView = view.findViewById(R.id.progress);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (mOnLoadUrlListener != null) {
                    if (mOnLoadUrlListener.onLoadUrl(url)) {
                        return true;
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loadingView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loadingView.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                loadingView.setVisibility(View.GONE);
            }
        });

        if (!TextUtils.isEmpty(mUrlString)) {
            mWebView.loadUrl(mUrlString);
        } else if (!TextUtils.isEmpty(mDataString)) {
            mWebView.loadDataWithBaseURL(null, mDataString, MINE_TYPE_TEXT_PLAIN, ENCODING_UTF8, null);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof OnLoadUrlListener) {
            mOnLoadUrlListener = (OnLoadUrlListener) parent;
        } else if (activity instanceof OnLoadUrlListener) {
            mOnLoadUrlListener = (OnLoadUrlListener) activity;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoadUrlListener = null;
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.stopLoading();
            mWebView.setWebViewClient(null);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
