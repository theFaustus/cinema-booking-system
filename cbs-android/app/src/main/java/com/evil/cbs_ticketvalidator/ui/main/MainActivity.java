package com.evil.cbs_ticketvalidator.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.data.model.TripAttendRequest;
import com.evil.cbs_ticketvalidator.data.model.ValidatorVerdict;
import com.evil.cbs_ticketvalidator.service.AttendAttemptHistoryService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;
import com.evil.cbs_ticketvalidator.service.ValidatorService;
import com.evil.cbs_ticketvalidator.ui.history.HistoryActivity;
import com.evil.cbs_ticketvalidator.ui.info.InfoActivity;
import com.evil.cbs_ticketvalidator.ui.login.LoginActivity;
import com.evil.cbs_ticketvalidator.ui.scan.QrCodeScanningActivity;
import com.evil.cbs_ticketvalidator.util.ErrorDialogPresenter;
import com.evil.cbs_ticketvalidator.util.NetworkChangeReceiver;

import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AttendAttemptHistoryService attendAttemptHistoryService;
    private ValidatorService validatorService;
    private NetworkChangeReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mNetworkReceiver = new NetworkChangeReceiver();
        registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        validatorService = ServiceGenerator.createServiceWithInterceptor(ValidatorService.class, MainActivity.this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            toHistoryActivity(null);
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        String username = (String) getIntent().getExtras().get("username");

        TextView usernameTextView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.username);
        usernameTextView.setText(username);

        String token = getSharedPreferences("session", MODE_PRIVATE).getString("token", "");
        String type = getSharedPreferences("session", MODE_PRIVATE).getString("type", "");
        log.info("\n--> Token : {}", token);
        log.info("\n--> Type : {}", type);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == QrCodeScanningActivity.SCAN_QR_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String encryptedOrderId = data.getStringExtra(QrCodeScanningActivity.SCANNED_QR_CODE);

                validatorService.validate(new TripAttendRequest(encryptedOrderId))
                        .enqueue(new Callback<ValidatorVerdict>() {
                            @Override
                            public void onResponse(Call<ValidatorVerdict> call, Response<ValidatorVerdict> response) {
                                if (response.isSuccessful()) {
                                    log.info("\n--> ValidatorVerdict {}", response.body());
                                    Toast.makeText(MainActivity.this, response.body().getEncryptedOrderId() + " - Allowed to enter: " + response.body().isAllowedToEnter(), Toast.LENGTH_SHORT).show();
                                    new ValidatorVerdictDialogPresenter(MainActivity.this, response.body()).showDialog();
                                }
                            }

                            @Override
                            public void onFailure(Call<ValidatorVerdict> call, Throwable t) {
                                log.info("\n--> Error {}", t);
                                new ErrorDialogPresenter(MainActivity.this, "Ooops, error!", t.getMessage()).showDialog();

                            }
                        });
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_scan) {
            toScanningTicket();
        } else if (id == R.id.nav_info) {
            toInformation();
        } else if (id == R.id.nav_list) {
            toHistoryActivityView(null);
        } else if (id == R.id.nav_signout) {
            toSignout();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkReceiver);
    }

    private interface DialogInputListener {
        void onClick(String encryptedOrderId);
    }

    private void showOrderInputDialog(String title, DialogInputListener okAction) {
        EditText orderIdEditText = new EditText(MainActivity.this);
        orderIdEditText.setPadding(60, 60, 60, 60);
        orderIdEditText.setHint("Order id:");
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setView(orderIdEditText)
                .setCancelable(false)
                .setPositiveButton("Check", (dialogInterface, i) -> okAction.onClick(orderIdEditText.getText().toString()))
                .setNegativeButton("Cancel", (dialogInterface, i) -> {
                })
                .show();
    }

    public void toHistoryActivity(String encryptedOrderId) {
        if (encryptedOrderId == null) {
            showOrderInputDialog("View entry attempts for ticket", id -> {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                intent.putExtra("encryptedOrderId", id);
                startActivity(intent);
            });
        } else {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("encryptedOrderId", encryptedOrderId);
            startActivity(intent);
        }
    }

    public void toHistoryActivityView(View view) {
        toHistoryActivity(null);
    }


    public void toScanningTicket() {
        Intent intent = new Intent(MainActivity.this, QrCodeScanningActivity.class);
        startActivityForResult(intent, QrCodeScanningActivity.SCAN_QR_CODE);
    }

    public void toInformation() {
        startActivity(new Intent(this, InfoActivity.class));
    }

    public void toSignout() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void toScanningTicketView(View view) {
        Intent intent = new Intent(MainActivity.this, QrCodeScanningActivity.class);
        startActivityForResult(intent, QrCodeScanningActivity.SCAN_QR_CODE);
    }


    private class ValidatorVerdictDialogPresenter {
        private final Context context;
        private final ValidatorVerdict verdict;

        private ValidatorVerdictDialogPresenter(Context context, ValidatorVerdict verdict) {
            this.context = context;
            this.verdict = verdict;
        }

        public void showDialog() {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
            int messageId = verdict.isAllowedToEnter() ? R.string.ticketIsAllowed : R.string.ticketIsNotAllowed;
            int iconId = verdict.isAllowedToEnter() ? R.drawable.ic_thumb_up_black_24dp :
                    R.drawable.ic_thumb_down_black_24dp;
            android.app.AlertDialog.Builder dialog = builder.setMessage(context.getResources().getString(messageId))
                    .setIcon(iconId)
                    .setTitle("Validator verdict")
                    .setCancelable(false)
                    .setPositiveButton("OK", (d, which) -> {
                        d.dismiss();
                    });
            if (!verdict.isAllowedToEnter())
                dialog.setNegativeButton("View entry attempts", (d, which) -> {
                    d.dismiss();
                    toHistoryActivity(verdict.getEncryptedOrderId());
                });
            dialog.show();
        }
    }


}
