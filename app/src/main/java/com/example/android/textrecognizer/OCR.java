package com.example.android.textrecognizer;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

    public void pick_image(View v)
    {
    }
}