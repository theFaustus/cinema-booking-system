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

    private static class BouncerVerdictDialogPresenter {
        private final Context context;
        private final ValidatorVerdict verdict;

        private BouncerVerdictDialogPresenter(Context context, ValidatorVerdict verdict) {
            this.context = context;
            this.verdict = verdict;
        }

        public void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            int messageId = verdict.isAllowedToEnter() ? R.string.ticketIsAllowed : R.string.ticketIsNotAllowed;
            int iconId = verdict.isAllowedToEnter() ? R.drawable.ic_thumb_up_black_24dp :
                    R.drawable.ic_thumb_down_black_24dp;
            AlertDialog.Builder dialog = builder.setMessage(context.getResources().getString(messageId))
                    .setIcon(iconId)
                    .setTitle("Bouncer verdict")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            if (!verdict.isAllowedToEnter())
                dialog.setNegativeButton("View entry attempts", (dialog1, which) -> {
                    //TODO View entry attempts service call
                });
            dialog.show();
        }
    }

    private static class ErrorDialogPresenter {
        private final Context context;
        private final int titleResourceId;
        private final int messageResourceId;

        private ErrorDialogPresenter(Context context, int titleResourceId, int messageResourceId) {
            this.context = context;
            this.titleResourceId = titleResourceId;
            this.messageResourceId = messageResourceId;
        }

        public void showDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage(context.getResources().getString(messageResourceId))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(context.getResources().getString(titleResourceId))
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();
        }
    }
}
