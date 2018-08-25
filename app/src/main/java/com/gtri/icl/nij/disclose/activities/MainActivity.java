package com.gtri.icl.nij.disclose.activities;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.gtri.icl.nij.disclose.R;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // center title text, yeah all this for that...

        TextView textView = new TextView(this);
        textView.setTextSize(22);
        textView.setText("DISCLOSE");
        textView.setTextColor( 0xffffffff );
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER );

        getSupportActionBar().setCustomView(textView, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        // Social App Tap Handler

        ((LinearLayout)findViewById(R.id.appLogLinearLayout)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "App Log Tapped" );
            }
        });

        // Device Log Tap Handler

        ((LinearLayout)findViewById(R.id.deviceLogLinearLayout)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "Device Log Tapped" );
            }
        });

        // Message Tap Handler

        ((LinearLayout)findViewById(R.id.messagesLinearLayout)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "Messages Tapped" );
            }
        });

        // Photo/Video Tap Handler

        ((LinearLayout)findViewById(R.id.mediaLinearLayout)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "Media Tapped" );
            }
        });

        // submit button handler

        ((Button)findViewById(R.id.submitButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "Submit Button Tapped" );
            }
        });
    }
}
