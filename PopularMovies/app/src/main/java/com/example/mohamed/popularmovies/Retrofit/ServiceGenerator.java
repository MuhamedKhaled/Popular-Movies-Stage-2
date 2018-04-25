package com.example.mohamed.popularmovies.Retrofit;
import android.content.Context;

import com.example.mohamed.popularmovies.fragments.PopularMoviesFragment;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mohamed on 09/03/18.
 */

public class ServiceGenerator {

    static Context mContext;

    public ServiceGenerator(Context Context) {
        mContext = Context;
    }

    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()));


    public static <S> S createService(Class<S> serviceClass) {
        httpClient.addInterceptor(new AddCookiesInterceptor(mContext));
        httpClient.addInterceptor(new ReceivedCookiesInterceptor(mContext));
        Retrofit retrofit = builder.client(httpClient.followRedirects(true).build()).build();

        return retrofit.create(serviceClass);
    }


}
