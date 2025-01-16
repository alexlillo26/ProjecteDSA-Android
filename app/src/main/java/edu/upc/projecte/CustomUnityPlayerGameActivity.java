package edu.upc.projecte;

import com.google.gson.Gson;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerGameActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import edu.upc.projecte.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CustomUnityPlayerGameActivity extends UnityPlayerGameActivity {
    private ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
    private static final String TAG = "CustomUnityActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "CustomUnityPlayerGameActivity creada");

        // Verificar si el Intent contiene el JSON
        String jsonPartida = getIntent().getStringExtra("partida_json");
        if (jsonPartida != null) {
            try {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    UnityPlayer.UnitySendMessage("SaveGameManager", "RecibirPartidaDesdeAndroid", jsonPartida);
                }, 1000);
            } catch (Exception e) {
                Log.e("UnityIntegration", "Error al enviar mensaje a Unity", e);
            }
        }
    }


    // Método llamado desde Unity
    public void onGameExit(String message) {
        Log.d(TAG, "Mensaje recibido de Unity: " + message);

        // Regresar a la actividad principal
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        Partida partida = gsonpartida(message);
        Call<Void> call = apiService.saveGame(message);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Partida guardada exitosamente en el servidor");
                } else {
                    Log.e(TAG, "Error al guardar la partida: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Error de conexión al guardar la partida", t);
            }
        });
        startActivity(intent);


    }


    public Partida gsonpartida(String jsonPartida) {
        Gson gson = new Gson();
        Partida partida = gson.fromJson(jsonPartida, Partida.class);
        return partida;
    }
}
