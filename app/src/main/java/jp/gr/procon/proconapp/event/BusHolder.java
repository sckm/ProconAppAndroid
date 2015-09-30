package jp.gr.procon.proconapp.event;

import com.squareup.otto.Bus;

public class BusHolder {
    private static Bus sInstance;

    public static Bus getInstance() {
        if (sInstance == null) {
            sInstance = new Bus();
        }

        return sInstance;
    }
}
