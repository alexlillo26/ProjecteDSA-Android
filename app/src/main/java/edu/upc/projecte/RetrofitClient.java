package edu.upc.projecte;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    //private static final String BASE_URL = "http://10.0.2.2:8080/dsaApp/";
    private static final String BASE_URL = "http://147.83.7.207:8080/dsaApp/"; // Per tenir connexió amb la BBDD cal tenir posada la URL: http://10.0.2.2:8080/dsaApp/
    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // Utilitza Gson per a JSON
                    .build();
        }
        return retrofit;
    }
}

