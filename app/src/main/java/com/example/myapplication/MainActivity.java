package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button Tocs;
    private Context cscontext;

    private ActivityMainBinding binding;
    private AppBarConfiguration mAppBarConfiguration;

    String firstUser = User.getUsername();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNavigationMenu();

        Tocs = (Button)findViewById(R.id.ToCs);

        Tocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CsCheck.class);
                startActivity(intent);
                //((CsCheck)cscontext).CsCheck();
            }
        });
        //bind view

    }
    private void initNavigationMenu() {
        //사이드 메뉴바
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBar.toolbar);
        binding.appBar.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawerLayout = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.menu_item2, R.id.menu_item3)
                .setOpenableLayout(drawerLayout)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    public MenuItem item;
                    public boolean onNavigationItemSelected(MenuItem item) {
                        this.item = item;
                        int id = item.getItemId();

                        switch (id) {
                            case R.id.nav_home:
                                // nav_home Fragment로 이동
                                Log.d("MainActivity", "Home menu item clicked");
                                navController.navigate(R.id.nav_home);
                                break;
                            case R.id.menu_item2:
                                // menu_item2 Fragment로 이동
                                Log.d("MainActivity", "Gallery menu item clicked");
                                navController.navigate(R.id.menu_item2);
                                break;
                            case R.id.menu_item3:
                                // menu_item3 Fragment로 이동
                                navController.navigate(R.id.menu_item3);
                                break;
                            case R.id.menu_item4:
                                // menu_item4 Fragment로 이동
                                navController.navigate(R.id.menu_item4);
                                break;
                        }

                        // Navigation Drawer 닫기
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    }
                }
        );
        View headerView = navigationView.getHeaderView(0);
        TextView headerTextView = headerView.findViewById(R.id.header_text);
        headerTextView.setText(firstUser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}








