
package com.maysa.marvelapp.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Stories implements Parcelable{

    @SerializedName("available")
    private Long mAvailable;
    @SerializedName("collectionURI")
    private String mCollectionURI;
    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("returned")
    private Long mReturned;


    protected Stories(Parcel in) {
        if (in.readByte() == 0) {
            mAvailable = null;
        } else {
            mAvailable = in.readLong();
        }
        mCollectionURI = in.readString();
        if (in.readByte() == 0) {
            mReturned = null;
        } else {
            mReturned = in.readLong();
        }
    }

    public static final Creator<Stories> CREATOR = new Creator<Stories>() {
        @Override
        public Stories createFromParcel(Parcel in) {
            return new Stories(in);
        }

        @Override
        public Stories[] newArray(int size) {
            return new Stories[size];
        }
    };

    public Long getAvailable() {
        return mAvailable;
    }

    public void setAvailable(Long available) {
        mAvailable = available;
    }

    public String getCollectionURI() {
        return mCollectionURI;
    }

    public void setCollectionURI(String collectionURI) {
        mCollectionURI = collectionURI;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public Long getReturned() {
        return mReturned;
    }

    public void setReturned(Long returned) {
        mReturned = returned;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mAvailable == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mAvailable);
        }
        dest.writeString(mCollectionURI);
        if (mReturned == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mReturned);
        }
    }
}
