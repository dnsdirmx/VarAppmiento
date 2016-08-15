package mx.uv.varappmiento.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by willo on 27/06/2016.
 */
public class VarAppiConsumer {
    public static final String BASE_URL = "http://varappmiento.esy.es/";
    Gson gson;
    Retrofit retrofit;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static VarAppiConsumer ourInstance = new VarAppiConsumer();

    public static VarAppiConsumer getInstance() {
        return ourInstance;
    }

    private VarAppiConsumer() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

}
