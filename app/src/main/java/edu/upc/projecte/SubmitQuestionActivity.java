package edu.upc.projecte;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_question);

        EditText titleEditText = findViewById(R.id.titleEditText);
        EditText messageEditText = findViewById(R.id.messageEditText);
        EditText senderEditText = findViewById(R.id.senderEditText);
        Button submitButton = findViewById(R.id.submitButton);
        Button backButton = findViewById(R.id.backButton);

        submitButton.setOnClickListener(v -> {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String title = titleEditText.getText().toString();
            String message = messageEditText.getText().toString();
            String sender = senderEditText.getText().toString();

            if (sender.isEmpty()) {
                Toast.makeText(SubmitQuestionActivity.this, "Sender is required", Toast.LENGTH_SHORT).show();
                return;
            }

            Question question = new Question(date, title, message, sender);

            ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
            Call<ResponseBody> call = apiService.submitQuestion(question);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(SubmitQuestionActivity.this, "Question submitted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SubmitQuestionActivity.this, "Failed to submit question", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(SubmitQuestionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(SubmitQuestionActivity.this, MenuActivity.class);
            startActivity(intent);
        });
    }
}