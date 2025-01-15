package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {

    private Button buttonProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonTienda = findViewById(R.id.btTienda);
        buttonTienda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TiendaActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
                startActivity(intent);
            }
        });

        Button buttonInventario = findViewById(R.id.btInventario);
        buttonInventario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InventarioActivity.class);
                startActivity(intent);
            }
        });

        Button buttonSubmitQuestion = findViewById(R.id.btSubmitQuestion);
        buttonSubmitQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SubmitQuestionActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
                startActivity(intent);
            }
        });

        buttonProfile = findViewById(R.id.button_profile);
        if (isUserLoggedIn()) {
            buttonProfile.setVisibility(View.VISIBLE);
        }

        buttonProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear(); // Clear all saved data
                editor.apply();

                // Add logic to log out, such as clearing shared preferences
                finish();
            }
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}