package com.spk.santi.lipzme;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class AlternatifKualitas extends AppCompatActivity {

    SeekBar sb1,sb2,sb3;
    TextView tv1,tv2,tv3, tvlips1,tvlips2,tvlips3,
            tvlips4,tvlips5,tvlips6;

    int xPos;
    double[] vrEigKualitas= new double[3];
    double[] m = new double[3];
    String[] merk = new String[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_alternatifkualitas);

        sb1 = (SeekBar) findViewById(R.id.SBKualitas1Alt1);
        sb2 = (SeekBar) findViewById(R.id.SBKualitas2Alt1);
        sb3 = (SeekBar) findViewById(R.id.SBKualitas3Alt1);
        sb1.setProgress(8);
        sb2.setProgress(8);
        sb3.setProgress(8);

        tv1 = (TextView) findViewById(R.id.TxtValKualitas1);
        tv2 = (TextView) findViewById(R.id.TxtValKualitas2);
        tv3 = (TextView) findViewById(R.id.TxtValKualitas3);

        Button btnbanding = (Button) findViewById(R.id.BtnBandingkanK);

        tvlips1 = (TextView) findViewById(R.id.Lips1Kualitas1);
        tvlips2 = (TextView) findViewById(R.id.Lips2Kualitas1);
        tvlips3 = (TextView) findViewById(R.id.Lips1Kualitas2);
        tvlips4 = (TextView) findViewById(R.id.Lips3Kualitas1);
        tvlips5 = (TextView) findViewById(R.id.Lips2Kualitas2);
        tvlips6 = (TextView) findViewById(R.id.Lips3Kualitas2);

        SharedPreferences sharedPref = getSharedPreferences("VektorEigen", Context.MODE_PRIVATE);
        merk[0]= sharedPref.getString("CB1","");
        merk[1]= sharedPref.getString("CB2","");
        merk[2]= sharedPref.getString("CB3","");

        tvlips1.setText(merk[0]);
        tvlips2.setText(merk[1]);
        tvlips3.setText(merk[0]);
        tvlips4.setText(merk[2]);
        tvlips5.setText(merk[1]);
        tvlips6.setText(merk[2]);

        LipstikSeekBar();
        btnbanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AHPAlternatif();
                SaveVektorEigen(v);
                AlternatifWarna();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, AHPActivity.class));
        finish();
    }

    public void LipstikSeekBar(){
        //Thumbnail SeekBar 1 Listener
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekBar1Val(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv1.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        //Thumbnail SeekBar 2 Listener
        sb2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekBar2Val(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //Thumbnail SeekBar 3 Listener
        sb3.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekBar3Val(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                tv3.setVisibility(View.VISIBLE);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    //Value of Thumbnail SeekBar 1
    public void SeekBar1Val(int val){

        int left = sb1.getLeft() + sb1.getPaddingLeft();
        int right = sb1.getRight() - sb1.getPaddingRight();

        for(int i=0;i<=16;i++){
            if(val == i && i <8){
                xPos = (((right - left) * sb1.getProgress()) / sb1.getMax()) + left;
                tv1.setX(xPos - tv1.getWidth() / 2);
                int thumb = 9-val;
                tv1.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i>8) {
                xPos = (((right - left) * sb1.getProgress()) / sb1.getMax()) + left;
                tv1.setX(xPos - tv1.getWidth() / 2);
                int thumb = val-7;
                tv1.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i==8){
                xPos = (((right - left) * sb1.getProgress()) / sb1.getMax()) + left;
                tv1.setX(xPos - tv1.getWidth() / 2);
                int thumb = 1;
                tv1.setText(String.valueOf(thumb));
                break;
            }
        }
    }
    //Value of Thumbnail SeekBar 2
    public void SeekBar2Val(int val){

        int left = sb2.getLeft() + sb2.getPaddingLeft();
        int right = sb2.getRight() - sb2.getPaddingRight();

        for(int i=0;i<=16;i++){
            if(val == i && i <8){
                xPos = (((right - left) * sb2.getProgress()) / sb2.getMax()) + left;
                tv2.setX(xPos - tv2.getWidth() / 2);
                int thumb = 9-val;
                tv2.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i>8) {
                xPos = (((right - left) * sb2.getProgress()) / sb2.getMax()) + left;
                tv2.setX(xPos - tv2.getWidth() / 2);
                int thumb = val-7;
                tv2.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i==8){
                xPos = (((right - left) * sb2.getProgress()) / sb2.getMax()) + left;
                tv2.setX(xPos - tv2.getWidth() / 2);
                int thumb = 1;
                tv2.setText(String.valueOf(thumb));
                break;
            }
        }
    }
    //Value of Thumbnail SeekBar 3
    public void SeekBar3Val(int val){

        int left = sb3.getLeft() + sb3.getPaddingLeft();
        int right = sb3.getRight() - sb3.getPaddingRight();

        for(int i=0;i<=16;i++){
            if(val == i && i <8){
                xPos = (((right - left) * sb3.getProgress()) / sb3.getMax()) + left;
                tv3.setX(xPos - tv3.getWidth() / 2);
                int thumb = 9-val;
                tv3.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i>8) {
                xPos = (((right - left) * sb3.getProgress()) / sb3.getMax()) + left;
                tv3.setX(xPos - tv3.getWidth() / 2);
                int thumb = val-7;
                tv3.setText(String.valueOf(thumb));
                break;
            }else if(val == i && i==8){
                xPos = (((right - left) * sb3.getProgress()) / sb3.getMax()) + left;
                tv3.setX(xPos - tv3.getWidth() / 2);
                int thumb = 1;
                tv3.setText(String.valueOf(thumb));
                break;
            }
        }
    }


    /**
     * AHP Method
     */
    public void AHPAlternatif(){
        int n=3;
        int NUMBER_COMPARISON=(n*n-n)/2;
        double tb;

        double [] jml=new double[n];
        double [] jmlb=new double[n];
        double [][] pair=new double[n][n];
        double [][] a=new double[n][n];
        double [] p=new double[NUMBER_COMPARISON];

        a=SeekBarAlternatif(p);

        /**
         * Jumlah per-Kolom Matriks
         */
        int x=0;
        for(int i=0;i<n;i++){
            jml[x]=a[0][i]+a[1][i]+a[2][i];
            x++;
        }

        /**
         * Normal Pairwise
         */
        for(int i=0;i<pair.length;i++){
            for(int j=0;j<pair[i].length;j++){
                pair[i][j]=a[i][j]/jml[j];
            }
        }

        /**
         * Jumlah Baris Normal Pairwise
         */
        for(int i=0;i<n;i++){
            jmlb[i]=pair[i][0]+pair[i][1]+pair[i][2];
        }
        //Total Jumlah Baris Normal Pairwise
        tb = jmlb[0]+jmlb[1]+jmlb[2];

        /**
         * Vektor Eigen
         */
        for(int i=0;i<vrEigKualitas.length;i++){
            vrEigKualitas[i]=jmlb[i]/tb;
        }
    }
    //Getting Value From SeekBar
    public double[][] SeekBarAlternatif(double[] m){
        double p[][] = new double[m.length][m.length];

        //Slider1
        int val1 = sb1.getProgress();
        double realval1;
        for(int i=0;i<=16;i++){
            if(val1 == i && i<8){
                realval1=9.0-val1;
                p[0][1]=realval1;
                p[1][0]=1.0/realval1;
                break;
            }else if(val1 == i && i>8){
                realval1=val1-7.0;
                p[0][1]=1.0/realval1;
                p[1][0]=realval1;
                break;
            }else if(val1 == i && i == 8){
                p[0][1]=1;
                p[1][0]=1;
                break;
            }
        }

        //Slider2
        int val2 = sb2.getProgress();
        double realval2;
        for(int i=0;i<=16;i++){
            if(val2 == i && i<8){
                realval2=9.0-val2;
                p[0][2]=realval2;
                p[2][0]=1.0/realval2;
                break;
            }else if(val2 == i && i>8){
                realval2=val2-7.0;
                p[0][2]=1.0/realval2;
                p[2][0]=realval2;
                break;
            }else if(val2 == i && i == 8){
                p[0][2]=1;
                p[2][0]=1;
                break;
            }
        }

        //Slider3
        int val3 = sb3.getProgress();
        double realval3;
        for(int i=0;i<=16;i++){
            if(val3 == i && i<8){
                realval3=9.0-val3;
                p[1][2]=realval3;
                p[2][1]=1.0/realval3;
                break;
            }else if(val3 == i && i>8){
                realval3=val3-7.0;
                p[1][2]=1.0/realval3;
                p[2][1]=realval3;
                break;
            }else if(val3 == i && i == 8){
                p[1][2]=1;
                p[2][1]=1;
                break;
            }
        }

        p[0][0]=1;
        p[1][1]=1;
        p[2][2]=1;
        return p;
    }

    public void AlternatifWarna(){
        Intent intent=new Intent(this, AlternatifWarna.class);
        startActivity(intent);
    }

    public void SaveVektorEigen(View view){
        SharedPreferences sharedPref = getSharedPreferences("VektorEigen", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("vrEigKualitas1", String.valueOf(vrEigKualitas[0]));
        edit.putString("vrEigKualitas2", String.valueOf(vrEigKualitas[1]));
        edit.putString("vrEigKualitas3", String.valueOf(vrEigKualitas[2]));
        edit.apply();
    }

}