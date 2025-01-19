package edu.upc.projecte;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private ImageButton buttonProfile;
    private ImageButton buttonPlay;
    private ApiService apiService;
    private Button buttonLogout;
    private ImageButton denunciasButton;
    private ImageButton buttonTienda;
    private ImageButton buttonInventario;
    private ImageButton buttonSubmitQuestion;

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
        apiService = RetrofitClient.getClient().create(ApiService.class);
        buttonTienda = findViewById(R.id.btTienda);

        buttonTienda.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, TiendaActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
            startActivity(intent);
        });

        buttonInventario = findViewById(R.id.btInventario);
        buttonInventario.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, InventarioActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
            startActivity(intent);
        });

        buttonSubmitQuestion = findViewById(R.id.btSubmitQuestion);
        buttonSubmitQuestion.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, SubmitQuestionActivity.class);
            intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
            startActivity(intent);
        });

        buttonPlay = findViewById(R.id.btJugar);
        buttonPlay.setOnClickListener(v -> {
            String username = getUsername(); // Obtén el nombre de usuario
            Partida partida = new Partida(
                    100, "Normal", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                    1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,
                    2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f
            );

            Call<ResponseBody> call = apiService.getGame(username);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String jsonPartida = response.body().string();
                            if (jsonPartida != null && !jsonPartida.isEmpty() && !jsonPartida.equals("null")) {
                                // Si la partida existe, inicia la actividad con la partida cargada
                                Intent intent = new Intent(MenuActivity.this, CustomUnityPlayerGameActivity.class);
                                intent.putExtra("partida_json", jsonPartida);
                                startActivity(intent);
                            } else {
                                // Si no hay partida, muestra el diálogo para seleccionar nivel
                                showLevelSelectionDialog();
                                Log.e("MenuActivity", "No hay partida para el usuario: " + username);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MenuActivity.this, "Error al leer la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error en la respuesta del servidor
                        Toast.makeText(MenuActivity.this, "Error al obtener la partida: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("MenuActivity", "Error al obtener la partida: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Error en la conexión o en la llamada
                    Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error de conexión", t);
                }
            });
        });

        buttonProfile = findViewById(R.id.button_profile);
        if (isUserLoggedIn()) {
            buttonProfile.setVisibility(View.VISIBLE);
        }

        buttonProfile.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        buttonLogout = findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // Clear all saved data
            editor.apply();

            // Add logic to log out, such as clearing shared preferences
            finish();
        });

        denunciasButton = findViewById(R.id.btDenuncia);
        denunciasButton.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, DenunciaActivity.class);
            startActivity(intent);
        });
    }

    private boolean isUserLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "ara");
    }

    private void showLevelSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        builder.setTitle("No tienes ninguna partida iniciada")
                .setMessage("Selecciona el nivel que quieres jugar:");

        // Crear un ArrayAdapter para los niveles
        String[] levels = {"Nivel 1", "Nivel 2", "Nivel 3"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, levels);

        // Configurar un ListView en el diálogo
        ListView listView = new ListView(this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedLevel = levels[position];
            Toast.makeText(MenuActivity.this, "Has seleccionado " + selectedLevel, Toast.LENGTH_SHORT).show();
            String solicitud;
            if (selectedLevel.equals("Nivel 1")) {
                solicitud = "Solicitud_de_partida_001";
            } else if (selectedLevel.equals("Nivel 2")) {
                solicitud = "Solicitud_de_partida_002";
            } else {
                solicitud = "Solicitud_de_partida_003";
            }
            Call<ResponseBody> call = apiService.getGame(solicitud);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        try {
                            String jsonPartida = response.body().string();
                            if (jsonPartida != null && !jsonPartida.isEmpty()) {
                                iniciarPartida(jsonPartida);
                            } else {
                                // Si no hay partida, muestra el diálogo para seleccionar nivel
                                Log.e("MenuActivity", "No hay partida para el usuario: ");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(MenuActivity.this, "Error al leer la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Error en la respuesta del servidor
                        Toast.makeText(MenuActivity.this, "Error al obtener la partida: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.e("MenuActivity", "Error al obtener la partida: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    // Error en la conexión o en la llamada
                    Toast.makeText(MenuActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("API", "Error de conexión", t);
                }
            });
        });

        // Añadir el ListView al diálogo
        builder.setView(listView);

        // Botón adicional en la parte inferior
        builder.setNeutralButton("Cancelar", (dialog, which) -> dialog.dismiss());

        // Mostrar el diálogo
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void iniciarPartida(String jsonPartida) {
        Intent intent = new Intent(MenuActivity.this, CustomUnityPlayerGameActivity.class);
        intent.putExtra("partida_json", jsonPartida);
        startActivity(intent);
    }
}