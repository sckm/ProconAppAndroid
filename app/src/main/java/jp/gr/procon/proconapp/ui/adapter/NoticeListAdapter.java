package jp.gr.procon.proconapp.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import jp.gr.procon.proconapp.model.Notice;
import jp.gr.procon.proconapp.ui.view.NoticeListItemView;

public class NoticeListAdapter extends ArrayAdapter<Notice> {
    public NoticeListAdapter(Context context, List<Notice> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoticeListItemView holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(NoticeListItemView.RESOURECE_ID, parent, false);
            holder = new NoticeListItemView(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NoticeListItemView) convertView.getTag();
        }

        Notice item = getItem(position);
        holder.bindTo(item);

        return convertView;
    }


}
