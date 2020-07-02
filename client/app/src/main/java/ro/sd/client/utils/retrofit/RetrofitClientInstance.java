package ro.sd.client.utils.retrofit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    private static RetrofitClientInstance myInstance;
    private static final String BASE_URL = "http://10.0.2.2:8080/warehouse/";

    public RetrofitClientInstance() {

        OkHttpClient client = new OkHttpClient();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized RetrofitClientInstance getInstance(){
        if(myInstance == null) {
            myInstance = new RetrofitClientInstance();
        }
        return myInstance;
    }

    public EndpointsAPI getApi(){
        return retrofit.create(EndpointsAPI.class);
    }
}
