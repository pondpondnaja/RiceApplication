package com.example.riceapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = "home";
    private DrawerLayout drawer;
    FragmentManager fragmentManager;
    NavigationView navigationView;
    Toolbar toolbar;
    String tooltitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,drawer,toolbar,
                R.string.navigation_draw_open,R.string.navigation_draw_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            Log.d(TAG, "onCreate: setHomepage");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){

            case R.id.menu_no1:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
                tooltitle = menuItem.getTitle().toString();
                toolbar.setTitle(tooltitle);
                break;

            case R.id.menu_no2:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment2()).addToBackStack(null).commit();
                tooltitle = menuItem.getTitle().toString();
                toolbar.setTitle(tooltitle);
                break;

            case R.id.menu_no3:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment3()).addToBackStack(null).commit();
                tooltitle = menuItem.getTitle().toString();
                toolbar.setTitle(tooltitle);
                break;

            case R.id.menu_no4:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment4()).addToBackStack(null).commit();
                tooltitle = menuItem.getTitle().toString();
                toolbar.setTitle(tooltitle);
                break;

            case R.id.contact:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ContactFragment()).addToBackStack(null).commit();
                tooltitle = menuItem.getTitle().toString();
                toolbar.setTitle(tooltitle);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
        if(fragmentManager.getBackStackEntryCount() == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment1()).addToBackStack(null).commit();
        }
    }
}
