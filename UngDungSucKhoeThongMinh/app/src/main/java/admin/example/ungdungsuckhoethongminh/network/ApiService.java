package admin.example.ungdungsuckhoethongminh.network;


import java.util.List;

import admin.example.ungdungsuckhoethongminh.FitnessWeek;
import admin.example.ungdungsuckhoethongminh.model.Fitness;
import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("products")
    Call<List<Product>> getProducts();

    @GET("fitness")
    Call<List<Fitness>> getFitnessByDate(@Query("ngay") String ngay);

    @GET("fitness_week")
    Call<List<FitnessWeek>> getFitnessWeekByWeek(@Query("tuan") String tuan);


}