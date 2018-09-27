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

import com.picolab.R;
import com.picolab.utils.GetCanvas;

public class showImage extends AppCompatActivity {

    TextView count;
    ImageView image;
    Button showImageButton;
    Canvas canvas;
    GetCanvas getCanvas;

    private final static String url = "https://colaborativepicture.herokuapp.com/canvas/user";
    private TextView id, imageurl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        getCanvas = new GetCanvas(this, url);
        getCanvas.execute();

        count = (TextView)findViewById(R.id.counterText);
        image = (ImageView)findViewById(R.id.imageOriView);
        showImageButton = (Button)findViewById(R.id.showImageButton);
    }

    public void callBackData(String[] result) {
        Toast.makeText(getApplicationContext(), "Temperature: "+result[1],
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCountDown();
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
