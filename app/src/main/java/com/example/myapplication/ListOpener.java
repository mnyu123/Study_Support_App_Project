package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;


public class ListOpener extends AppCompatActivity {

    ListView listView;
    Button BTNall;
    Button BTNOK;
    Button BTNNO;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cslist);
        CSLIST cslist = new CSLIST();
        listView = (ListView)findViewById(R.id.cslistview);

        initLoad();
        BTNall = findViewById(R.id.BTNALL);
        BTNOK = findViewById(R.id.BTNOK);
        BTNNO = findViewById(R.id.BTNNO);

        BTNall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initLoad();
            }
        });
        BTNOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CsokinitLoad();
            }
        });
        BTNNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CsnoinitLoad();
            }
        });
    }

    public void initLoad() {

        DataBaseHelper dbhelper =new DataBaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student WHERE is_professor = '0'",null);
        CSLIST cslist = new CSLIST();

        String va1="";
        String va2="";
        String va3="";
        String va4="";
        while(cursor.moveToNext()){
            cslist.addItemToList(cursor.getString(1),cursor.getString(0),cursor.getString(6),cursor.getString(7));
        }

        listView.setAdapter(cslist);
        dbhelper.close();
        db.close();
    }



    public void CsokinitLoad() {

        DataBaseHelper dbhelper =new DataBaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student WHERE Cs = 'O' AND is_professor = '0' ",null);
        CSLIST cslist = new CSLIST();

        String va1="";
        String va2="";
        String va3="";
        String va4="";
        while(cursor.moveToNext()){
            cslist.addItemToList(cursor.getString(1),cursor.getString(0),cursor.getString(6),cursor.getString(7));
        }

        listView.setAdapter(cslist);
        dbhelper.close();
        db.close();
    }

    public void CsnoinitLoad() {

        DataBaseHelper dbhelper =new DataBaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM student WHERE Cs = 'X' AND is_professor = '0'",null);
        CSLIST cslist = new CSLIST();

        String va1="";
        String va2="";
        String va3="";
        String va4="";
        while(cursor.moveToNext()){
            cslist.addItemToList(cursor.getString(1),cursor.getString(0),cursor.getString(6),cursor.getString(7));
        }

        listView.setAdapter(cslist);
        dbhelper.close();
        db.close();
    }



}
