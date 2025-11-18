package admin.example.ungdungsuckhoethongminh.network;


import java.util.List;

import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("products")
    Call<List<Product>> getProducts();
}