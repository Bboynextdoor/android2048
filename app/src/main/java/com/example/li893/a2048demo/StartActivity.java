package com.example.li893.a2048demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class StartActivity extends Activity {
    public String isLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button start =findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoad="false";
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("isLoad",isLoad);
                startActivity(intent);
            }
        });

        Button load =findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoad="true";
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                intent.putExtra("isLoad",isLoad);
                startActivity(intent);
            }
        });

        Button rank =findViewById(R.id.rank);
        rank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,RankActivity.class);
                startActivity(intent);
            }
        });
    }
}
