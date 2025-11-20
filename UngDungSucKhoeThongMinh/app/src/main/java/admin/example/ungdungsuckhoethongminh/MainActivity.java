package admin.example.ungdungsuckhoethongminh;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import admin.example.ungdungsuckhoethongminh.model.Product;
import admin.example.ungdungsuckhoethongminh.model.User;
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

        // 🔥 1. Ánh xạ nút giao diện
        Button btnSignIn = findViewById(R.id.btnSignIn);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        // 🔥 2. Gán sự kiện chuyển trang
        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignInMethodScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.fade_in);
        });

        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpMethodScreen.class);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.fade_in);
        });

        // 🔥 3. Gọi API
        fetchUsers();
        // fetchProducts(); // Bật nếu bạn muốn test API sản phẩm
    }

    // ==========================
    // 🔥 GỌI API USERS
    // ==========================
    private void fetchUsers() {
        ApiClient.getApiService().getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> users = response.body();

                    for (User user : users) {
                        Log.d(TAG, "User ID: " + user.getId());
                        Log.d(TAG, "Name: " + user.getName());
                        Log.d(TAG, "Age: " + user.getAge());
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

    // ==========================
    // 🔥 GỌI API PRODUCTS
    // ==========================
    private void fetchProducts() {
        ApiClient.getApiService().getProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
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