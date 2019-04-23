package com.evil.cbs_ticketvalidator.service;

import com.evil.cbs_ticketvalidator.data.security.JwtResponse;
import com.evil.cbs_ticketvalidator.data.model.UserDTO;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginService {
    @POST("auth/sign-in")
    Call<JwtResponse> login(@Body UserDTO userDTO);

    @POST("auth/sign-in")
    Observable<JwtResponse> loginObservable(@Body UserDTO userDTO);
}
