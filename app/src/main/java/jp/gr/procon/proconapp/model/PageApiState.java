package jp.gr.procon.proconapp.model;

import java.util.ArrayList;

import timber.log.Timber;

/**
 * ページを使ったAPI用のクラス
 *
 * @param <T> ページ内リストのアイテムの型
 */
public class PageApiState<T> {
    /** １ページのアイテム数 */
    public static final int DEFAULT_NUM_PAGE_ITEM = 10;

    /** APIから取得したアイテムのリスト */
    private ArrayList<T> mItems;

    /** APIで次に取得すべきページ */
    private int mNextPage;

    /** データをすべて読み込み終わった時はtrue, それ以外はfalse */
    private boolean mIsLoadedAll;

    private final int mNumPageItem;

    public PageApiState() {
        this(DEFAULT_NUM_PAGE_ITEM);
    }

    public PageApiState(int numPageItem) {
        mItems = new ArrayList<>();
        mNextPage = 0;
        mIsLoadedAll = false;
        mNumPageItem = numPageItem;
    }

    /**
     * 読み込んだページを追加する
     * 次に読み込むページ番号を更新
     * 追加されたページ内のアイテム数が１ページのアイテム数より少ない場合は読み込み完了を判定
     */
    public void addPageList(ArrayList<T> pageItems) {
        if (pageItems == null) {
            Timber.d("addPageList: items is null");
            return;
        }

        mIsLoadedAll = pageItems.size() < mNumPageItem;

        mItems.addAll(pageItems);
        mNextPage++;
    }

    public ArrayList<T> getItems() {
        return mItems;
    }

    public int getNextPage() {
        return mNextPage;
    }

    public boolean isLoadedAll() {
        return mIsLoadedAll;
    }

    public int getNumPageItem() {
        return mNumPageItem;
    }
}
