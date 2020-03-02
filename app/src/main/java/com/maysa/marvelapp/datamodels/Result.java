
package com.maysa.marvelapp.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class Result implements Parcelable {

    @SerializedName("comics")
    private Comics mComics;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("events")
    private Events mEvents;
    @SerializedName("id")
    private Long mId;
    @SerializedName("modified")
    private String mModified;
    @SerializedName("name")
    private String mName;
    @SerializedName("resourceURI")
    private String mResourceURI;
    @SerializedName("series")
    private Series mSeries;
    @SerializedName("stories")
    private Stories mStories;
    @SerializedName("thumbnail")
    private Thumbnail mThumbnail;
    @SerializedName("urls")
    private List<Url> mUrls;


    protected Result(Parcel in) {
        mDescription = in.readString();
        if (in.readByte() == 0) {
            mId = null;
        } else {
            mId = in.readLong();
        }
        mModified = in.readString();
        mName = in.readString();
        mResourceURI = in.readString();
        mSeries = in.readParcelable(Series.class.getClassLoader());
        mStories = in.readParcelable(Stories.class.getClassLoader());
        mThumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        mUrls = in.createTypedArrayList(Url.CREATOR);
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public Comics getComics() {
        return mComics;
    }

    public void setComics(Comics comics) {
        mComics = comics;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Events getEvents() {
        return mEvents;
    }

    public void setEvents(Events events) {
        mEvents = events;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getModified() {
        return mModified;
    }

    public void setModified(String modified) {
        mModified = modified;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getResourceURI() {
        return mResourceURI;
    }

    public void setResourceURI(String resourceURI) {
        mResourceURI = resourceURI;
    }

    public Series getSeries() {
        return mSeries;
    }

    public void setSeries(Series series) {
        mSeries = series;
    }

    public Stories getStories() {
        return mStories;
    }

    public void setStories(Stories stories) {
        mStories = stories;
    }

    public Thumbnail getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        mThumbnail = thumbnail;
    }

    public List<Url> getUrls() {
        return mUrls;
    }

    public void setUrls(List<Url> urls) {
        mUrls = urls;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mDescription);
        if (mId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(mId);
        }
        dest.writeString(mModified);
        dest.writeString(mName);
        dest.writeString(mResourceURI);
        dest.writeParcelable(mSeries, flags);
        dest.writeParcelable(mStories, flags);
        dest.writeParcelable(mThumbnail, flags);
        dest.writeTypedList(mUrls);
    }
}
