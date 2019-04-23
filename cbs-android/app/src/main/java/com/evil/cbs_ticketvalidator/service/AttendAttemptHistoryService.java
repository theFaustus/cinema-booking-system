package com.evil.cbs_ticketvalidator.service;

import com.evil.cbs_ticketvalidator.data.model.TripAttendAttemptHistory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AttendAttemptHistoryService {
    @GET("attend-attempt-history/{encryptedOrderId}")
    Call<TripAttendAttemptHistory> getAttendAttemptHistoryFor(@Path("encryptedOrderId") String encryptedOrderId);
}
