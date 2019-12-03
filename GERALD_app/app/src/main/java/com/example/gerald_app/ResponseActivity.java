package com.example.gerald_app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        final RequestQueue requestQueue = Volley.newRequestQueue(this);

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

        // take and get image from server
        // ImageRequest code from: https://medium.com/@manuaravindpta/networking-using-volley-library-39c22061b4ba
        /*btnGetImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //POST method
                String url = "http://10.245.66.214:5000/getimg";
                int maxWidth = imgCapture.getMaxWidth();
                int maxHeight = imgCapture.getMaxHeight();
                ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        // Assign the response to imgCapture
                        imgCapture.setImageBitmap(response);
                    }
                }, maxWidth, maxHeight, ImageView.ScaleType.CENTER_INSIDE, null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                });
                //add request to queue
                requestQueue.add(imageRequest);
            }
        });*/


        // reset button brings user back to main activity page with the option to initialize the security system again
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bring user back to main activity
                openMainActivity();
            }
        });
    }

    // opens main activity
    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
