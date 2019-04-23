package com.evil.cbs_ticketvalidator.service;

import com.evil.cbs_ticketvalidator.data.JwtResponse;
import com.evil.cbs_ticketvalidator.data.model.LogInUserDTO;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginService {
    @POST("auth/sign-in")
    Call<JwtResponse> login(@Body LogInUserDTO logInUserDTO);

    @POST("auth/sign-in")
    Observable<JwtResponse> loginObservable(@Body LogInUserDTO logInUserDTO);
}
