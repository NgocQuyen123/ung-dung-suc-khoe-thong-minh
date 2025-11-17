package admin.example.ungdungsuckhoethongminh;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.model.User;
import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gọi API và hiển thị dữ liệu user
        fetchUsers();

        // Nếu muốn lấy products cũng có thể gọi fetchProducts()
        // fetchProducts();
    }

    private void fetchUsers() {
        ApiClient.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<User> users = response.body();
                    for (User user : users) {
                        Log.d(TAG, "User ID: " + user.getId());
                        Log.d(TAG, "Tên: " + user.getTen());
                        Log.d(TAG, "SĐT: " + user.getSdt());
                        Log.d(TAG, "Giới tính: " + user.getGioiTinh());
                        Log.d(TAG, "Chiều cao: " + user.getChieuCao());
                        Log.d(TAG, "Năm sinh: " + user.getNamSinh());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch users", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Nếu bạn vẫn muốn lấy products
    private void fetchProducts() {
        ApiClient.getApiService().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body();
                    for (Product product : products) {
                        Log.d(TAG, "Product ID: " + product.getId());
                        Log.d(TAG, "Name: " + product.getName());
                        Log.d(TAG, "Price: " + product.getPrice());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
