package com.example.riceapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, lot_Selecter.OnMyFragmentListener {
    private static final String TAG = "home";
    private DrawerLayout drawer;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    Toolbar toolbar;
    Bundle bundle;
    TextView name;
    View headerView;
    ProgressBar progressBar;
    ImageView img_overlay;
    LoginHelper usrHelper;

    String tooltitle, go_to, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //*** Session Login
        usrHelper = new LoginHelper(this);

        fragmentManager = getSupportFragmentManager();
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        progressBar = findViewById(R.id.progressBar);
        img_overlay = findViewById(R.id.img_overlay);
        headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nav_t1);
        bundle = new Bundle();

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this, drawer, toolbar,
                R.string.navigation_draw_open, R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: setHomepage");
            Bundle extras = getIntent().getExtras();

            /*
            if (extras == null) {
                go_to = usrHelper.getRole();
            } else {
                go_to = extras.getString("from_fg");
            }
            */

            name.setText(usrHelper.getUserName());

            /*lot_Selecter fragment1 = new lot_Selecter();
            bundle.putString("from_fragment",go_to);
            fragment1.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment1).addToBackStack(null).commit();
            */
            checkRole();
            toolbar.setTitle(getResources().getString(R.string.menu1));
            Log.d(TAG, "onNavigationItemSelected: Username sent to Fragment1 : " + bundle);
        }

        Log.d(TAG, "onCreate: Username : " + usrHelper.getUserName());
    }

    public void checkRole() {
        role = usrHelper.getRole();
        switch (role) {
            case "0":
                lot_Selecter fragment1 = new lot_Selecter();
                bundle.putString("from_fragment", "1");
                fragment1.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack(null).commit();
                break;

            case "1":
                lot_Selecter fragment2 = new lot_Selecter();
                bundle.putString("from_fragment", "2");
                fragment2.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment2).addToBackStack(null).commit();
                break;

            case "2":
                lot_Selecter fragment3 = new lot_Selecter();
                bundle.putString("from_fragment", "3");
                fragment3.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment3).addToBackStack(null).commit();
                break;

            case "3":
                lot_Selecter fragment4 = new lot_Selecter();
                bundle.putString("from_fragment", "4");
                fragment4.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment4).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.menu_no1:
                if (!role.equals("0")) {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    lot_Selecter fragment1 = new lot_Selecter();
                    bundle.putString("from_fragment", "1");
                    fragment1.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack(null).commit();
                    Log.d(TAG, "onNavigationItemSelected: " + bundle);
                    tooltitle = menuItem.getTitle().toString();
                    toolbar.setTitle(tooltitle);
                }
                break;

            case R.id.menu_no2:
                if (!role.equals("1")) {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    lot_Selecter fragment2 = new lot_Selecter();
                    bundle.putString("from_fragment", "2");
                    fragment2.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment2).addToBackStack(null).commit();
                    Log.d(TAG, "onNavigationItemSelected: " + bundle);
                    tooltitle = menuItem.getTitle().toString();
                    toolbar.setTitle(tooltitle);
                }
                break;

            case R.id.menu_no3:
                if (!role.equals("2")) {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    lot_Selecter fragment3 = new lot_Selecter();
                    bundle.putString("from_fragment", "3");
                    fragment3.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment3).addToBackStack(null).commit();
                    Log.d(TAG, "onNavigationItemSelected: " + bundle);
                    tooltitle = menuItem.getTitle().toString();
                    toolbar.setTitle(tooltitle);
                }
                break;

            case R.id.menu_no4:
                if (!role.equals("3")) {
                    Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                } else {
                    lot_Selecter fragment4 = new lot_Selecter();
                    bundle.putString("from_fragment", "4");
                    fragment4.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment4).addToBackStack(null).commit();
                    Log.d(TAG, "onNavigationItemSelected: " + bundle);
                    tooltitle = menuItem.getTitle().toString();
                    toolbar.setTitle(tooltitle);
                }
                break;

            case R.id.logout_btn:
                img_overlay.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        usrHelper.deleteSession();
                        if (fragmentManager.getBackStackEntryCount() > 0) {
                            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                                fragmentManager.popBackStackImmediate();
                            }
                        }
                        Log.d(TAG, "onNavigationItemSelected: BackStack : " + fragmentManager.getBackStackEntryCount());
                        Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        progressBar.setVisibility(View.GONE);
                        img_overlay.setVisibility(View.GONE);
                        finish();
                    }
                }, 3000);
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        if (fragmentManager.getBackStackEntryCount() == 0) {
            //bundle.putString("username", username);
            lot_Selecter fragment1 = new lot_Selecter();
            bundle.putString("from_fragment", go_to);
            fragment1.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment1).addToBackStack("lot_no").commit();
            toolbar.setTitle(getResources().getString(R.string.menu1));
            Log.d(TAG, "onNavigationItemSelected: Username sent to Fragment1 : " + bundle);
        }
    }

    @Override
    public void onChangeToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
