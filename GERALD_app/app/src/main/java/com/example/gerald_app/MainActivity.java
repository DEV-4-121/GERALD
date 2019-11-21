package com.example.gerald_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    private Button btnRun;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // get access to btnRun
        btnRun = findViewById(R.id.btnRun);

        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //POST method
                String url = "http://10.245.73.230:5000/connect";
                StringRequest stringRequest
                        = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                openResponseActivity();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.v(TAG,"Error response is "+ error);
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });
    }

    private void openResponseActivity() {
        Intent intent = new Intent(this, ResponseActivity.class);
        startActivity(intent);
    }
}

// REFERENCES
// For GET/POST methods with flask API: https://varunmishra.com/teaching/cs65/http-volley/
// More references for server communication: https://medium.com/@manuaravindpta/networking-using-volley-library-39c22061b4ba
