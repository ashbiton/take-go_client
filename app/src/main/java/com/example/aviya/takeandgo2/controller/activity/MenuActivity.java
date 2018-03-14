package com.example.aviya.takeandgo2.controller.activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.aviya.takeandgo2.R;
import com.example.aviya.takeandgo2.controller.ReservationUpdateReceiver;
import com.example.aviya.takeandgo2.controller.ReservationUpdateService;
import com.example.aviya.takeandgo2.controller.fragment.AboutUsFragment;
import com.example.aviya.takeandgo2.controller.fragment.BranchesFragment;
import com.example.aviya.takeandgo2.controller.fragment.FreeCarsFragment;
import com.example.aviya.takeandgo2.controller.fragment.StartFragment;
import com.example.aviya.takeandgo2.controller.fragment.YourCarFragment;
import com.example.aviya.takeandgo2.model.datasource.List_DB_manager;
import com.example.aviya.takeandgo2.model.datasource.getManager;
import com.example.aviya.takeandgo2.model.entities.Client;
import com.example.aviya.takeandgo2.model.entities.Reservation;
import com.example.aviya.takeandgo2.model.entities.UserClient;


public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , FreeCarsFragment.OnCarOrderedListener , YourCarFragment.OnReservationClosedListener{

    Client client;
    UserClient user;
    Reservation reservation;
    List_DB_manager manager = getManager.getInstance();
    ReservationUpdateReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        receiver = new ReservationUpdateReceiver();
        IntentFilter filter = new IntentFilter("CAR_CHANGE");
        registerReceiver(receiver,filter);
        startService(new Intent(this, ReservationUpdateService.class));

        setTitle("Menu");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Have a happy day", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        updateViewByClient();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void updateViewByClient() {
        try{
            Intent intent = getIntent();
            int id = intent.getIntExtra("ID",0);
            if(id == 0)
                return;
            NavigationView navigation = (NavigationView) findViewById(R.id.nav_view);
            View view = navigation.getHeaderView(0);
            TextView userDataViewMenu = (TextView) view.findViewById(R.id.userDataViewMenu);
            TextView userEmailViewMenu = (TextView) view.findViewById(R.id.userEmailViewMenu);
            user = manager.findUser(id);
            if(user == null)
                return;
            client = manager.findClientByUser(user.getUserName());
            reservation = manager.openReservationForClient(id);
            if (client == null)
                return;
            String firstName = manager.toProperFormat(client.getName());
            String lastName = manager.toProperFormat(client.getLastName());
            String fullName = firstName+" "+lastName;
            userDataViewMenu.setText(fullName);
            userEmailViewMenu.setText(client.getEmailAddress().toLowerCase());

            StartFragment fragment = new StartFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_menu,fragment).commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
        if (id == R.id.nav_free_cars) {
            freeCarsFragment();
        } else if (id == R.id.nav_branches) {
            branchesFragment();
        } else if (id == R.id.nav_client_car) {
            yourCarFragment();
        } else if (id == R.id.nav_company_info) {
            aboutCompanyFragment();
        } else if (id == R.id.nav_log_out) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void branchesFragment() {
        BranchesFragment fragment = new BranchesFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_menu,fragment).commit();
    }

    private void yourCarFragment() {
        YourCarFragment fragment = new YourCarFragment();
        Bundle args = new Bundle();
        if(reservation != null)
            args.putInt("RESERVATION",reservation.getReservationNumber());
        else
            args.putInt("RESERVATION",-1);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_menu,fragment).commit();
        //reservation = manager.openReservationForClient(client.get_id());//if the reservation was closed, its updated here;
    }

    private void freeCarsFragment() {
        FreeCarsFragment fragment = new FreeCarsFragment();
        Bundle args = new Bundle();
        args.putInt("ID",client.get_id());
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_menu,fragment).commit();

    }

    private void aboutCompanyFragment() {
        AboutUsFragment fragment = new AboutUsFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.content_menu,fragment).commit();
    }

    @Override
    public void newReservationForClient(Reservation reserv) {
        reservation = reserv;
    }

    @Override
    public void closeReservation() {
        reservation = null; yourCarFragment();
    }
}
