package com.example.gerald_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ResponseActivity extends AppCompatActivity {

    private Button btnReset, btnPolice, btnTurret;
    private ImageView imgCapture;
    private static boolean imageCheck;
    private static final String serverIP = "http://169.254.91.218:5000";
    private static boolean lastReqFinished = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        // create instances of buttons in activity
        btnReset = findViewById(R.id.btnReset);
        btnPolice = findViewById(R.id.btnPolice);
        btnTurret = findViewById(R.id.btnTurret);
        imgCapture = findViewById(R.id.imgCapture);

        // call police on btnPolice click
        // code for phone call from: https://stackoverflow.com/questions/5403308/make-a-phone-call-click-on-a-button
        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:911"));
                    startActivity(callIntent);
                }
        });

        btnTurret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tell server to fire turret
                String url = serverIP + "/fireTurret/d2f747a10ab09354956653c28dd09fb03880990b211ce8e53b7c5d6507e8b1c9";
                StringRequest stringRequest
                        = new StringRequest(
                        Request.Method.GET,
                        url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println("turret fired");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                System.out.println("Failed to fire turret");
                            }
                        });
                requestQueue.add(stringRequest);
            }
        });

        // reset button brings user back to main activity page with the option to initialize the security system again
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exit bgThread
                imageCheck = false;
                lastReqFinished = true;

                // bring user back to main activity
                openMainActivity();
            }
        });



    }

    // override starts image thread whenever activity is reopened
    @Override
    protected void onResume()
    {
        super.onResume();
        // start bgThread to update image from server
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateImg();
            }
        }).start();
    }

    // method to keep updating image from server while user is on response page
    // nested requests firsts takes image with camera, then populates the apps image view with it
    public void updateImg() {
        imageCheck = true;
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        while (imageCheck) {
            // Use encrypted string to gain access to getImage function on server
            String url = serverIP + "/getImage/d2f747a10ab09354956653c28dd09fb03880990b211ce8e53b7c5d6507e8b1c9";
            StringRequest stringRequest
                    = new StringRequest(
                    Request.Method.GET,
                    url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                            String imgURL = serverIP + response;
                            int maxWidth = imgCapture.getMaxWidth();
                            int maxHeight = imgCapture.getMaxHeight();
                            ImageRequest imageRequest = new ImageRequest(imgURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap imgResponse) {
                                    // Assign the response to imgCapture
                                    imgCapture.setImageBitmap(imgResponse);
                                    lastReqFinished = true;
                                }
                            }, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    System.out.println("Failed to get image from camera on server");
                                    lastReqFinished = true;
                                }
                            });
                            //add request to queue
                            requestQueue.add(imageRequest);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            error.printStackTrace();
                            lastReqFinished = true;
                        }
                    });
            if (lastReqFinished) {
                requestQueue.add(stringRequest);
                lastReqFinished = false;
            }
        }
    }


    // opens main activity
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
