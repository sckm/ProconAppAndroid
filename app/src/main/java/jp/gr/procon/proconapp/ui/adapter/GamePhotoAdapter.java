package jp.gr.procon.proconapp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.util.List;

import jp.gr.procon.proconapp.R;
import jp.gr.procon.proconapp.model.GamePhoto;
import timber.log.Timber;

public class GamePhotoAdapter extends ArrayAdapter<GamePhoto> {
    public GamePhotoAdapter(Context context, List<GamePhoto> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(ViewHolder.RESOURCE_ID, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GamePhoto item = getItem(position);
        Glide.with(getContext())
                .load(item.getmThumbnailUrl())
                .asBitmap()
//                .asBitmap()
//                .override(300, 300)
//                .fitCenter()
//                .centerCrop()
                .transform(new ScaleBaseOutWidth(getContext()))
                .into(holder.mImageView);

        return convertView;
    }


    private class ViewHolder {
        private static final int RESOURCE_ID = R.layout.item_game_photo;

        private ImageView mImageView;

        public ViewHolder(View v) {
            mImageView = (ImageView) v.findViewById(R.id.thumbnail_view);
//            Timber.d("ViewHolder: " + mImageView);
        }
    }

    private static class ScaleBaseOutWidth extends BitmapTransformation {

        public ScaleBaseOutWidth(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            Timber.d("transform: from (" + toTransform.getWidth() + ", " + toTransform.getHeight() + ") to (" + outWidth + ", " + outHeight + ")");
            return Bitmap.createScaledBitmap(toTransform, outWidth, toTransform.getHeight() * outWidth / toTransform.getWidth(), true);
        }

        @Override
        public String getId() {
            return ScaleBaseOutWidth.class.getSimpleName();
        }
    }
}
