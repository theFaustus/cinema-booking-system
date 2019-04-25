package com.evil.cbs_ticketvalidator.ui.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
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
import android.widget.EditText;
import android.widget.TextView;

import com.evil.cbs_ticketvalidator.R;
import com.evil.cbs_ticketvalidator.service.AttendAttemptHistoryService;
import com.evil.cbs_ticketvalidator.service.ServiceGenerator;
import com.evil.cbs_ticketvalidator.service.ValidatorService;
import com.evil.cbs_ticketvalidator.ui.history.HistoryActivity;
import com.evil.cbs_ticketvalidator.util.NetworkChangeReceiver;

import lombok.extern.slf4j.Slf4j;

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
            toHistoryActivity();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkReceiver);
    }

    private interface DialogInputListener {
        void onClick(String encryptedOrderId);
    }

    private void toHistoryActivity() {
        showOrderInputDialog("View entry attempts for ticket", encryptedOrderId -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            intent.putExtra("encryptedOrderId", encryptedOrderId);
            startActivity(intent);
        });
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
                .setNegativeButton("Cancel", (dialogInterface, i) -> {})
                .show();
    }
}
