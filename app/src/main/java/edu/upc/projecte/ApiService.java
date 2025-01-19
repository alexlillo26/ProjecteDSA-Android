package edu.upc.projecte;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import java.util.List;

public interface ApiService {

    @POST ("users/partidas")
    Call<Void> saveGame(@Body String level);

    @POST("denuncia")
    Call<Void> enviarDenuncia(@Body Denuncia denuncia);

    @POST("users")
    Call<Void> registerUser(@Body User user);

    @POST("users/login")
    Call<Void> loginUser(@Body User user);

    @GET("users/{username}/partidas")
    Call<ResponseBody> getGame(@Path("username") String name);

    @GET("store")
    Call<List<Item>> getItems();

    @GET("user/{id}/coins")
    Call<Integer> getUserCoins(@Path("id") String userId);

    @PUT("users/{username}/profile")
    Call<Void> updateUserProfile(@Path("username") String username, @Body User userProfileUpdate);

    @GET("users/{username}/profile")
    Call<User> getUserProfile(@Path("username") String username);


    @POST("users/purchase")
    Call<Void> purchase(@Body PurchaseRequest request);
    //
    @POST("question")
    Call<ResponseBody> submitQuestion(@Body Question question);

    @GET("users/inventories/{username}")
    Call<List<Item>> getUserInventory(@Path("username") String username);
}