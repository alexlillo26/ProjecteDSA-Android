package edu.upc.projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileActivity extends AppCompatActivity {

    private TextInputEditText fullNameText, emailText, ageText;
    private TextView usernameText, passwordText;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);
        fullNameText = findViewById(R.id.fullNameText);
        emailText = findViewById(R.id.emailText);
        ageText = findViewById(R.id.ageText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        loadUserProfile();

        findViewById(R.id.button_save).setOnClickListener(v -> updateUserData());
        findViewById(R.id.button_back).setOnClickListener(v -> {
            startActivity(new Intent(ProfileActivity.this, MenuActivity.class));
            finish();
        });
    }

    private void loadUserProfile() {
        String username = getUsername();
        Call<User> call = apiService.getUserProfile(username);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User user = response.body();
                    usernameText.setText(user.getUsername());
                    passwordText.setText(user.getPassword());
                    fullNameText.setText(user.getFullName());
                    emailText.setText(user.getEmail());
                    ageText.setText(String.valueOf(user.getAge()));
                } else {
                    Toast.makeText(ProfileActivity.this, "Error, no se ha podido cargar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error, no se ha podido cargar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserData() {
        String username = usernameText.getText().toString();
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordText.getText().toString());
        user.setFullName(fullNameText.getText().toString());
        user.setEmail(emailText.getText().toString());
        user.setAge(Integer.parseInt(ageText.getText().toString()));
        user.setId(getUserId());

        Call<Void> call = apiService.updateUserProfile(username, user);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    saveUserData(user);
                    Toast.makeText(ProfileActivity.this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileActivity.this, "Error, no se ha podido actualizar el perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error, no se ha podido actualizar el perfil", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserData(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", user.getUsername());
        editor.putString("password", user.getPassword());
        editor.putString("fullName", user.getFullName());
        editor.putString("email", user.getEmail());
        editor.putInt("age", user.getAge());
        editor.putString("userId", user.getId());
        editor.apply();
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "defaultUsername");
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("userId", "defaultUserId");
    }
}