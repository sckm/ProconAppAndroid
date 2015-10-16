package jp.gr.procon.proconapp;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class ProconApplication extends BaseApplication {
    synchronized public Tracker getTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            analytics.setDryRun(true);

            mTracker = analytics.newTracker(GoogleAnalyticsConfig.ANALYTICS_PROPERTY_ID);
            mTracker.enableExceptionReporting(true);
        }
        return mTracker;
    }
}
