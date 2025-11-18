package admin.example.ungdungsuckhoethongminh.network;


import java.util.List;

import admin.example.ungdungsuckhoethongminh.model.MucDoHoatDong;
import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.model.ThongTinCanNang;
import admin.example.ungdungsuckhoethongminh.model.TocDoCanNang;
import admin.example.ungdungsuckhoethongminh.model.User;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("users")
    Call<List<User>> getUsers();

    @GET("products")
    Call<List<Product>> getProducts();
    @GET("thongTinCanNang")
    Call<List<ThongTinCanNang>> getThongTinCanNang();

    @GET("mucDoHoatDong")
    Call<List<MucDoHoatDong>> getMucDoHoatDong();

    @GET("tocDoCanNang")
    Call<List<TocDoCanNang>> getTocDoCanNang();

}