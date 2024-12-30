package edu.upc.projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.widget.ProgressBar;
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

public class MainActivity extends AppCompatActivity {

    private Button login_button;
    private Button register_button;
    //private Button PlayButton;
    private Button denuncias_button;
    private ApiService apiService;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar progressBar;

    private String username;

    static {
        System.loadLibrary("game"); // Nombre sin "lib" y sin extensión ".so"
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (isUserLoggedIn()) {
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
            return;
        }

        login_button = findViewById(R.id.loginButton);
        register_button = findViewById(R.id.registerButton);
        usernameEditText = findViewById(R.id.usernameText);
        passwordEditText = findViewById(R.id.passwordText);
        progressBar = findViewById(R.id.progressBar);
        denuncias_button = findViewById(R.id.button);
        apiService = RetrofitClient.getClient().create(ApiService.class);
       //PlayButton = findViewById(R.id.button2);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        //PlayButton.setOnClickListener(new View.OnClickListener() {
        //@Override
        //public void onClick(View v) {
        //    Intent intent = new Intent(MainActivity.this, com.unity3d.player.UnityPlayerGameActivity.class);
        //    startActivity(intent);
        //    }
        //});
        // Encuentra el botón en el layout

        Button startUnityButton = findViewById(R.id.button2);

        startUnityButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, com.unity3d.player.UnityPlayerGameActivity.class); // Cambia a GameActivity si lo necesitas
            startActivity(intent);
        });

        login_button.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "El nom d'usuari i la contrasenya són obligatoris", Toast.LENGTH_SHORT).show();
                return;
            }

            User user = new User(username, password);
            loginUser(user);
        });

        denuncias_button.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DenunciaActivity.class);
            startActivity(intent);
        });


    }

    private void loginUser(User user) {
        progressBar.setVisibility(View.VISIBLE);

        Call<Void> call = apiService.loginUser(user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    saveUserLoggedIn();
                    setUsername(user.getUsername()); // Llamar a setUsername con el nombre de usuario
                    Intent intent = new Intent(MainActivity.this, MenuActivity.class);
                    intent.putExtra("username", user.getUsername()); // Pasar el nombre de usuario
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Error d'inici de sessió: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de connexió", t);
            }
        });
    }

    private void saveUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
}