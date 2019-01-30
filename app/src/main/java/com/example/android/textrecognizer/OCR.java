package com.example.android.textrecognizer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OCR extends AppCompatActivity {

    ImageView iv;
    TextView tv;
    Bitmap bp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);
        iv=(ImageView)findViewById(R.id.ImageView);
        tv=(TextView)findViewById(R.id.textView);
    }

    public void detect(View v)
    {
        if(bp==null)
        {
            Toast.makeText(getApplicationContext(),"Bitmap is Null", Toast.LENGTH_LONG).show();
        }
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bp);
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        firebaseVisionTextRecognizer.processImage(firebaseVisionImage)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {

                        process_text(firebaseVisionText);
                    }
                });


    }

    private void process_text(FirebaseVisionText firebaseVisionText) {
        List<FirebaseVisionText.TextBlock> blocks = firebaseVisionText.getTextBlocks();
        if(blocks.size()==0)
        {
            Toast.makeText(getApplicationContext(),"No text detected", Toast.LENGTH_LONG).show();
        }else
        {
            for (FirebaseVisionText.TextBlock block: firebaseVisionText.getTextBlocks())
            {
                String text = block.getText();
                tv.setText(text);
                //Toast.makeText(getApplicationContext(),"Extracted ", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void pick_image(View v)
    {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i,1);

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri uri = data.getData();
            try {
                bp=MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                iv.setImageBitmap(bp);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}