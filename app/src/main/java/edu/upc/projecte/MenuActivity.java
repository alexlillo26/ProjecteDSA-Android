package edu.upc.projecte;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.SharedPreferences;
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
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {

    private Button buttonProfile;
<<<<<<< HEAD
    private Button buttonPlay;
    private ApiService apiService;
=======
    private Button buttonLogout;
    private Button denuncias_button;
    private Button buttonTienda;
    private Button buttonInventario;
    private Button buttonSubmitQuestion;
>>>>>>> a05c05ff981aa866a9b46b947a7decf8b4ebfded

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
<<<<<<< HEAD
        apiService = RetrofitClient.getClient().create(ApiService.class);
        Button buttonTienda = findViewById(R.id.btTienda);
=======

        buttonTienda = findViewById(R.id.btTienda);
>>>>>>> a05c05ff981aa866a9b46b947a7decf8b4ebfded
        buttonTienda.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, TiendaActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
                startActivity(intent);
            }
        });

        buttonInventario = findViewById(R.id.btInventario);
        buttonInventario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, InventarioActivity.class);
                startActivity(intent);
            }
        });

        buttonSubmitQuestion = findViewById(R.id.btSubmitQuestion);
        buttonSubmitQuestion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, SubmitQuestionActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username")); // Pass the username to the next activity
                startActivity(intent);
            }


        });
        buttonPlay = findViewById(R.id.btPlay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                if (jsonPartida != null && !jsonPartida.isEmpty()) {
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
            }
        });


                    // Crear un Intent para iniciar Unity
//                    Intent intent = new Intent(MenuActivity.this, CustomUnityPlayerGameActivity.class);
//                    intent.putExtra("partida_json", "{\n" +
//                            "    \"playerPosX\": 11.130021095275879,\n" +
//                            "    \"playerPosY\": 20.1399223804473879,\n" +
//                            "    \"playerPosZ\": -1.0,\n" +
//                            "    \"Playerstats\": [\n" +
//                            "        4.0,\n" +
//                            "        5.0,\n" +
//                            "        5.0\n" +
//                            "    ],\n" +
//                            "    \"respawnPosX\": 37.63999938964844,\n" +
//                            "    \"respawnPosY\": -2.109999895095825,\n" +
//                            "    \"respawnPosZ\": -1.0199999809265137,\n" +
//                            "    \"coinsCount\": 20,\n" +
//                            "    \"dificulty\": \"\",\n" +
//                            "    \"JumpPotions\": 2.0,\n" +
//                            "    \"SpeedPotions\": 3.0,\n" +
//                            "    \"MaxHealthPotions\": 4.0,\n" +
//                            "    \"AttackSpeedPotions\": 5.0,\n" +
//                            "    \"velocity\": 5.0,\n" +
//                            "    \"jumpForce\": 7.0,\n" +
//                            "    \"attackSpeed\": 1.0,\n" +
//                            "    \"MapItems\": [],\n" +
//                            "    \"enemies\": []\n" +
//                            "}");
//
//                    // Iniciar Unity
//                    startActivity(intent);


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

        buttonLogout = findViewById(R.id.button_logout);
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

        denuncias_button = findViewById(R.id.btDenuncia);
        denuncias_button.setOnClickListener(v -> {
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
        return sharedPreferences.getString("username", "sergi");
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
            if (selectedLevel.equals("Nivel 1"))
            {
                solicitud = "Solicitud_de_partida_001";
            }
            else if (selectedLevel.equals("Nivel 2"))
            {
                solicitud = "Solicitud_de_partida_002";
            }
            else
            {
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