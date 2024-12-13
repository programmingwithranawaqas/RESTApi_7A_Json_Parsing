package com.example.restapi_7a;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvResult = findViewById(R.id.tvResult);
        url = "https://jsonplaceholder.typicode.com/todos/";

        JsonArrayRequest json = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray res) {
            try {
                Toast.makeText(MainActivity.this, res.getJSONObject(0).toString()+"", Toast.LENGTH_SHORT).show();

                String filtered = "";
                for(int i=0; i<res.length();i++) {
                    JSONObject response = res.getJSONObject(i);
                    int id = response.getInt("id");
                    int userId = response.getInt("userId");
                    String title = response.getString("title");
                    Boolean isCompleted = response.getBoolean("completed");
                    if (!isCompleted) {
                        filtered += ("ID : " + id + "\n" + "TITLE : " + title)+"\n";

                    } else {
                        tvResult.setText("Task not completed");
                    }

                }
                tvResult.setText(filtered);
                }catch (JSONException e)
                {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvResult.setText(error.getMessage());
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

//        JsonObjectRequest json = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                //try {
//                    Toast.makeText(MainActivity.this, response.length()+"", Toast.LENGTH_SHORT).show();
//
//                    tvResult.setText(response.toString());
//                   /* int id = response.getInt("id");
//                    int userId = response.getInt("userId");
//                    String title = response.getString("title");
//                    Boolean isCompleted = response.getBoolean("completed");
//                    if(isCompleted)
//                    {
//                        tvResult.setText("ID : "+id+"\n"+"TITLE : "+title);
//                    }
//                    else
//                    {
//                        tvResult.setText("Task not completed");
//                    }
//
//                    */
////                }catch (JSONException e)
////                {
////                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
////                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                tvResult.setText(error.getMessage());
//                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(json);
    }
}