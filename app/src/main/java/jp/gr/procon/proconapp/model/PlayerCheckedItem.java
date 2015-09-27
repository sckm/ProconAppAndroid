package jp.gr.procon.proconapp.model;

public class PlayerCheckedItem implements Comparable<Long> {

    private Player mPlayer;

    private boolean mIsCheck;

    public PlayerCheckedItem(Player player, boolean isCheck) {
        mPlayer = player;
        mIsCheck = isCheck;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public boolean isCheck() {
        return mIsCheck;
    }

    public void setIsCheck(boolean isCheck) {
        mIsCheck = isCheck;
    }

    public boolean toggleCheck() {
        mIsCheck = !mIsCheck;
        return mIsCheck;
    }

    @Override
    public int compareTo(Long another) {
        return mPlayer.getId().compareTo(another);
    }
}
