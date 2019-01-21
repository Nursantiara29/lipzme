package com.spk.santi.lipzme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AHPActivity extends AppCompatActivity {

    /**
     *Deklarasi Objek yang akan digunakan
     */
    EditText ed1,ed2;
    SeekBar sb1,sb2,sb3;
    TextView tv1,tv2,tv3;
    Spinner sp1,sp2,sp3;
    Button btn;

    int n=3;    //Total Kriteria
    int NUMBER_COMPARISON=(n*n-n)/2; //Perhitungan Jumlah Perbandingan Berdasarkan Kriteria
    double tb;  //Variabel Total Baris
    double tvg; //Variabel Total Vektor Eigen

    double [] jml=new double[n]; //Variabel Jumlah per-Kolom Matriks
    double [] jmlb=new double[n]; //Variabel Jumlah Baris Normal Pairwise
    double [] vrEig=new double[n]; //Variabel Vektor Eigen
    double [][] pair=new double[n][n]; //Variabel untuk menampung Normal Pairwise
    double [][] a=new double[n][n]; //Variabel untuk menampung matriks
    double [] p=new double[NUMBER_COMPARISON];//digunakan untuk menampung nilai banding

    int xPos; //Variabel untuk menampung value yang digunakan Thumbnail SeekBar
    //double[] m = new double[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahp);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Melakukan Force Fullscreen pada layar
        getWindow().setTitle("Pilih Lipstik"); //Merubah Title Menjadi Pilih Lipstik

        //SeekBar 1 & Text Value 1
        sb1 = (SeekBar) findViewById(R.id.seekBarKW); //Mendeklarasikan Objek SeekBar berdasarkan id yang ada pada xml.
        tv1 = (TextView) findViewById(R.id.TxtValKW); //Mendeklarasikan Objek TextView berdasarkan id yang ada pada xml.
        sb1.setProgress(8);

        //SeekBar 2 & Text Value 2
        sb2 = (SeekBar) findViewById(R.id.seekBarKT);
        tv2 = (TextView) findViewById(R.id.TxtValKT);
        sb2.setProgress(8);

        /**
         * SeekBar 3 & Text Value 3
         */
        sb3 = (SeekBar) findViewById(R.id.seekBarWT);
        tv3 = (TextView) findViewById(R.id.TxtValWT);
        sb3.setProgress(8);
        
        //Spinner
        //Mendeklarasikan Objek Spinner berdasarkan id yang ada pada xml.
        sp1=(Spinner)findViewById(R.id.SpinnerLipstik1);
        sp2=(Spinner)findViewById(R.id.SpinnerLipstik2);
        sp3=(Spinner)findViewById(R.id.SpinnerLipstik3);

        ed1 = (EditText) findViewById(R.id.TitlePilihLipstik); ed1.setClickable(false); ed1.setInputType(InputType.TYPE_NULL);
        ed2 = (EditText) findViewById(R.id.TxtBandingkan); ed2.setClickable(false); ed2.setInputType(InputType.TYPE_NULL);

        LipstikSeekBar();
        LipstikSpinner();

        ShowAlert();

        //Button Event OnClick Listener
        /**
         * Bila Button Bandingkan di tekan maka perintah yang ada pada Function akan di eksekusi
         */
        btn = (Button) findViewById(R.id.BtnBandingkan);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp1.getSelectedItem().toString() == "Pilih Lipstik" || sp2.getSelectedItem().toString() == "Pilih Lipstik"
                        || sp3.getSelectedItem().toString() == "Pilih Lipstik"){
                    Toast warning =Toast.makeText(getApplicationContext(),"Pilih Lipstik Terlebih Dahulu",Toast.LENGTH_SHORT);
                    warning.show();
                }else{
                    AHP();
                    SaveVektorEigen(v);
                    AlternatifKualitas();
                }
            }
        });

    }

    //Jika Tombol Back pada Smartphone ditekan maka akan dikerjakan
    @Override
    public void onBackPressed(){
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
    /**
     * SeekBar Method & Function
     */
    //Eksekusi dari SeekBar1Val, SeekBar2Val dan SeekBar3Val
    public void LipstikSeekBar(){
        //Thumbnail SeekBar 1 Listener
        sb1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SeekBar1Val(progress);
                //SeekBarAHP(m); *Digunakan saat Debugging saja. Blm Dihapus
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
    //Value of Thumbnail SeekBar 1, Memberikan value pada Thumbnail di SeekBar
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
    //Value of Thumbnail SeekBar 2, Memberikan value pada Thumbnail di SeekBar
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
    //Value of Thumbnail SeekBar 3, Memberikan value pada Thumbnail di SeekBar
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
     * Spinner Method
     */
    public void LipstikSpinner(){
        String lipstik[] = {"Pilih Lipstik", "Goban","Polka","MOB","Wardah","LT Pro"}; //Isi String Spinner

        //Deklarasi ArrayAdapter untuk Spinner dan Memasukan String Kedalam Dropdown Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, lipstik);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        final Toast warning =Toast.makeText(getApplicationContext(),"Lipstik Tidak Bisa Sama",Toast.LENGTH_SHORT);

        sp1.setAdapter(adapter); //Melakukan setAdapter untuk Spinner dengan adapter (Lihat atas)
        sp2.setAdapter(adapter);
        sp3.setAdapter(adapter);

        //Melakukan Perbandingan apakah pilihan spinner 1 sama dengan spinner lainnya
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp1.getSelectedItem().toString() == sp2.getSelectedItem().toString()){
                    warning.show();
                    sp1.setSelection(0,true);
                }else if(sp1.getSelectedItem().toString() == sp3.getSelectedItem().toString()){
                    warning.show();
                    sp1.setSelection(0,true);
                }else if(sp1.getSelectedItem().toString() == "Pilih Lipstik"){
                    warning.cancel();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Melakukan Perbandingan apakah pilihan spinner 2 sama dengan spinner lainnya
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp2.getSelectedItem().toString() == sp1.getSelectedItem().toString()){
                    warning.show();
                    sp2.setSelection(0,true);
                }else if(sp2.getSelectedItem().toString() == sp3.getSelectedItem().toString()){
                    warning.show();
                    sp2.setSelection(0,true);
                }else if(sp2.getSelectedItem().toString() == "Pilih Lipstik"){
                    warning.cancel();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Melakukan Perbandingan apakah pilihan spinner 3 sama dengan spinner lainnya
        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(sp3.getSelectedItem().toString() == sp1.getSelectedItem().toString()){
                    warning.show();
                    sp3.setSelection(0,true);
                }else if(sp3.getSelectedItem().toString() == sp2.getSelectedItem().toString()){
                    warning.show();
                    sp3.setSelection(0,true);
                }else if(sp3.getSelectedItem().toString() == "Pilih Lipstik"){
                    warning.cancel();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * AHP Method
     */
    public void AHP(){
        a=SeekBarAHP(p);

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
        for(int i=0;i<vrEig.length;i++){
            vrEig[i]=jmlb[i]/tb;
        }
        //Total Vektor Eigen
        tvg = vrEig[0]+vrEig[1]+vrEig[2];

    }
    //Getting Value From SeekBar untuk metode AHP
    public double[][] SeekBarAHP(double[] m){
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

    public void ShowAlert(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AHPActivity.this);
        alertBuilder.setIcon(R.drawable.ic_slide3);
        alertBuilder.setTitle(R.string.alertTitle);
        alertBuilder.setMessage(R.string.alertMsg);
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Mengerti", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    /**
     * Alternatif Activity untuk Pindah Activity
     */
    public void AlternatifKualitas(){
        Intent intent=new Intent(this, AlternatifKualitas.class);
        startActivity(intent);
        finish();
    }

    //Menyimpan Value Perhitungan dalam file VektorEigen
    public void SaveVektorEigen(View view){
        SharedPreferences sharedPref = getSharedPreferences("VektorEigen", Context.MODE_PRIVATE);

        SharedPreferences.Editor edit = sharedPref.edit();
        edit.putString("vrEig1", String.valueOf(vrEig[0]));
        edit.putString("vrEig2", String.valueOf(vrEig[1]));
        edit.putString("vrEig3", String.valueOf(vrEig[2]));
        edit.putString("ProgressK1",String.valueOf(a[0][1]));
        edit.putString("ProgressW1",String.valueOf(a[1][0]));
        edit.putString("ProgressK2",String.valueOf(a[0][2]));
        edit.putString("ProgressT1",String.valueOf(a[2][0]));
        edit.putString("ProgressW2",String.valueOf(a[1][2]));
        edit.putString("ProgressT2",String.valueOf(a[2][1]));
        edit.putString("CB1",sp1.getSelectedItem().toString());
        edit.putString("CB2",sp2.getSelectedItem().toString());
        edit.putString("CB3",sp3.getSelectedItem().toString());
        edit.apply();
    }
}