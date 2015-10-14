# 高専プロコン公式App

* App公式サイト: [https://proconapp.com](https://proconapp.com)
* プロコン公式: [http://procon.gr.jp/](http://procon.gr.jp/)

このリポジトリは高専プロコンアプリ(プロコンアプリ)のAndroid版です。サーバーサイドなどの関連するサービスのリポジトリは以下にあります。

* APIサーバー [https://github.com/saga-dash/proconapp-server](https://github.com/saga-dash/proconapp-server)
* ランディングページ [https://github.com/novi/proconapp-lp](https://github.com/novi/proconapp-lp)
* ソーシャルフィードサーバー [https://github.com/novi/procon-social-server](https://github.com/novi/procon-social-server)
* iOS版 [https://github.com/novi/proconapp](https://github.com/novi/proconapp)
* Windows Phone版 [https://github.com/TK-R/wpproconapp](https://github.com/TK-R/wpproconapp)
* Android版 [https://github.com/sckm/ProconAppAndroid] (https://github.com/sckm/ProconAppAndroid)
* ランディングページ メール送信フォーム [https://github.com/saga-dash/proconapp-form](https://github.com/saga-dash/proconapp-form)

# ライセンス
関連するリポジトリ含め、すべてMITです。詳細は各リポジトリ内のLICENSEをご覧ください。

# ビルド方法
ビルドするには以下のファイルが必要です。

###API設定
APIサーバーの接続先の設定

``` app/src/main/java/jp/gr/procon/proconapp/api/ApiConfig.java
public class ApiConfig {
    public static final String DEFAULT_SCHEME = "YOUR API SERVER SCHEME";
    public static final String DEFAULT_HOST = "YOUR SERVER HOST";
    public static final String DEFAULT_PREFIX_PATH_SEGMENT = "api";
}
```

###Google Analytics設定
``` app/src/main/java/jp/gr/procon/proconapp/GoogleAnalyticsConfig.java
public class GoogleAnalyticsConfig {
    public static final String SCREEN_NAME_HOME = "Home";
    public static final String SCREEN_NAME_SOCIAL = "Social";
    public static final String ANALYTICS_PROPERTY_ID = "YOUR GOOGLE ANALYTICS PROPERTY ID";
}
```


###通知の設定
Google Cloud Messaging for Androidを使用した通知の設定

``` app/src/main/java/jp/gr/procon/proconapp/notification/Notification.java
public class NotificationConfig {
    public static final String SENDER_ID = "YOUR SENDER ID";
    // 今回は未使用
    public static final int NOTIFICATION_ID_GAME_RESULT = NOTIFICATION ID FOR GAME RESULT;

    public static final int NOTIFICATION_ID_MAIN = NOTIFICATION ID FOR HOME ;
}
```

