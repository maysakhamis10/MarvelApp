package com.maysa.marvelapp.viewmodels;

import android.util.Log;
import com.google.gson.Gson;
import com.maysa.marvelapp.datamodels.CharactersDataModel;
import com.maysa.marvelapp.datamodels.Item;
import com.maysa.marvelapp.datamodels.Result;
import com.maysa.marvelapp.utilis.Constants;
import com.maysa.marvelapp.utilis.MyApplication;
import com.maysa.marvelapp.utilis.RetrofitSetting;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends ViewModel {

    private MutableLiveData<Item> comicMutableLiveData ;
    private MutableLiveData<Item> eventsMutableLiveData ;
    private MutableLiveData<Item> seriesMutableLiveData ;
    private MutableLiveData<Item> storiesMutableLiveData ;

    public LiveData<Item> getPhotosOfEachComic(List<Item> allList ){
        comicMutableLiveData = new MutableLiveData<>();
        getPhotoOfItemsMethod(allList,"1");
        return comicMutableLiveData;
    }
    public LiveData<Item> getPhotosOfEachStory(List<Item> allList ){
        storiesMutableLiveData = new MutableLiveData<>();
        getPhotoOfItemsMethod(allList,"2");
        return storiesMutableLiveData;
    }
    public LiveData<Item> getPhotosOfEachSeries(List<Item> allList ){
        seriesMutableLiveData = new MutableLiveData<>();
        getPhotoOfItemsMethod(allList,"3");
        return seriesMutableLiveData;
    }
    public LiveData<Item> getPhotosOfEachEvent(List<Item> allList){
        eventsMutableLiveData = new MutableLiveData<>();
        getPhotoOfItemsMethod(allList,"4");
        return eventsMutableLiveData;
    }

    private void getPhotoOfItemsMethod( List<Item> allList ,String isFlag){
        for (Item item: allList) {
            getPhotoForEachItem(item,isFlag);
        }

    }
    private void getPhotoForEachItem(Item item, String isFlag){
        String  hash =  MyApplication.MD5String("1" + Constants.PRIVATE_KEY + Constants.PUBLIC_KEY);
        String urlStr = item.getResourceURI()+"?apikey="+Constants.PUBLIC_KEY+"&ts=1&hash="+hash;
        Observable<CharactersDataModel> modelObservable =  RetrofitSetting.getInstance()
                .getApi().GetPhoto(urlStr);
        modelObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<CharactersDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(CharactersDataModel dataModel) {
                        if (dataModel.getData().getResults()!=null) {
                            for (Result result: dataModel.getData().getResults()) {
                                Log.d("RESULT",new Gson().toJson(result));
                                if (result.getThumbnail()!=null) {
                                    item.setPhotoUrl(result.getThumbnail().getPath() + "." +
                                            result.getThumbnail().getExtension());
                                }
                                else {
                                    item.setPhotoUrl("no_photo");
                                }
                                switch (isFlag) {
                                    case "1":
                                        comicMutableLiveData.postValue(item);
                                        break;
                                    case "2":
                                        Log.d("TEST", item.getPhotoUrl());
                                        storiesMutableLiveData.postValue(item);
                                        break;
                                    case "3":
                                        seriesMutableLiveData.postValue(item);
                                        break;
                                    default:
                                        eventsMutableLiveData.postValue(item);
                                        break;
                                }
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                    }
                    @Override
                    public void onComplete() {
                    }
                });

    }



}
