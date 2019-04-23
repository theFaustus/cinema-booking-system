package com.evil.cbs_ticketvalidator.service;

import com.evil.cbs_ticketvalidator.data.security.JwtResponse;
import com.evil.cbs_ticketvalidator.data.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginService {
    @POST("auth/sign-in")
    Call<JwtResponse> login(@Body User user);
}
