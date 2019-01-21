package com.spk.santi.lipzme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


    public class FinishActivity extends AppCompatActivity {

        EditText tnl1,tnl2,tnl3,tnl4,tnl5;
        TextView tve1,tve2,tve3;
        TextView th1,th2,th3;
        TextView tp1,tp2,tp3,tp4,tp5,tp6;
        ImageView imgLipstik1,imgLipstik2,imgLipstik3;

        double[] vrEig= new double[3];
        double[] vrEigWarna= new double[3];
        double[] vrEigKualitas= new double[3];
        double[] vrEigTekstur= new double[3];
        double hsl[] = new double[3];
        double progress[] = new double[6];
        String spinner[] = new String[3];
        String val1,val2,val3;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_finish);
            GetVektorEigen();

            imgLipstik1 = (ImageView) findViewById(R.id.imgLipstik1);
            imgLipstik2 = (ImageView) findViewById(R.id.imgLipstik2);
            imgLipstik3 = (ImageView) findViewById(R.id.imgLipstik3);

            tve1 = (TextView) findViewById(R.id.ValueLipstik1);
            tve2 = (TextView) findViewById(R.id.ValueLipstik2);
            tve3 = (TextView) findViewById(R.id.ValueLipstik3);

            tp1 = (TextView) findViewById(R.id.ValueKualitasKW);
            tp2 = (TextView) findViewById(R.id.ValueWarnaKW);
            tp3 = (TextView) findViewById(R.id.ValueKualitasKT);
            tp4 = (TextView) findViewById(R.id.ValueTeksturKT);
            tp5 = (TextView) findViewById(R.id.ValueWarnaWT);
            tp6 = (TextView) findViewById(R.id.ValueTeksturWT);

            tp1.setText(String.format("%.3f",progress[0]));
            tp2.setText(String.format("%.3f",progress[1]));
            tp3.setText(String.format("%.3f",progress[2]));
            tp4.setText(String.format("%.3f",progress[3]));
            tp5.setText(String.format("%.3f",progress[4]));
            tp6.setText(String.format("%.3f",progress[5]));

            tnl1 = (EditText) findViewById(R.id.NmLipstik1); tnl1.setClickable(false); tnl1.setInputType(InputType.TYPE_NULL);
            tnl2 = (EditText) findViewById(R.id.NmLipstik2); tnl2.setClickable(false); tnl2.setInputType(InputType.TYPE_NULL);
            tnl3 = (EditText) findViewById(R.id.NmLipstik3); tnl3.setClickable(false); tnl3.setInputType(InputType.TYPE_NULL);
            tnl4 = (EditText) findViewById(R.id.TitlePerbandingan); tnl4.setClickable(false); tnl4.setInputType(InputType.TYPE_NULL);
            tnl5 = (EditText) findViewById(R.id.hasilPerbandingan); tnl5.setClickable(false); tnl5.setInputType(InputType.TYPE_NULL);

            tnl1.setText(spinner[0]);
            tnl2.setText(spinner[1]);
            tnl3.setText(spinner[2]);

            hsl[0] = (vrEigKualitas[0]*vrEig[0])+(vrEigWarna[0]*vrEig[1])+(vrEigTekstur[0]*vrEig[2]);
            hsl[1] = (vrEigKualitas[1]*vrEig[0])+(vrEigWarna[1]*vrEig[1])+(vrEigTekstur[1]*vrEig[2]);
            hsl[2] = (vrEigKualitas[2]*vrEig[0])+(vrEigWarna[2]*vrEig[1])+(vrEigTekstur[2]*vrEig[2]);

            val1 = String.format("%.1f",(hsl[0]*100))+"%";
            val2 = String.format("%.1f",(hsl[1]*100))+"%";
            val3 = String.format("%.1f",(hsl[2]*100))+"%";

            tve1.setText(val1);
            tve2.setText(val2);
            tve3.setText(val3);

            DynamicImage();
            DynamicText();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                startActivity(new Intent(this, AHPActivity.class));
                finish();
                return true;
            case R.id.action_home:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
        }
        return onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, AHPActivity.class));
        finish();
    }

    public void GetVektorEigen(){
        SharedPreferences sharedPref = getSharedPreferences("VektorEigen", Context.MODE_PRIVATE);

        vrEig[0] = Double.valueOf(sharedPref.getString("vrEig1",""));
        vrEig[1] = Double.valueOf(sharedPref.getString("vrEig2",""));
        vrEig[2] = Double.valueOf(sharedPref.getString("vrEig3",""));
        vrEigKualitas[0] = Double.valueOf(sharedPref.getString("vrEigKualitas1",""));
        vrEigKualitas[1] = Double.valueOf(sharedPref.getString("vrEigKualitas2",""));
        vrEigKualitas[2] = Double.valueOf(sharedPref.getString("vrEigKualitas3",""));
        vrEigWarna[0] = Double.valueOf(sharedPref.getString("vrEigWarna1",""));
        vrEigWarna[1] = Double.valueOf(sharedPref.getString("vrEigWarna2",""));
        vrEigWarna[2] = Double.valueOf(sharedPref.getString("vrEigWarna3",""));
        vrEigTekstur[0] = Double.valueOf(sharedPref.getString("vrEigTekstur1",""));
        vrEigTekstur[1] = Double.valueOf(sharedPref.getString("vrEigTekstur2",""));
        vrEigTekstur[2] = Double.valueOf(sharedPref.getString("vrEigTekstur3",""));

        progress[0]= Double.valueOf(sharedPref.getString("ProgressK1",""));
        progress[1]= Double.valueOf(sharedPref.getString("ProgressW1",""));
        progress[2]= Double.valueOf(sharedPref.getString("ProgressK2",""));
        progress[3]= Double.valueOf(sharedPref.getString("ProgressT1",""));
        progress[4]= Double.valueOf(sharedPref.getString("ProgressW2",""));
        progress[5]= Double.valueOf(sharedPref.getString("ProgressT2",""));

        spinner[0]= sharedPref.getString("CB1","");
        spinner[1]= sharedPref.getString("CB2","");
        spinner[2]= sharedPref.getString("CB3","");
    }

    public void DynamicText(){
        th1 = (TextView) findViewById(R.id.TxtHasil1);
        th2 = (TextView) findViewById(R.id.TxtHasil2);
        th3 = (TextView) findViewById(R.id.TxtHasil3);

        th1.setText("Dari hasil banding, "+ val1 +" merupakan hasil perbandingan dari Lipstik "+spinner[0]);
        th2.setText("Dari hasil banding, "+ val2 +" merupakan hasil perbandingan dari Lipstik "+spinner[1]);
        th3.setText("Dari hasil banding, "+ val3 +" merupakan hasil perbandingan dari Lipstik "+spinner[2]);
    }

    public void DynamicImage(){
        Resources res = getResources();
        String img1 = "ic_slide2";
        String img2 = "ic_slide2";
        String img3 = "ic_slide2";

        if(tnl1.getText().toString().equals("Goban")){
            img1 = "goban";
        }else if(tnl1.getText().toString().equals("Polka")){
            img1 = "polka";
        }else if(tnl1.getText().toString().equals("MOB")){
            img1 = "mob";
        }else if(tnl1.getText().toString().equals("Wardah")){
            img1 = "wardah";
        }else if(tnl1.getText().toString().equals("LT Pro")){
            img1 = "ltpro";
        }
        int resID1 = res.getIdentifier(img1 , "drawable", getPackageName());
        Drawable drawable1 = res.getDrawable(resID1);
        imgLipstik1.setImageDrawable(drawable1);

        if(tnl2.getText().toString().equals("Goban")){
            img2 = "goban";
        }else if(tnl2.getText().toString().equals("Polka")){
            img2 = "polka";
        }else if(tnl2.getText().toString().equals("MOB")){
            img2 = "mob";
        }else if(tnl2.getText().toString().equals("Wardah")){
            img2 = "wardah";
        }else if(tnl2.getText().toString().equals("LT Pro")){
            img2 = "ltpro";
        }
        int resID2 = res.getIdentifier(img2 , "drawable", getPackageName());
        Drawable drawable2 = res.getDrawable(resID2);
        imgLipstik2.setImageDrawable(drawable2);

        if(tnl3.getText().toString().equals("Goban")){
            img3 = "goban";
        }else if(tnl3.getText().toString().equals("Polka")){
            img3 = "polka";
        }else if(tnl3.getText().toString().equals("MOB")){
            img3 = "mob";
        }else if(tnl3.getText().toString().equals("Wardah")){
            img3 = "wardah";
        }else if(tnl3.getText().toString().equals("LT Pro")){
            img3 = "ltpro";
        }
        int resID3 = res.getIdentifier(img3 , "drawable", getPackageName());
        Drawable drawable3 = res.getDrawable(resID3);
        imgLipstik3.setImageDrawable(drawable3);
    }
}
