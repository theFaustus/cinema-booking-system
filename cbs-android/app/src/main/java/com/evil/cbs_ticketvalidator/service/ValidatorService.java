package com.evil.cbs_ticketvalidator.service;

import com.evil.cbs_ticketvalidator.data.model.TripAttendRequest;
import com.evil.cbs_ticketvalidator.data.model.User;
import com.evil.cbs_ticketvalidator.data.model.ValidatorVerdict;
import com.evil.cbs_ticketvalidator.data.security.JwtResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface ValidatorService {
    @POST("validation")
    Call<ValidatorVerdict> validate(@Body TripAttendRequest request);
}
