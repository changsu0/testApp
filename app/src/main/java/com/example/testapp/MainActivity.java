
package com.example.testapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.testapp.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    GetRestfulTask task;

    String strCallUrl = "http://changsu0.cafe24.com/api/getBusinessNo.api?businessNo=";

    JsonUtils jsonUtils = new JsonUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    protected void onResume() {
        super.onResume();

    }

    public void onClickBtn(View view) {
        EditText editTextLoginID = (EditText) findViewById(R.id.editText_id);
        EditText editTextLoginPW = (EditText) findViewById(R.id.editText_pw);

//        editTextLoginID.setText("user01");
//        editTextLoginPW.setText("1");

        task = new GetRestfulTask();
        task.execute(strCallUrl + editTextLoginID.getText());

    }

    public void cbApi(List<Map<String, Object>> paramList){

        System.out.println(paramList);
        for (Map<String, Object> map: paramList) {
            System.out.println(map);
            for( Map.Entry<String, Object> entry : map.entrySet() ){
                System.out.println( String.format("키 : %s, 값 : %s", entry.getKey(), entry.getValue()) );
            }
        }

        if(paramList.size() > 0){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class GetRestfulTask extends AsyncTask<String, Integer, List<Map<String, Object>>> {

        @Override
        protected List<Map<String, Object>> doInBackground(String... params){

            List<Map<String, Object>> rstList = new ArrayList<Map<String, Object>>();
            try {
                // call API by using HTTPURLConnection
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                int resCode = urlConnection.getResponseCode();

                if (200 == resCode){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    JSONObject json = new JSONObject(getStringFromInputStream(in));
                    rstList = jsonUtils.getListMapFromJsonArray((JSONArray) json.get("shopUserVO"));
                }

            }catch(MalformedURLException e){
                System.err.println("Malformed URL");
                e.printStackTrace();
                return null;
            }catch(JSONException e) {
                System.err.println("JSON parsing error");
                e.printStackTrace();
                return null;
            }catch(IOException e){
                System.err.println("URL Connection failed");
                e.printStackTrace();
                return null;
            }
            return rstList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> map) {
            cbApi(map);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        private String getStringFromInputStream(InputStream is) {
            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return sb.toString();
        }
    }
}