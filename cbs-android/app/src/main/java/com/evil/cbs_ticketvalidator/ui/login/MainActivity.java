package com.evil.cbs_ticketvalidator.ui.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.data.model.TripAttendAttemptHistory;
import com.evil.cbs_ticketvalidator.data.model.TripAttendRequest;
import com.evil.cbs_ticketvalidator.data.model.ValidatorVerdict;
import com.evil.cbs_ticketvalidator.service.AttendAttemptHistoryService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;
import com.evil.cbs_ticketvalidator.service.ValidatorService;

import org.json.JSONObject;

import lombok.extern.slf4j.Slf4j;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Slf4j
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AttendAttemptHistoryService attendAttemptHistoryService;
    private ValidatorService validatorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        attendAttemptHistoryService = ServiceGenerator.createServiceWithInterceptor(AttendAttemptHistoryService.class, MainActivity.this);
        validatorService = ServiceGenerator.createServiceWithInterceptor(ValidatorService.class, MainActivity.this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //TODO remove these from here
            attendAttemptHistoryService.getAttendAttemptHistoryFor("085806C9A4DD313F")
                    .enqueue(new Callback<TripAttendAttemptHistory>() {
                        @Override
                        public void onResponse(Call<TripAttendAttemptHistory> call, Response<TripAttendAttemptHistory> response) {
                            log.info("\n--> TripAttendAttemptHistory {}", response.body());
                            Snackbar.make(view, "TripAttendAttemptHistory received!", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onFailure(Call<TripAttendAttemptHistory> call, Throwable t) {
                            log.error("\n--> Error {}", t);
                        }
                    });

            validatorService.validate(new TripAttendRequest("085806C9A4DD313F"))
                    .enqueue(new Callback<ValidatorVerdict>() {
                        @Override
                        public void onResponse(Call<ValidatorVerdict> call, Response<ValidatorVerdict> response) {
                            log.info("\n--> ValidatorVerdict {}", response.body());
                            Snackbar.make(view, response.body().getEncryptedOrderId() + " " + response.body().isAllowedToEnter(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        @Override
                        public void onFailure(Call<ValidatorVerdict> call, Throwable t) {
                            log.info("\n--> Error {}", t);
                        }
                    });
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
