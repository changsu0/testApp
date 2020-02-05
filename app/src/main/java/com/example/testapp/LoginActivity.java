package com.example.testapp;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        List<String> strList = new ArrayList<>();

        for (Map<String, Object> map : paramList){
            strList.add(map.get("userId").toString());
        }

        // 5. ArrayList 객체와 ListView 객체를 연결하기 위해 ArrayAdapter객체를 사용합니다.
        // 우선 ArrayList 객체를 ArrayAdapter 객체에 연결합니다.
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, //context(액티비티 인스턴스)
                android.R.layout.simple_list_item_1, // 한 줄에 하나의 텍스트 아이템만 보여주는 레이아웃 파일
                // 한 줄에 보여지는 아이템 갯수나 구성을 변경하려면 여기에 새로만든 레이아웃을 지정하면 됩니다.
                strList  // 데이터가 저장되어 있는 ArrayList 객체
        );

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);


    }
}
