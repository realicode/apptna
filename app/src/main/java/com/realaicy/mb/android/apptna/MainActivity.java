package com.realaicy.mb.android.apptna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String ver="1";

        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1=new Date();
        String t1=format.format(d1);

        TextView tvVer=(TextView)findViewById(R.id.realver); //R.id.tv是xml布局里textview对应的id
        tvVer.setText("版本: " + ver +"\t 现在时间: " +t1 );
    }
}
