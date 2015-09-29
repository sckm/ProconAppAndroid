package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GamePhotoListApi;
import jp.gr.procon.proconapp.api.asynctask.GamePhotoApiAsyncTask;
import jp.gr.procon.proconapp.dummymodel.DummyGamePhoto;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class PhotoOutlineFragment extends BaseFragment implements
        View.OnClickListener
        , GamePhotoApiAsyncTask.GamePhotoApiListener {
    private static final int MAX_NUM_PHOTO = 3;
    private static final int[] THUMBNAIL_IMAGE_RES_IS = new int[]{R.id.image1, R.id.image2};

    public interface OnShowAllGamePhotoClickListener {
        void onShowAllGamePhotoClick();
    }

    public static PhotoOutlineFragment newInstance() {
        PhotoOutlineFragment fragment = new PhotoOutlineFragment();
        return fragment;
    }

    private LinearLayout mLinearLayout;

    private GamePhotoList mPhotoList;
    private OnShowAllGamePhotoClickListener mOnShowAllGamePhotoClickListener;

    private GamePhotoApiAsyncTask mGamePhotoApiAsyncTask;

    public PhotoOutlineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outline_game_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO savedInstanceState

        // TODO headerをviewかfragmentへまとめる
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);
        iconImageView.setImageResource(R.drawable.photo);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_game_photo);

        TextView showAllTextView = (TextView) view.findViewById(R.id.outline_show_all);
        showAllTextView.setOnClickListener(this);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.thumbnails_layout);

        if (mPhotoList != null) {
            setDataToView();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPhotoList == null) {
            startApiAsyncTask();
        }
    }

    @Override
    public void onPause() {
        stopApiAsyncTask();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof OnShowAllGamePhotoClickListener) {
            mOnShowAllGamePhotoClickListener = (OnShowAllGamePhotoClickListener) parent;
        } else if (activity instanceof OnShowAllGamePhotoClickListener) {
            mOnShowAllGamePhotoClickListener = (OnShowAllGamePhotoClickListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }
    }

    @Override
    public void onDetach() {
        mOnShowAllGamePhotoClickListener = null;
        super.onDetach();
    }

    private void setDataToView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        int i = 0;
        View mLayout = null;
        for (GamePhoto photo : mPhotoList) {
            if (i % 2 == 0) {
                mLayout = inflater.inflate(R.layout.view_two_column_image, mLinearLayout, false);
            }
            ImageView imageView = (ImageView) mLayout.findViewById(THUMBNAIL_IMAGE_RES_IS[i % 2]).findViewById(R.id.thumbnail_view);

            if (i % 2 == 0) {
                mLinearLayout.addView(mLayout);
            }
            // TODO 画像表示サイズ調整
            Glide.with(this)
                    .load(photo.getmThumbnailUrl())
//                    .centerCrop()
                    .fitCenter()
                    .into(imageView);
            i++;
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.outline_show_all:
                if (mOnShowAllGamePhotoClickListener != null) {
                    mOnShowAllGamePhotoClickListener.onShowAllGamePhotoClick();
                }
                break;
        }
    }

    @Override
    public void onPreExecuteGamePhotoApi() {
    }

    @Override
    public void onPostExecuteGamePhotoApi(GamePhotoListApi.GetRequest api) {
        if (isDetached() || getActivity() == null) {
            return;
        }

        if (api.isSuccessful()) {
            mPhotoList = api.getResponseObj();
            setDataToView();
        } else {
            // TODO error
        }
    }

    @Override
    public void onCanceledGamePhotoApi() {
    }

    private void startApiAsyncTask() {
        if (mGamePhotoApiAsyncTask != null) {
            return;
        }

        mGamePhotoApiAsyncTask = new GamePhotoApiAsyncTask(getUserToken(), this);
        mGamePhotoApiAsyncTask.execute(MAX_NUM_PHOTO);
    }

    private void stopApiAsyncTask() {
        if (mGamePhotoApiAsyncTask != null) {
            mGamePhotoApiAsyncTask.cancel(true);
            mGamePhotoApiAsyncTask = null;
        }
    }
}
