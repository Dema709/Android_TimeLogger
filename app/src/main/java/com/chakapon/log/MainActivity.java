package com.chakapon.log;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    final String LOG_TAG = "log";
    TextView textView;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView2);
        editText=findViewById(R.id.editText3);
        requestAppPremissions();

        readFile(getFilename());
    }

    public void onclick(View v){
        switch (v.getId()){
            case R.id.DayStart:
                writeInFile(getFilename(),"Вход в концерн");
                break;
            case R.id.DayEnd:
                writeInFile(getFilename(),"Выход из концерна");
                break;
            case R.id.ObedStart:
                writeInFile(getFilename(),"Вышел на обед");
                break;
            case R.id.ObedEnd:
                writeInFile(getFilename(),"Зашёл с обеда");
                break;
            case R.id.AddComment:
                writeInFile(getFilename(),editText.getText().toString());
                break;
        }
        readFile(getFilename());
    }

    private void requestAppPremissions(){
        if (hasReadPremissions()&&hasWritePremissions()){
            Log.wtf(LOG_TAG,"HasPermissions");
            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },1);
    }

    private boolean hasReadPremissions(){
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePremissions(){
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED);
    }

    private String getCurrentTime(){
        String h = Calendar.getInstance().get(Calendar.YEAR)+".";
        if (Calendar.getInstance().get(Calendar.MONTH)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.MONTH)+".";
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+" ";

        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.HOUR_OF_DAY)+":";
        if (Calendar.getInstance().get(Calendar.MINUTE)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.MINUTE)+":";
        if (Calendar.getInstance().get(Calendar.SECOND)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.SECOND);
        return h;
    }

    private String getFilename(){
        String h = Calendar.getInstance().get(Calendar.YEAR)+".";
        if (Calendar.getInstance().get(Calendar.MONTH)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.MONTH)+".";
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH)<10) h=h+"0";
        h=h+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        h=h+".txt";
        return h;
    }

    private void writeInFile(String filename, String action){
        try {
            String pathname=Environment.getExternalStorageDirectory().toString()+"/Android/LogVEGA/"+filename;
            File f = new File(pathname);
            FileWriter fw;
            if (f.exists()){
                fw = new FileWriter(f,true);//Дозапись
            }
            else{
                fw = new FileWriter(f);
            }
            PrintWriter writer = new PrintWriter(fw);
            writer.println(getCurrentTime()+" "+action);
            writer.flush();
            writer.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void readFile(String filename){
        try {
            String pathname=Environment.getExternalStorageDirectory().toString()+"/Android/LogVEGA/"+filename;
            File f = new File(pathname);
            FileInputStream inputStream = new FileInputStream(f);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            StringBuilder stringBuilder = new StringBuilder();
            String textViewText="";
            while ((line=bufferedReader.readLine())!=null){
                textViewText=textViewText+line+"\n";
                stringBuilder.append(line+"\n");
            }
            textView.setText(textViewText);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
