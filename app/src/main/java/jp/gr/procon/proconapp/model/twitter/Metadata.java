package jp.gr.procon.proconapp.model.twitter;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

// TODO 適宜必要なもの追加
public class Metadata implements Serializable {
    @SerializedName("iso_language_code")
    private String mIsoLanguageCode;

    @SerializedName("result_type")
    private String mResultType;


}
