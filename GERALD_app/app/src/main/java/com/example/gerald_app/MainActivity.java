package com.example.gerald_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button testButton;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // get access to testButton
        testButton = findViewById(R.id.testButton);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //POST method
                String url = "http://192.168.0.29:5000/connect";
                StringRequest stringRequest
                        = new StringRequest(
                        Request.Method.POST,
                        url,
                        null,
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.v(TAG,"Error response is "+error);
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });
    }
}

// REFERENCES
// For GET/POST methods with flask API: https://varunmishra.com/teaching/cs65/http-volley/
