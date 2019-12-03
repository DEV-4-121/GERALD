package com.example.gerald_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static java.lang.Boolean.parseBoolean;

public class MainActivity extends AppCompatActivity {

    private Button btnRun, btnStop;
    private ImageView imgCapture;
    private static boolean detect;
    private static boolean motionDetected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCapture = findViewById(R.id.imgCapture);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // get access to btnRun and btnStop
        btnRun = findViewById(R.id.btnRun);
        btnStop = findViewById(R.id.btnStop);

        // turn off stop button on app initialization
        btnStop.setEnabled(false);

        // Initiate security protocol
        // Sends post method to server to turn on motion sensor.
        // Start method on new thread to check in real time if motion was detected
        btnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tell server to turn security system on through post request
                String url = "http://169.254.91.218:5000/init";
                StringRequest stringRequest
                        = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("security system initialized");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                error.printStackTrace();
                            }
                        });
                requestQueue.add(stringRequest);

                // start new thread to periodically check if motion has been detected
                detect = true;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        checkDetection();
                    }
                }).start();

                // disable button and enable stop button
                btnRun.setEnabled(false);
                btnStop.setEnabled(true);
            }
        });

        // Hitting stop button will exit threads on both server and ap side that are checking for motion
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // stop check detection thread
                detect = false;

                // Tell server to stop motion detection through post request
                String url = "http://169.254.91.218:5000/stopDetection";
                StringRequest stringRequest
                        = new StringRequest(
                        Request.Method.POST,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("motion detection has been halted");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                error.printStackTrace();
                            }
                        });
                requestQueue.add(stringRequest);

                // disable button and enable start button
                btnRun.setEnabled(true);
                btnStop.setEnabled(false);
            }
        });
    }

    // method to run on separate thread to constantly check for motion detection from server
    private void checkDetection() {

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // initialize detected as false - new run sequence
        motionDetected = false;

        // keep thread alive while user has not turned GERALD off and motion has not been detected
        while (detect) {
            try {
                // get boolean from server to check status of motion sensor
                String url = "http://169.254.91.218:5000/checkDetection";
                StringRequest boolRequest
                        = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (parseBoolean(response)) {
                                    setDetectStatus();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                requestQueue.add(boolRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // check if motion has been detected
            if (motionDetected) {
                // open response activity
                openResponseActivity();
                return;
            }

            // wait 2 seconds before checking server detected status
            try {
                Thread.sleep(2000);
            }
             catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    // sets global detected variable to true
    private void setDetectStatus() {
        motionDetected = true;
    }

    // opens response activity when motion is detected
    private void openResponseActivity() {
        Intent intent = new Intent(this, ResponseActivity.class);
        startActivity(intent);
    }
}

// REFERENCES
// For GET/POST methods with flask API: https://varunmishra.com/teaching/cs65/http-volley/
// More references for server communication: https://medium.com/@manuaravindpta/networking-using-volley-library-39c22061b4ba
// multithreading for check detection: https://stackoverflow.com/questions/3489543/how-to-call-a-method-with-a-separate-thread-in-java
// time sleep for thread: https://stackoverflow.com/questions/24104313/how-do-i-make-a-delay-in-java