package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.gr.procon.proconapp.R;

public class SettingFragment extends BaseFragment implements View.OnClickListener {
    public interface OnClickSettingItemListener {
        void onClickNoticeSetting();

        void onClickLicense();
    }

    private OnClickSettingItemListener mOnClickSettingItemListener;

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 通知設定
        View notice = view.findViewById(R.id.item_notice);
        ((TextView) notice.findViewById(R.id.title_text)).setText(R.string.title_setting_notice);
        notice.setOnClickListener(this);

        // ライセンス
        View license = view.findViewById(R.id.item_license);
        ((TextView) license.findViewById(R.id.title_text)).setText(R.string.title_setting_license);
        license.setOnClickListener(this);

        // バージョン
        View version = view.findViewById(R.id.item_version);
        ((TextView) version.findViewById(R.id.title_text)).setText(R.string.title_setting_version);
        
        TextView versionText = (TextView) version.findViewById(R.id.body_text);
        versionText.setText(getVersionName(getActivity()));
        versionText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnClickSettingItemListener) {
            mOnClickSettingItemListener = (OnClickSettingItemListener) fragment;
        } else if (activity instanceof OnClickSettingItemListener) {
            mOnClickSettingItemListener = (OnClickSettingItemListener) activity;
        } else {
            throw new RuntimeException("parent fragment or activity mush implements OnClickSettingItemListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickSettingItemListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.item_notice:
                if (mOnClickSettingItemListener != null) {
                    mOnClickSettingItemListener.onClickNoticeSetting();
                }
                break;

            case R.id.item_license:
                if (mOnClickSettingItemListener != null) {
                    mOnClickSettingItemListener.onClickLicense();
                }
                break;

            default:
                break;
        }
    }

    public static String getVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        String versionName = "";
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
