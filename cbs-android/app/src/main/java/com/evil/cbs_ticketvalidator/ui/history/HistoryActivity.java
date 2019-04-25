package com.evil.cbs_ticketvalidator.ui.history;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.data.model.TripAttendAttempt;
import com.evil.cbs_ticketvalidator.data.model.TripAttendAttemptHistory;
import com.evil.cbs_ticketvalidator.data.model.ValidatorVerdict;
import com.evil.cbs_ticketvalidator.service.AttendAttemptHistoryService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;
import com.evil.cbs_ticketvalidator.ui.main.MainActivity;


import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
public class HistoryActivity extends AppCompatActivity {

    private AttendAttemptHistoryService attendAttemptHistoryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        String encryptedOrderId = getIntent().getStringExtra("encryptedOrderId");
        attendAttemptHistoryService = ServiceGenerator.createServiceWithInterceptor(AttendAttemptHistoryService.class, HistoryActivity.this);
        attendAttemptHistoryService.getAttendAttemptHistoryFor(encryptedOrderId)
                .enqueue(new Callback<TripAttendAttemptHistory>() {
                    @Override
                    public void onResponse(Call<TripAttendAttemptHistory> call, Response<TripAttendAttemptHistory> response) {
                        if (response.isSuccessful()) {
                            log.info("\n--> TripAttendAttemptHistory {}", response.body());
                            Toast.makeText(HistoryActivity.this, "TripAttendAttemptHistory received!", Toast.LENGTH_LONG).show();
                            if (response.body() != null)
                                setUpActivity(response.body().getAttempts());
                            else
                                setUpActivity(new ArrayList<>());
                        }
                    }

                    @Override
                    public void onFailure(Call<TripAttendAttemptHistory> call, Throwable t) {
                        log.error("\n--> Error {}", t);
                    }
                });
    }

    private void setUpActivity(List<TripAttendAttempt> attempts) {
        HistoryListViewAdapter adapter = new HistoryListViewAdapter(HistoryActivity.this, attempts);
        getHistoryListView().setAdapter(adapter);
    }

    private ListView getHistoryListView() {
        return (ListView) findViewById(R.id.history_list_view);
    }
}
