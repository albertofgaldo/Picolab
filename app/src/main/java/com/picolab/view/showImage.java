package com.picolab.view;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.picolab.application.Controller.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.picolab.R;
import com.picolab.application.Controller.RequestsController;
import com.picolab.domain.CanvasImage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.URL;

public class showImage extends AppCompatActivity {

    TextView count;
    ImageView image;
    Button showImageButton;

    private RequestQueue queue;
    protected static CanvasImage canvasImage = new CanvasImage();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        count = (TextView)findViewById(R.id.counterText);
        image = (ImageView)findViewById(R.id.imageOriView);
        showImageButton = (Button)findViewById(R.id.showImageButton);

        queue = Volley.newRequestQueue(this);

        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatosGet();
                loadImageFromUrl(canvasImage.getUrl());
                startCountDown();
            }
        });
    }

    //PIDO LOS DATOS MEDIANTE GET
    private void obtenerDatosGet() {

        String url = "https://colaborativepicture.herokuapp.com/canvas/user/";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject JsonCanvas = new JSONObject();
                JsonCanvas = response;
                try {
                    int id = Integer.parseInt(JsonCanvas.get("id").toString());
                    String urlCanvas = JsonCanvas.get("url").toString();
                    canvasImage.setId(id);
                    canvasImage.setUrl(urlCanvas);
//                    Toast.makeText(showImage.this, "Id: " + canvasImage.getId(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request);
    }

    private void loadImageFromUrl(String url){
        Picasso.with(this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    public void startCountDown(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                long l = millisUntilFinished / 1000;
                if(l<=3){count.setTextColor(Color.RED);}
                count.setText(Long.toString(l));
            }

            public void onFinish() {
                Toast.makeText(showImage.this,"Cuenta finalizada", Toast.LENGTH_SHORT).show();
                Intent paintImage = new Intent(showImage.this, paintImage.class);
                startActivity(paintImage);
            }
        }.start();
    }

}
