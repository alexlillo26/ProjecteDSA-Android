package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaActivity extends AppCompatActivity {

    private TextView coinCounter;
    private TextView itemDescription;
    private int coinCount = 0;
    private ApiService apiService;
    private ProgressBar progressBar;

    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tienda);

        coinCounter = findViewById(R.id.coin_counter);
        itemDescription = findViewById(R.id.item_description);
        progressBar = findViewById(R.id.progressBar);

        Button buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(v -> {
            Intent intent = new Intent(TiendaActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Obtener el nombre de usuario del Intent
        String username = getIntent().getStringExtra("username");
        fetchUserProfile(username); // Obtener el perfil del usuario

        fetchItems();
    }

    private void fetchItems() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.getItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> itemList = response.body();
                    ItemAdapter adapter = new ItemAdapter(itemList, TiendaActivity.this);
                    RecyclerView recyclerView = findViewById(R.id.recycler_view);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(TiendaActivity.this, "Failed to load items", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserProfile(String username) {
        apiService.getUserProfile(username).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    setUser(user);
                    coinCount = user.getCoins();
                    coinCounter.setText("Coins: " + String.valueOf(coinCount));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void purchaseItem(Item item, int quantity) {
        if (coinCount >= item.getPrice() * quantity) {
            coinCount -= item.getPrice() * quantity;
            coinCounter.setText("Coins: " + coinCount);

            PurchaseRequest request = new PurchaseRequest(user, item, quantity);
            Log.d("PurchaseRequest", "Request: " + request.toString());


            Call<Void> call = apiService.purchase(request);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("PurchaseResponse", "Response received");
                    if (response.isSuccessful()) {
                        Toast.makeText(TiendaActivity.this, "Item purchased successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("PurchaseError", "Error code: " + response.code() + ", message: " + response.message());
                        Toast.makeText(TiendaActivity.this, "Failed to purchase item", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("PurchaseFailure", "Error: " + t.getMessage());
                    Toast.makeText(TiendaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(TiendaActivity.this, "Not enough coins", Toast.LENGTH_SHORT).show();
        }
    }
}