package edu.upc.projecte;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.Toast;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameRegText;
    private EditText passwordRegText;
    private EditText confirmPasswordRegText;
    private Button registerButton;
    private Button cancelButton;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        usernameRegText = findViewById(R.id.usernameText);
        passwordRegText = findViewById(R.id.passwordText);
        confirmPasswordRegText = findViewById(R.id.confirmPasswordText);
        registerButton = findViewById(R.id.registerButton);
        cancelButton = findViewById(R.id.cancelButton);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        registerButton.setOnClickListener(v -> {
            String username = usernameRegText.getText().toString().trim();
            String password = passwordRegText.getText().toString().trim();
            String confirmPassword = confirmPasswordRegText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "El nom d'usuari i la contrasenya són obligatoris", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Les contrasenyes no coincideixen", Toast.LENGTH_SHORT).show();
                return;
            }

            checkUsernameAvailability(username);
            User user = new User(username, password);
            registerUser(user);
        });

        cancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void checkUsernameAvailability(String username) {
        Call<User> call = apiService.getUserProfile(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "El nom d'usuari ja està en ús. Si us plau, tria un altre.", Toast.LENGTH_SHORT).show();
                } else {
                    User user = new User(username, passwordRegText.getText().toString().trim());
                    registerUser(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de connexió", t);
            }
        });
    }

    private void registerUser(User user) {
        Call<Void> call = apiService.registerUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registre correcte!", Toast.LENGTH_SHORT).show();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Cierra la actividad de registro para que no se pueda volver con el botón de atrás
                    }, 1000); // Espera 1 segundos
                } else {
                    Toast.makeText(RegisterActivity.this, "Error de registre " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de connexió", t);
            }
        });
    }
}