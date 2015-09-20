package jp.gr.procon.proconapp.ui.callback;

import android.support.v7.widget.RecyclerView;

public interface OnGetViewListener {
    void OnGetView(RecyclerView.Adapter adapter, int position);
}
