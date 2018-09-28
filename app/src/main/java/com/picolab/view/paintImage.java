package com.picolab.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.picolab.R;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathDrawnListener;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;
import com.rm.freedrawview.ResizeBehaviour;

import org.w3c.dom.Text;

public class paintImage extends AppCompatActivity {

    FreeDrawView mSignatureView;
    Button btn_save;
    TextView whiteButton, redButton, blueButton, yellowButton, greenButton, blackButton, orangeButton, pinkButton, purpleButton, skyButton, clearButton, grayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintimage);

        whiteButton = (TextView)findViewById(R.id.colorWhiteButton);
        redButton = (TextView)findViewById(R.id.colorRedButton);
        blueButton = (TextView)findViewById(R.id.colorBlueButton);
        yellowButton = (TextView)findViewById(R.id.colorYellowButton);
        greenButton = (TextView)findViewById(R.id.colorGreenButton);
        blackButton = (TextView)findViewById(R.id.colorBlackButton);
        orangeButton = (TextView)findViewById(R.id.colorOrangeButton);
        pinkButton = (TextView)findViewById(R.id.colorPinkButton);
        purpleButton = (TextView)findViewById(R.id.colorPurpleButton);
        skyButton = (TextView)findViewById(R.id.colorSkyButton);
        grayButton = (TextView)findViewById(R.id.colorGrayButton);
        clearButton = (TextView)findViewById(R.id.clearButton);


        btn_save = (Button) findViewById(R.id.btn_save);
        mSignatureView = (FreeDrawView) findViewById(R.id.parentView);
        // Setup the View

       // mSignatureView.setPaintColor(Color.BLACK); //modifica el color con el que pinta
        mSignatureView.setPaintWidthPx(12);
        //mSignatureView.setPaintWidthPx(12);
        mSignatureView.setPaintWidthDp(12);
        //mSignatureView.setPaintWidthDp(6);
        mSignatureView.setPaintAlpha(255);// from 0 to 255
        mSignatureView.setResizeBehaviour(ResizeBehaviour.CROP);// Must be one of ResizeBehaviour
        // values;
        // This listener will be notified every time the path done and undone count changes
        mSignatureView.setPathRedoUndoCountChangeListener(new PathRedoUndoCountChangeListener() {
            @Override
            public void onUndoCountChanged(int undoCount) {
                // The undoCount is the number of the paths that can be undone
            }

            @Override
            public void onRedoCountChanged(int redoCount) {
                // The redoCount is the number of path removed that can be redrawn
            }
        });
        // This listener will be notified every time a new path has been drawn
        mSignatureView.setOnPathDrawnListener(new PathDrawnListener() {
            @Override
            public void onNewPathDrawn() {
                // The user has finished drawing a path
            }

            @Override
            public void onPathStart() {
                // The user has started drawing a path
            }
        });
        // This will take a screenshot of the current drawn content of the view
        mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
            @Override
            public void onDrawCreated(Bitmap draw) {
                // The draw Bitmap is the drawn content of the View
            }

            @Override
            public void onDrawCreationError() {
                // Something went wrong creating the bitmap, should never
                // happen unless the async task has been canceled
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.getDrawScreenshot(new FreeDrawView.DrawCreatorListener() {
                    @Override
                    public void onDrawCreated(Bitmap draw) {
                        //TODO:Aqui ya puedes coger el Bitmap y hacer lo que quieras
                        Toast.makeText(paintImage.this, "tengo el bitmap, envíalo", Toast.LENGTH_SHORT).show();
                        btn_save.setEnabled(false);
                    }
                    @Override
                    public void onDrawCreationError() {
                        //TODO:Muestra error!!
                    }
                });
            }
        });

        whiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.WHITE);
            }
        });
        redButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.RED);
            }
        });
        blueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.BLUE);
            }
        });
        yellowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.YELLOW);
            }
        });
        blackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.BLACK);
            }
        });
        greenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.GREEN);
            }
        });
        orangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.parseColor("#E49817"));
            }
        });
        pinkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.parseColor("#EB17BC"));
            }
        });
        purpleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.parseColor("#7817D7"));
            }
        });
        skyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.parseColor("#17DCEB"));
            }
        });
        grayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.setPaintColor(Color.parseColor("#C4C4C4"));
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignatureView.clearDraw();
                mSignatureView.setPaintColor(Color.BLACK);
            }
        });
    }

}