package jp.gr.procon.proconapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.api.GamePhotoListApi;
import jp.gr.procon.proconapp.api.asynctask.GamePhotoApiAsyncTask;
import jp.gr.procon.proconapp.dummymodel.DummyGamePhoto;
import jp.gr.procon.proconapp.event.BusHolder;
import jp.gr.procon.proconapp.event.RequestUpdateEvent;
import jp.gr.procon.proconapp.model.GamePhoto;
import jp.gr.procon.proconapp.model.GamePhotoList;
import jp.gr.procon.proconapp.ui.callback.OnClickPhotoListener;
import jp.gr.procon.proconapp.util.JsonUtil;
import timber.log.Timber;

public class PhotoOutlineFragment extends BaseFragment implements
        View.OnClickListener
        , GamePhotoApiAsyncTask.GamePhotoApiListener {
    private static final int MAX_NUM_PHOTO = 4;
    private static final int[] THUMBNAIL_IMAGE_RES_IS = new int[]{R.id.image1, R.id.image2};

    // 失敗/キャンセルを知りたい場合は追加
    public interface OnUpdatePhotoOutlineListener {
        void OnCompletePhotoOutlineUpdate();
    }

    public interface OnShowAllGamePhotoClickListener {
        void onShowAllGamePhotoClick();
    }

    public static PhotoOutlineFragment newInstance() {
        PhotoOutlineFragment fragment = new PhotoOutlineFragment();
        return fragment;
    }

    private LinearLayout mLinearLayout;
    private ArrayList<ViewHolder> holders;

    private GamePhotoList mPhotoList;
    private OnShowAllGamePhotoClickListener mOnShowAllGamePhotoClickListener;
    private OnUpdatePhotoOutlineListener mOnUpdatePhotoOutlineListener;
    private OnClickPhotoListener mOnClickPhotoListener;

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
        View header = view.findViewById(R.id.header);
        header.setOnClickListener(this);
        ImageView iconImageView = (ImageView) view.findViewById(R.id.icon);
        iconImageView.setImageResource(R.drawable.photo);

        TextView titleTextView = (TextView) view.findViewById(R.id.outline_title);
        titleTextView.setText(R.string.title_outline_game_photo);

        mLinearLayout = (LinearLayout) view.findViewById(R.id.thumbnails_layout);
        setupView();

        if (mPhotoList != null) {
            setDataToView();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        BusHolder.getInstance().register(this);
    }

    @Override
    public void onStop() {
        BusHolder.getInstance().unregister(this);
        super.onStop();
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

        if (parent != null && parent instanceof OnUpdatePhotoOutlineListener) {
            mOnUpdatePhotoOutlineListener = (OnUpdatePhotoOutlineListener) parent;
        } else if (activity instanceof OnUpdatePhotoOutlineListener) {
            mOnUpdatePhotoOutlineListener = (OnUpdatePhotoOutlineListener) activity;
        } else {
            throw new RuntimeException("parent or activity must implement listener");
        }

        if (parent != null && parent instanceof OnClickPhotoListener) {
            mOnClickPhotoListener = (OnClickPhotoListener) parent;
        } else if (activity instanceof OnClickPhotoListener) {
            mOnClickPhotoListener = (OnClickPhotoListener) activity;
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
        int i = 0;
        for (GamePhoto photo : mPhotoList) {
            ViewHolder holder = holders.get(i);

            final GamePhoto gamePhoto = photo;
            Glide.with(this)
                    .load(photo.getmThumbnailUrl())
                    .centerCrop()
                    .into(holder.imageView);
            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickPhotoListener != null) {
                        mOnClickPhotoListener.onClickPhoto(gamePhoto);
                    }
                }
            });

            i++;

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.header:
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

        mOnUpdatePhotoOutlineListener.OnCompletePhotoOutlineUpdate();
    }

    @Override
    public void onCanceledGamePhotoApi() {
        mOnUpdatePhotoOutlineListener.OnCompletePhotoOutlineUpdate();
    }


    private void setupView() {
        holders = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View mLayout = null;
        for (int i = 0; i < MAX_NUM_PHOTO; i++) {
            if (i % 2 == 0) {
                mLayout = inflater.inflate(R.layout.view_two_column_image, mLinearLayout, false);
                mLinearLayout.addView(mLayout);
            }
            ImageView imageView = (ImageView) mLayout.findViewById(THUMBNAIL_IMAGE_RES_IS[i % 2]).findViewById(R.id.thumbnail_view);
            ViewHolder holder = new ViewHolder(imageView);
            holders.add(holder);
        }
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

    @Subscribe
    public void requestUpdate(RequestUpdateEvent event) {
        stopApiAsyncTask();
        startApiAsyncTask();
    }

    private static class ViewHolder {
        private ImageView imageView;

        public ViewHolder(ImageView imageView) {
            this.imageView = imageView;
        }


    }
}
