package com.gtri.icl.nij.disclose.activities;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.view.Gravity;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.gtri.icl.nij.disclose.R;

public class MainActivity extends AppCompatActivity
{
    private Button submitButton;
    private LinearLayout mediaLogLinearLayout;
    private LinearLayout socialLogLinearLayout;
    private LinearLayout deviceLogLinearLayout;
    private LinearLayout messageLogLinearLayout;

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

        socialLogLinearLayout = (LinearLayout)findViewById(R.id.socialLogLinearLayout);

        socialLogLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );

                Intent intent = new Intent(MainActivity.this, SocialLogActivity.class);
                startActivity(intent);

                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // Device Log Tap Handler

        deviceLogLinearLayout = (LinearLayout)findViewById(R.id.deviceLogLinearLayout);

        deviceLogLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );

                Intent intent = new Intent(MainActivity.this, DeviceLogActivity.class);
                startActivity(intent);

                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // Message Tap Handler

        messageLogLinearLayout = (LinearLayout)findViewById(R.id.messageLogLinearLayout);

        messageLogLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );

                Intent intent = new Intent(MainActivity.this, MessageLogActivity.class);
                startActivity(intent);

                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // Photo/Video Tap Handler

        mediaLogLinearLayout = (LinearLayout)findViewById(R.id.mediaLogLinearLayout);

        mediaLogLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );

                Intent intent = new Intent(MainActivity.this, MediaLogActivity.class);
                startActivity(intent);

                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // submit button handler

        submitButton = (Button)findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
                Log.d( "xxx", "Submit Button Tapped" );
            }
        });
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        submitButton.setEnabled(true);
        mediaLogLinearLayout.setEnabled(true);
        socialLogLinearLayout.setEnabled(true);
        deviceLogLinearLayout.setEnabled(true);
        messageLogLinearLayout.setEnabled(true);
    }
}
