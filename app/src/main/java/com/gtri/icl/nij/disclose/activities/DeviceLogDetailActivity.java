package com.gtri.icl.nij.disclose.activities;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.view.WindowManager;
import android.text.method.ScrollingMovementMethod;

import com.gtri.icl.nij.disclose.R;

import java.io.File;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class DeviceLogDetailActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_device_log_detail);

        setCustomTitle("");

        TextView textView = (TextView)findViewById( R.id.textView );
        textView.setMovementMethod(new ScrollingMovementMethod());

        Intent intent = getIntent();

        String pathName = intent.getStringExtra( "PathName" );

        try
        {
            File file = new File(pathName);
            FileInputStream fileInputStream = new FileInputStream(file);
            textView.setText( getString(fileInputStream ));
            fileInputStream.close();
        }
        catch (Exception e)
        {
        }
    }

    public static String getString(InputStream is) throws Exception
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null)
        {
            sb.append(line).append("\n");
        }

        reader.close();

        return sb.toString();
    }}
