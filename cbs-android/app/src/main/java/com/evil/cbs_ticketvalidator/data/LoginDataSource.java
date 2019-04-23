package com.evil.cbs_ticketvalidator.data;

import android.widget.Toast;

import com.evil.cbs_ticketvalidator.data.model.LoggedInUser;
import com.evil.cbs_ticketvalidator.data.model.LogInUserDTO;
import com.evil.cbs_ticketvalidator.service.LoginService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;
import com.evil.cbs_ticketvalidator.ui.login.LoginActivity;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Slf4j
public class LoginDataSource {

    private JwtResponse jwtResponse;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            LoginService loginService = ServiceGenerator.createService(LoginService.class);
            LogInUserDTO logInUserDTO = new LogInUserDTO(username, password);

            loginService.login(logInUserDTO).enqueue(new Callback<JwtResponse>() {
                @Override
                public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                    if (response.isSuccessful()) {
                        jwtResponse = response.body();
                        log.info("Response {}", response.body());
                        log.info("JwtResponse {}", jwtResponse);
                    }
                }

                @Override
                public void onFailure(Call<JwtResponse> call, Throwable t) {
                    log.error("Error {}", t);
                }
            });

            new Thread(() -> {
                try {
                    loginService.login(logInUserDTO).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            Disposable subscribe = loginService.loginObservable(logInUserDTO)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> jwtResponse = r);


            LoggedInUser loggedInUser = new LoggedInUser(username, username);
            return new Result.Success<>(loggedInUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
