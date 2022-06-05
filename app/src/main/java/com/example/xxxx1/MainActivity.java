package com.example.xxxx1;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "pttt";
    int currentYear=2022;
    boolean isValidParent;
    ImageView imageView;
    TextView detectedText;
    Button btn_detect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isValidParent=false;
        imageView = findViewById(R.id.img);
        detectedText = findViewById(R.id.detectedText);
        btn_detect = findViewById(R.id.button_detect);

        btn_detect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                detect();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void detect() {

        TextRecognizer recognizer = new TextRecognizer.Builder(MainActivity.this).build();


        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();


        Frame frame = new Frame.Builder().setBitmap(bitmap).build();

        SparseArray<TextBlock> sparseArray =  recognizer.detect(frame);


        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0;i < sparseArray.size(); i++){
            TextBlock tx = sparseArray.get(i);
            String str = tx.getValue();
            if(str.contains("."))
            {
                Log.d(TAG, "detect: "+str);
                isDateValid(str);
            }
            stringBuilder.append(str);
        }

        if (isValidParent==true){
            detectedText.setText("לאחר בדיקה,הינך מוגדר במערכת כהורה.");
        }
        else {
            detectedText.setText("מצטערים, אך לאחר בדיקה לא תוכל לקבל הרשאות של הורה.");
        }

    }

    private void isDateValid(String str) {
        String[] separated = str.split("\\.");
       try {
           Log.d(TAG, "isDateValid: "+separated[0]);
           Log.d(TAG, "isDateValid: "+separated[1]);
           Log.d(TAG, "isDateValid: "+separated[2]);
           String years = separated[2];
           int year = 0;
           try {
               year = Integer.parseInt(years);

               if (currentYear-year <= 6){
                   int x= currentYear-year;
                   Log.d(TAG, "x "+x);
                   isValidParent=true;
                   return;
               }

           } catch(NumberFormatException nfe) {
               System.out.println("Could not parse " + nfe);
           }

       }
       catch (Exception e){
           e.getMessage();
}

    }
}