
package com.maysa.marvelapp.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class CharactersDataModel  implements Parcelable{

    @SerializedName("attributionHTML")
    private String mAttributionHTML;
    @SerializedName("attributionText")
    private String mAttributionText;
    @SerializedName("code")
    private Long mCode;
    @SerializedName("copyright")
    private String mCopyright;
    @SerializedName("data")
    private Data mData;
    @SerializedName("etag")
    private String mEtag;
    @SerializedName("status")
    private String mStatus;


    protected CharactersDataModel(Parcel in) {
        mAttributionHTML = in.readString();
        mAttributionText = in.readString();
        if (in.readByte() == 0) {
            mCode = null;
        } else {
            mCode = in.readLong();
        }
        mCopyright = in.readString();
        mData = in.readParcelable(Data.class.getClassLoader());
        mEtag = in.readString();
        mStatus = in.readString();
    }

    public static final Creator<CharactersDataModel> CREATOR = new Creator<CharactersDataModel>() {
        @Override
        public CharactersDataModel createFromParcel(Parcel in) {
            return new CharactersDataModel(in);
        }

        @Override
        public CharactersDataModel[] newArray(int size) {
            return new CharactersDataModel[size];
        }
    };

    public String getAttributionHTML() {
        return mAttributionHTML;
    }

    public void setAttributionHTML(String attributionHTML) {
        mAttributionHTML = attributionHTML;
    }

    public String getAttributionText() {
        return mAttributionText;
    }

    public void setAttributionText(String attributionText) {
        mAttributionText = attributionText;
    }

    public Long getCode() {
        return mCode;
    }

    public void setCode(Long code) {
        mCode = code;
    }

    public String getCopyright() {
        return mCopyright;
    }

    public void setCopyright(String copyright) {
        mCopyright = copyright;
    }

    public Data getData() {
        return mData;
    }

    public void setData(Data data) {
        mData = data;
    }

    public String getEtag() {
        return mEtag;
    }

    public void setEtag(String etag) {
        mEtag = etag;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAttributionHTML);
        dest.writeString(mAttributionText);
        if (mCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mCode);
        }
        dest.writeString(mCopyright);
        dest.writeParcelable(mData, flags);
        dest.writeString(mEtag);
        dest.writeString(mStatus);
    }
}
