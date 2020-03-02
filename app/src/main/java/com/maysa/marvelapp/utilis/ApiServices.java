package com.maysa.marvelapp.utilis;


import com.maysa.marvelapp.datamodels.CharactersDataModel;
import com.maysa.marvelapp.datamodels.Item;
import com.maysa.marvelapp.datamodels.Result;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

// Implement Retrofit part APIS
public interface ApiServices {


    @GET(Constants.CHARACTERS)
    Observable<CharactersDataModel> GetCHARACTERS(@QueryMap HashMap<String, Object> data);

    @GET()
    Observable<CharactersDataModel> GetPhoto(@Url String url);


}
