package com.example.testapp;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        List<Map<String, Object>> paramList = new ArrayList<Map<String, Object>>();
        paramList = (ArrayList<Map<String, Object>>)getIntent().getSerializableExtra("userList");


        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_login, paramList);


        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


        for (Map<String, Object> map: paramList) {
            for( Map.Entry<String, Object> entry : map.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", entry.getKey(), entry.getValue()) );


            }
        }
    }
}
