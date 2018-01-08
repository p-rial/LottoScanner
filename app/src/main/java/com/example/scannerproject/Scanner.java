package com.example.scannerproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;
    MediaPlayer beapSound;
    ArrayList<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanner);
        Intent intent = getIntent();
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        // zXingScannerView.setAspectTolerance(0.5f);
        beapSound = MediaPlayer.create(this,R.raw.censor_beep_01);
        data = new ArrayList<String>();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }



    @Override
    protected void onPause() {
        super.onPause();
        //zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        beapSound.start();
        Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_SHORT).show();
        data.add(result.getText());
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        zXingScannerView.resumeCameraPreview(Scanner.this);
                        Log.i("tag", "This'll run 1 sec later");
                    }
                }, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String all = "";
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent= new Intent();
                for (String txt : data){
                    all += txt + "\n";
                }
                intent.putExtra("param", all);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}