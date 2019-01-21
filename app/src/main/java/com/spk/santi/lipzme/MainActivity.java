package com.spk.santi.lipzme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    ImageButton imgBtnBandingkan,imgBtnTentang,imgBtnBantuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        imgBtnBandingkan = (ImageButton) findViewById(R.id.imgBtnBandingkan);
        imgBtnBandingkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHPActivity();
            }
        });

        imgBtnTentang = (ImageButton) findViewById(R.id.imgBtnTentang);
        imgBtnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TentangActivity();
            }
        });

        imgBtnBantuan = (ImageButton) findViewById(R.id.imgBtnBantuan);
        imgBtnBantuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BantuanActivity();
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    protected void AHPActivity(){
        Intent intent=new Intent(this, AHPActivity.class);
        startActivity(intent);
        finish();
    }


    protected void BantuanActivity(){
        Intent intent=new Intent(this, BantuanActivity.class);
        startActivity(intent);
    }

    protected void TentangActivity(){
        Intent intent=new Intent(this, TentangActivity.class);
        startActivity(intent);
    }
}


