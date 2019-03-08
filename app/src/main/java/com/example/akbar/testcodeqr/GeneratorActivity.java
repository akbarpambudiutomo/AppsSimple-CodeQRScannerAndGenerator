package com.example.akbar.testcodeqr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GeneratorActivity extends AppCompatActivity {


    private Button gen_btn;
    private EditText text1;
    String text2Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generator);


        Thread t = new Thread() {
         @Override
         public void run() {
           try {
             while (!isInterrupted()) {
                Thread.sleep(1000);
                runOnUiThread(new Runnable() {
                   @Override
                 public void run() {
                     EditText tdate = (EditText) findViewById(R.id.Et1);
                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy hh:mm:ss");
                   String dateString = sdf.format(date);
                  tdate.setText(dateString);
              }
           });
           }
          } catch (InterruptedException e) {
             }
            }
           };
             t.start();

        final Context context = this;
        text1 = this.findViewById(R.id.Et1);
        gen_btn = this.findViewById(R.id.gen_btn);

        gen_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                text2Qr = text1.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE, 200, 200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    Intent intent = new Intent(context, GeneratorReaderActivity.class);
                    intent.putExtra("pic", bitmap);
                    context.startActivity(intent);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
