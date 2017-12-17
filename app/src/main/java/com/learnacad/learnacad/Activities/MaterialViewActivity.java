package com.learnacad.learnacad.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.learnacad.learnacad.R;

import java.io.File;

import es.voghdev.pdfviewpager.library.PDFViewPager;
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter;

public class MaterialViewActivity extends AppCompatActivity {

    PDFViewPager pdfViewPager;
    String title;
    String path;
    public static final String MIME_TYPE_PDF = "application/pdf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_view);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        title = intent.getStringExtra("title");

        if(Build.VERSION.SDK_INT < 21){

            File file = new File(path + "/" + title);
            Intent pdfViewIntent = new Intent(Intent.ACTION_VIEW);
            pdfViewIntent.setDataAndType(Uri.fromFile(file),MIME_TYPE_PDF);
            startActivity(pdfViewIntent);
        }else {

            showPdf(path,title);
        }


    }

    private void showPdf(String path, String title) {

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < title.length(); ++i){

            if(title.charAt(i) == ' '){

                sb.append("%20");
            }else{

                sb.append(title.charAt(i));

            }
        }

        Log.d("checkPDF", "showPdf: " + sb.toString());


        File file = new File(path + "/" + sb.toString());

        pdfViewPager = new PDFViewPager(this,file.getAbsolutePath());
        setContentView(pdfViewPager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(pdfViewPager != null)
            ((PDFPagerAdapter) pdfViewPager.getAdapter()).close();
    }
}
