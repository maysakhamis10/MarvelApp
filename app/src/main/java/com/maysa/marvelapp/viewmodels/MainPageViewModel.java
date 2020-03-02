package com.maysa.marvelapp.viewmodels;


import android.util.Log;
import com.google.gson.Gson;
import com.maysa.marvelapp.datamodels.CharactersDataModel;
import com.maysa.marvelapp.datamodels.Item;
import com.maysa.marvelapp.datamodels.Result;
import com.maysa.marvelapp.utilis.Constants;
import com.maysa.marvelapp.utilis.MyApplication;
import com.maysa.marvelapp.utilis.RetrofitSetting;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPageViewModel extends ViewModel {

    //get all  characters
    private MutableLiveData<CharactersDataModel> charactersDataModelMutableLiveData;
    public LiveData<CharactersDataModel> getAllCharacters(int offset) {
        charactersDataModelMutableLiveData = new MutableLiveData<>();
        callAllCharactersMethod(offset);
        return charactersDataModelMutableLiveData;
    }
    private void callAllCharactersMethod(int offset) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("ts", "1");
        map.put("offset",offset);
        map.put("apikey", Constants.PUBLIC_KEY);
        map.put("hash", MyApplication.MD5String("1" + Constants.PRIVATE_KEY + Constants.PUBLIC_KEY));
        Observable<CharactersDataModel> charactersDataModelObservable = RetrofitSetting.
                getInstance().getApi().GetCHARACTERS(map);
        charactersDataModelObservable.subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<CharactersDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharactersDataModel response) {
                        charactersDataModelMutableLiveData.postValue(response);
                        Log.d("CHARACTERSSS", new Gson().toJson(response));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("CHARACTERSSS", new Gson().toJson(e));

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}

