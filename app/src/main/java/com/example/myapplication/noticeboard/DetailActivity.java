package com.example.myapplication.noticeboard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class DetailActivity extends AppCompatActivity {

    private TextView title_tv, content_tv;
    private LinearLayout commentLayout;
    private EditText commentEt;
    private Button regButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        commentLayout = findViewById(R.id.comment_layout);
        commentEt = findViewById(R.id.comment_et);
        regButton = findViewById(R.id.reg_button);

        // ListActivity에서 전달받은 제목과 내용을 TextView에 표시
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String content = bundle.getString("content");
            title_tv.setText(title);
            content_tv.setText(content);
        }

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentEt.getText().toString().trim();
                if (!comment.isEmpty()) {
                    // 댓글 내용이 비어있지 않은 경우에만 댓글을 등록함
                    addComment(comment);
                    commentEt.setText(""); // 입력창 초기화
                }
            }
        });
    }

    // 댓글을 추가하는 메소드
    private void addComment(String comment) {
        // 댓글 뷰 생성
        TextView commentView = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 16, 0, 0);
        commentView.setLayoutParams(layoutParams);
        commentView.setText(comment);

        // 댓글 뷰 추가
        commentLayout.addView(commentView);
    }
}