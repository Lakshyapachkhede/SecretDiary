package com.pachkhede.secretdiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.concurrent.locks.Lock;


public class MainActivity extends AppCompatActivity implements MainFragment.OnAddButtonClickListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.OpenDrawer, R.string.CloseDrawer);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.lock_icon) {
                    Intent intent = new Intent(MainActivity.this, LockSetupActivity.class);
                    startActivity(intent);
                } else if (id == R.id.web_icon) {
                    String url = "https://lakshyapachkhede.github.io/Lakshyapachkhede/";

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));

                    startActivity(intent);

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        loadFragment(new MainFragment(), false, false);



    }


    private void loadFragment(Fragment fragment, boolean replace, boolean addToBackStack) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (replace) {
            ft.replace(R.id.main_fragment_container, fragment);
        } else {
            ft.add(R.id.main_fragment_container, fragment);
        }

        if (addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }

    public void detachFragment() {
        getSupportFragmentManager().popBackStack();
    }

    private void initViews() {
        drawerLayout = findViewById(R.id.main);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);


    }





    @Override
    public void onAddButtonClicked() {
        loadFragment(new EntryAddFragment(), true, true);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        SharedPreferences sharedPreferences = getSharedPreferences(LockSetupActivity.preferencesName, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(LockSetupActivity.prefIsLockEnabled, false)) {
            if (level == TRIM_MEMORY_UI_HIDDEN) {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        }


    }
}






