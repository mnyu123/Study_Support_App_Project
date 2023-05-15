package com.example.myapplication;

import static com.example.myapplication.R.layout.login_activity;

import android.app.Activity;
import android.content.Context;
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
}
