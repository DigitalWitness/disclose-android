package com.gtri.icl.nij.disclose.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.gtri.icl.nij.disclose.R;

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

        Intent intent = getIntent();

        String pathName = intent.getStringExtra( "PathName" );

        textView.setText( pathName );
    }
}
