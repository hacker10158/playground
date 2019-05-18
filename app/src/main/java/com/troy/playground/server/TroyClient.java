package com.troy.playground.server;

import com.troy.playground.server.response.SearchPictureResponse;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class TroyClient implements TroyClientInterface {

    private static final int MAX_REQUESTS = 64;
    private static final int MAX_REQUESTS_PER_HOST = 16;

    private MyService myService;

    private boolean isRelease = false;

    public TroyClient() {
        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(MAX_REQUESTS);
        dispatcher.setMaxRequestsPerHost(MAX_REQUESTS_PER_HOST);

        // logging interceptor
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (isRelease) {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            // for debug complete raw response data
            //logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(false)
                .connectTimeout(30, TimeUnit.SECONDS)
                .dispatcher(dispatcher)
                .connectionPool(new ConnectionPool(5, 50, TimeUnit.SECONDS))
                .addInterceptor(logging) // enable log
                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        // should not change this into main branch( ex dev-2.0 or master)
        // It can be test in local by modify settings.xml or create another branch for server build test

        String awsAddress = "https://pixabay.com/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(awsAddress)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build();

        myService = retrofit.create(MyService.class);
    }

    public interface MyService {
        @Headers("Content-Type:application/json")
        @GET("api/")
        Single<SearchPictureResponse> searchPicture(@Query("key") String key, @Query("q") String keyword, @Query("page") int page);
    }

    @Override
    public Single<SearchPictureResponse> searchPicture(String keyword, int page) {
        return myService.searchPicture("12494785-8e935ca087834ee899281ef5f", keyword, page);
    }
}
