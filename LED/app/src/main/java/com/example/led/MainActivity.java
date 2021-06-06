package com.example.led;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity implements AsyncResponse {
    Button btnON, btnOFF;
    EditText editIP;
    HttpURLConnection connection;
    ImageView imgLampu;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOFF = findViewById(R.id.btn_off_id);
        btnON = findViewById(R.id.btn_on_id);
        editIP = findViewById(R.id.txt_input_ip_id);
        imgLampu = findViewById(R.id.img_lampu_id);

        tombolNyala();
        tombolMati();
    }

    public void tombolNyala(){
        btnON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editIP.getText().toString();
                if(!url.isEmpty()){
                    HttpRequestTask proses = new HttpRequestTask(url, "on");
                    proses.response = MainActivity.this;
                    proses.execute();
                }else{
                    Toast.makeText(MainActivity.this, "Harap Masukkan Alamat IP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void tombolMati(){
        btnOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = editIP.getText().toString();
                if(!url.isEmpty()){
                    HttpRequestTask proses = new HttpRequestTask(url, "off");
                    proses.response = MainActivity.this;
                    proses.execute();
                }else{
                    Toast.makeText(MainActivity.this, "Harap Masukkan Alamat IP!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void processFinish(String output){
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        Log.i("MAIN", output);
        if(output.equals("on")){
            imgLampu.setBackground(ContextCompat.getDrawable(this , R.drawable.bulb_on));
        }else if(output.equals("off")){
            imgLampu.setBackground(ContextCompat.getDrawable(this , R.drawable.bulb_off));
        }else if(output.equals("gagal")){
            Toast.makeText(MainActivity.this, "Komunikasi Gagal", Toast.LENGTH_SHORT).show();
        }
    }
}