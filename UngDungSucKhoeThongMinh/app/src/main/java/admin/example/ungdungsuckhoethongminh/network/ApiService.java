package admin.example.ungdungsuckhoethongminh.network;

import java.util.List;
import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.model.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Body;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    // Lấy thông tin user theo ID
    @GET("users/{id}")
    Call<User> getUserById(@Path("id") int id);

    @GET("products")
    Call<List<Product>> getProducts();

    // Cập nhật user
    @PUT("users/{id}")
    Call<User> updateUser(@Path("id") int id, @Body User user);
}
