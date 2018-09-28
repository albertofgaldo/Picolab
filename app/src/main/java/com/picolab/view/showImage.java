package com.picolab.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
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
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

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

            }
        });
    }

    //PIDO LOS DATOS MEDIANTE GET
    private void obtenerDatosGet() {

        String url = "https://colaborativepicture.herokuapp.com/canvas/user";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject JsonCanvas = response;
                //JsonCanvas = response;
                try {
                    int id = Integer.parseInt(JsonCanvas.get("id").toString());
                    String urlCanvas = JsonCanvas.get("url").toString();
                    canvasImage.setId(id);
                    canvasImage.setUrl(urlCanvas);
                    loadImageFromUrl(canvasImage.getUrl());
                } catch (JSONException e) {
                    showToast("Error en el JSON");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("Sin respuesta del Servidor");
            }
        });
        queue.add(request);
    }

    private void loadImageFromUrl(String url){
        Picasso.with(showImage.this).load(url).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        //findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        startCountDown();
                        showImageButton.setEnabled(false);
                    }

                    @Override
                    public void onError() {
                        showToast("No hay imagen para cargar");
                    }
                });
    }

    public void startCountDown(){
        new CountDownTimer(1000, 1000) {

            public void onTick(long millisUntilFinished) {
                long l = millisUntilFinished / 1000;
                if(l<=3){count.setTextColor(Color.RED);}
                count.setText(Long.toString(l));
            }

            public void onFinish() {
                Toast.makeText(showImage.this,"Cuenta finalizada", Toast.LENGTH_SHORT).show();
                Intent paintImage = new Intent(showImage.this, paintImage.class);
                paintImage.putExtra("url",canvasImage.getUrl());
                startActivity(paintImage);
                finishAffinity();
            }
        }.start();
    }

    public void showToast(String text){
        Toast toast = new Toast(showImage.this);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.makeText(showImage.this, text, Toast.LENGTH_SHORT).show();
    }

}
