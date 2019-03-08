package com.example.akbar.testcodeqr;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class MainActivity extends AppCompatActivity {
    private Button button1, button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button mButton = findViewById(R.id.m_button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDate = new DatePickerDialog(MainActivity.this, date, 2016, 2, 24);
                mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                mDate.show();
            }
        });




        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity1();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });

    }
    public void openActivity1(){
        Intent i = new Intent(this, GeneratorActivity.class);
        startActivity(i);
    }
    public void openActivity2(){
        Intent in = new Intent(this, ReaderActivity.class);
        startActivity(in);
    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);

        }
    };

}
