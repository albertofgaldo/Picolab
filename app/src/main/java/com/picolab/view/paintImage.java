package com.picolab.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.picolab.R;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathDrawnListener;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;
import com.rm.freedrawview.ResizeBehaviour;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static com.picolab.view.showImage.canvasImage;

public class paintImage extends AppCompatActivity {

    FreeDrawView mSignatureView;
    Button btn_save;
    ImageView image;
    TextView whiteButton, redButton, blueButton, yellowButton, greenButton, blackButton, orangeButton, pinkButton, purpleButton, skyButton, clearButton, grayButton;
    private RequestQueue queue;
    private Uri filePath;
    String url =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintimage);

        url = getIntent().getExtras().getString("url");
        image = (ImageView)findViewById(R.id.imageOriView);
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
                        showToast("Tu dibujo se ha enviado");
                        uploadImage(draw);
                        devolverDatosPut();
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

    private void uploadImage(Bitmap draw) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        draw.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte[] data = baos.toByteArray();
        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReferenceFromUrl("gs://picolab-c4b9b.appspot.com");

        // Create a reference to "mountains.jpg"
        StorageReference mountainsRef = storageRef.child(canvasImage.getId() + ".jpg");

        final UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                showToast("El dibujo no se ha subido");
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Task<Uri> downloadUrl = uploadTask.getResult().getMetadata().getReference().getDownloadUrl();
                canvasImage.setUrl(downloadUrl.getResult().toString());
                //devolverDatosPut();
            }
        });
    }

    private void devolverDatosPut() {

        String url = "https://colaborativepicture.herokuapp.com/canvas/user";

        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                      //  Log.d("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                       // Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",Integer.toString(canvasImage.getId()));
                params.put("url", canvasImage.getUrl());

                return params;
            }
        };
        queue.add(putRequest);

    }
    public void showToast(String text){
        Toast toast = new Toast(paintImage.this);
        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.makeText(paintImage.this, text, Toast.LENGTH_SHORT).show();
    }

}