
package com.maysa.marvelapp.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Data  implements Parcelable{

    @SerializedName("count")
    private int mCount;
    @SerializedName("limit")
    private int mLimit;
    @SerializedName("offset")
    private int mOffset;
    @SerializedName("results")
    private List<Result> mResults;
    @SerializedName("total")
    private int mTotal;


    protected Data(Parcel in) {
        mCount = in.readInt();
        mLimit = in.readInt();
        mOffset = in.readInt();
        mResults = in.createTypedArrayList(Result.CREATOR);
        mTotal = in.readInt();
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getLimit() {
        return mLimit;
    }

    public void setLimit(int limit) {
        mLimit = limit;
    }

    public int getOffset() {
        return mOffset;
    }

    public void setOffset(int offset) {
        mOffset = offset;
    }

    public List<Result> getResults() {
        return mResults;
    }

    public void setResults(List<Result> results) {
        mResults = results;
    }

    public int getTotal() {
        return mTotal;
    }

    public void setTotal(int total) {
        mTotal = total;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCount);
        dest.writeInt(mLimit);
        dest.writeInt(mOffset);
        dest.writeTypedList(mResults);
        dest.writeInt(mTotal);
    }
}
