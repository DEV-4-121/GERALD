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

    private Button btnReset, btnPolice;
    private ImageView imgCapture;
    private static boolean imageCheck;
    private static final String serverIP = "http://169.254.91.218:5000";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        // create instances of buttons in activity
        btnReset = findViewById(R.id.btnReset);
        btnPolice = findViewById(R.id.btnPolice);
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


        // reset button brings user back to main activity page with the option to initialize the security system again
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // exit bgThread
                imageCheck = false;

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
        while (imageCheck) {
            final RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = serverIP + "/getImage";
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
                                }
                            }, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    System.out.println("Failed to get image from camera on server");
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
                        }
                    });
            requestQueue.add(stringRequest);
            try {
                Thread.sleep(5000);
            }
            catch(InterruptedException ex)
            {
                System.out.println("thread interrupted");
            }
        }
    }


    // opens main activity
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
