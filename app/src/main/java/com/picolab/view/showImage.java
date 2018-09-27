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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.picolab.R;
import com.picolab.domain.CanvasImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.net.URL;

public class showImage extends AppCompatActivity {

    TextView count;
    ImageView image;
    Button showImageButton;
    CanvasImage canvasImage;
    private RequestQueue queue;


    private final static String url = "https://colaborativepicture.herokuapp.com/canvas/user";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        count = (TextView)findViewById(R.id.counterText);
        image = (ImageView)findViewById(R.id.imageOriView);
        showImageButton = (Button)findViewById(R.id.showImageButton);

        queue = Volley.newRequestQueue(showImage.this);

        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    //PIDO LOS DATOS MEDIANTE GET
    private void obtenerDatosGet() {

        String url = "https://colaborativepicture.herokuapp.com/canvas/user";

        //Genero el request on la url
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Guardo el response en un objeto JSON
                JSONObject jsonObject = response;

                //Se castea el id y la url
                try {
                    int id = (int)jsonObject.get("id");
                    String canvasUrl = jsonObject.get("url").toString();
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

    @Override
    protected void onResume() {
        super.onResume();
        showImageButton.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatosGet();
                //startCountDown();
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
