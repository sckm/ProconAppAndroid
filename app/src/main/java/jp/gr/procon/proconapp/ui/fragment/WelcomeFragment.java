package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.gr.procon.proconapp.R;

public class WelcomeFragment extends BaseFragment {
    public interface OnClickEnterButtonListener {
        void onClickEnterButton();
    }

    private OnClickEnterButtonListener mOnClickEnterButtonListener;

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_enter_app).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClickEnterButtonListener != null) {
                    mOnClickEnterButtonListener.onClickEnterButton();
                }
            }
        });

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnClickEnterButtonListener) {
            mOnClickEnterButtonListener = (OnClickEnterButtonListener) activity;
        } else {
            throw new RuntimeException("must implement OnClickEnterButtonListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnClickEnterButtonListener = null;
    }
}
