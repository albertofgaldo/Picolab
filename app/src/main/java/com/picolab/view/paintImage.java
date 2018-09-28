package com.picolab.view;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.picolab.R;
import com.rm.freedrawview.FreeDrawView;
import com.rm.freedrawview.PathDrawnListener;
import com.rm.freedrawview.PathRedoUndoCountChangeListener;
import com.rm.freedrawview.ResizeBehaviour;


import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;

import static com.picolab.view.showImage.canvasImage;

public class paintImage extends AppCompatActivity {

    FreeDrawView mSignatureView;
    Button btn_save;
    TextView whiteButton, redButton, blueButton, yellowButton, greenButton, blackButton, orangeButton, pinkButton, purpleButton, skyButton, clearButton, grayButton;
    //FirebaseStorage storage = FirebaseStorage.getInstance();


    private Uri filePath;

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
                        Toast.makeText(paintImage.this, "Tu dibujo se ha enviado", Toast.LENGTH_SHORT).show();
                        uploadImage(draw);
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

        // Create a reference to 'images/mountains.jpg'
        //StorageReference mountainImagesRef = storageRef.child(canvasImage.getId() + ".jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
               // Uri downloadUrl = taskSnapshot.getDownloadUrl();
            }
        });
    }

}