package com.evil.cbs_ticketvalidator.ui.login;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.data.security.JwtResponse;
import com.evil.cbs_ticketvalidator.data.model.User;
import com.evil.cbs_ticketvalidator.service.LoginService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA_PERMISION_OPEN_CAMERA = 200;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISION_OPEN_CAMERA = 300;


    private JwtResponse jwtResponse;
    private ProgressBar loadingProgressBar;
    private Button loginButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.CAMERA)}, REQUEST_CAMERA_PERMISION_OPEN_CAMERA);
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{(Manifest.permission.WRITE_EXTERNAL_STORAGE)}, REQUEST_WRITE_EXTERNAL_STORAGE_PERMISION_OPEN_CAMERA);
        }
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        loadingProgressBar = findViewById(R.id.loading);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(LoginActivity.this);
                usernameEditText.clearFocus();
                passwordEditText.clearFocus();
                loadingProgressBar.setVisibility(View.VISIBLE);
                login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });
    }

    private void hideKeyboard(Activity activity) {
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void login(String username, String password) {

        LoginService loginService = ServiceGenerator.createService(LoginService.class);
        User user = new User(username, password);

        loginService.login(user).enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if (response.isSuccessful()) {
                    jwtResponse = response.body();
                    log.info("\n--> Response {}", response.body());
                    log.info("\n--> JwtResponse {}", jwtResponse);
                    getSharedPreferences("session", MODE_PRIVATE)
                            .edit()
                            .putString("token", jwtResponse.getToken())
                            .putString("type", jwtResponse.getType())
                            .putString("username", jwtResponse.getUsername())
                            .apply();
                    showSuccessMessage(username);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                } else {
                    showLoginFailed("Failed to log in");
                    loadingProgressBar.setVisibility(View.INVISIBLE);
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {
                showLoginFailed("Error when log in : " + t.getMessage());
                log.error("\n--> Error {}", t);
                loadingProgressBar.setVisibility(View.INVISIBLE);
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );
            }
        });
    }

    private void showSuccessMessage(String username) {
        Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
