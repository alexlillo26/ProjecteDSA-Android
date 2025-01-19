package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InventarioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView itemDescription;
    private Button buttonLogout;
    private ApiService apiService;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventario);

        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progressBar);
        itemDescription = findViewById(R.id.item_description);
        buttonLogout = findViewById(R.id.button_logout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        apiService = RetrofitClient.getClient().create(ApiService.class);
        username = getIntent().getStringExtra("username");

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventarioActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

        loadUserInventory();
    }

    private void loadUserInventory() {
        progressBar.setVisibility(View.VISIBLE);
        Call<List<Item>> call = apiService.getUserInventory(username);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    List<Item> items = response.body();
                    InventarioItemAdapter adapter = new InventarioItemAdapter(items, InventarioActivity.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(InventarioActivity.this, "No inventory found for user", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(InventarioActivity.this, "Error loading inventory", Toast.LENGTH_SHORT).show();
            }
        });
    }
}