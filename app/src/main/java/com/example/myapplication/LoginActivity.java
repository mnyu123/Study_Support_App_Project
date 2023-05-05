package com.example.myapplication;

import static com.example.myapplication.R.layout.login_activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends Activity {
    private EditText editText_id, editText_password;
    private Button button_login;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(login_activity);
        setContentView(R.layout.login_activity);
//        initNavigationMenu();

        editText_id = findViewById(R.id.editText_id);
        editText_password = findViewById(R.id.editText_password);
        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = editText_id.getText().toString();
                String pw = editText_password.getText().toString();

                // ID와 PW가 입력되었는지 검증
                if (id.isEmpty() || pw.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "ID와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // DataAdapter를 이용해 사용자 인증
                DataAdapter dataAdapter = new DataAdapter(LoginActivity.this);
                dataAdapter.createDatabase();
                dataAdapter.open();
                boolean isAuthenticated = dataAdapter.authenticateUser(id, pw);
                dataAdapter.close();

                // 인증 실패 시 처리
                if (!isAuthenticated) {
                    Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 인증 성공 시 계정정보 저장 후 MainActivity로 이동
                dataAdapter.setAccount(id, pw);
                String firstUser = User.getUsername();

                Toast.makeText(LoginActivity.this,firstUser,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                Toast.makeText(LoginActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }


//    private void initNavigationMenu() {
//        //사이드 메뉴바
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        setSupportActionBar(binding.appBar.toolbar);
//        binding.appBar.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        DrawerLayout drawerLayout = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
//
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.menu_item2, R.id.menu_item3)
//                .setOpenableLayout(drawerLayout)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        navigationView.setNavigationItemSelectedListener(
//                new NavigationView.OnNavigationItemSelectedListener() {
//                    public MenuItem item;
//
//                    public boolean onNavigationItemSelected(MenuItem item) {
//                        this.item = item;
//                        int id = item.getItemId();
//
//                        switch (id) {
//                            case R.id.nav_home:
//                                // nav_home Fragment로 이동
//                                Log.d("MainActivity", "Home menu item clicked");
//                                navController.navigate(R.id.nav_home);
//                                break;
//                            case R.id.menu_item2:
//                                // menu_item2 Fragment로 이동
//                                Log.d("MainActivity", "Gallery menu item clicked");
//                                navController.navigate(R.id.menu_item2);
//                                break;
//                            case R.id.menu_item3:
//                                // menu_item3 Fragment로 이동
//                                navController.navigate(R.id.menu_item3);
//                                break;
//                            case R.id.menu_item4:
//                                // menu_item4 Fragment로 이동
//                                navController.navigate(R.id.menu_item4);
//                                break;
//                        }
//
//                        // Navigation Drawer 닫기
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        return true;
//                    }
//                }
//        );
//        View headerView = navigationView.getHeaderView(0);
//        TextView headerTextView = headerView.findViewById(R.id.header_text);
//        headerTextView.setText(firstUser);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
//

}
