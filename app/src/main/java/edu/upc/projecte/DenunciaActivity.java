package edu.upc.projecte;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Call;
import retrofit2.Response;

public class DenunciaActivity extends AppCompatActivity {

    private ApiService apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_denuncia);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        EditText tituloText = findViewById(R.id.tituloText);
        EditText senderText = findViewById(R.id.senderText);
        EditText denunciaText = findViewById(R.id.denunciaText);
        Button enviarDenunciaButton = findViewById(R.id.enviarDenunciaButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        Button buttonBack = findViewById(R.id.backButton);
        buttonBack.setOnClickListener(v -> {
            Intent intent = new Intent(DenunciaActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });


        enviarDenunciaButton.setOnClickListener(v -> {
            String titulo = tituloText.getText().toString().trim();
            String remitente = senderText.getText().toString().trim();
            String denuncia = denunciaText.getText().toString().trim();

            if (titulo.isEmpty() || remitente.isEmpty() || denuncia.isEmpty()) {
                Toast.makeText(DenunciaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Denuncia nuevaDenuncia = new Denuncia(titulo, remitente, denuncia);
            enviarDenuncia(nuevaDenuncia);
        });
    }

    private void enviarDenuncia(Denuncia denuncia) {
        Call<Void> call = apiService.enviarDenuncia(denuncia);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DenunciaActivity.this, "Denuncia enviada con éxito", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DenunciaActivity.this, "Error al enviar la denuncia: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(DenunciaActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("API", "Error de conexión", t);
            }
        });
    }

}