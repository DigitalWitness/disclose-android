package com.gtri.icl.nij.disclose.activities;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.content.pm.PackageManager;

import com.gtri.icl.nij.disclose.R;
import com.gtri.icl.nij.disclose.Managers.EvidenceManager;

public class MainActivity extends BaseActivity
{
    private Button submitButton;

    private LinearLayout appLogLinearLayout;
    private LinearLayout mediaLogLinearLayout;
    private LinearLayout deviceLogLinearLayout;
    private LinearLayout messageLogLinearLayout;

    private TextView appLogTextView;
    private TextView mediaLogTextView;
    private TextView deviceLogTextView;
    private TextView messageLogTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        setCustomTitle( "DISCLOSE" );

        requestAllPermissions();

        appLogTextView = (TextView)findViewById(R.id.appLogTextView);
        mediaLogTextView = (TextView)findViewById(R.id.mediaLogTextView);
        deviceLogTextView = (TextView)findViewById(R.id.deviceLogTextView);
        messageLogTextView = (TextView)findViewById(R.id.messageLogTextView);

        // Social App Tap Handler

        appLogLinearLayout = (LinearLayout)findViewById(R.id.socialLogLinearLayout);

        appLogLinearLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );

                Intent intent = new Intent(MainActivity.this, AppLogActivity.class);
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
                startActivity( intent );

                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // submit button handler

        submitButton = (Button)findViewById(R.id.submitButton);

        submitButton.setEnabled( false );

        submitButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                v.setEnabled( false );
            }
        });
    }

    private static final int REQUEST_READ_PHONE_STATE = 10;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE=1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4040;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String[] permissions = new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS};

    private void requestAllPermissions()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
            }

            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions, PERMISSIONS_REQUEST_READ_CONTACTS);
            }

            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            }

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        appLogLinearLayout.setEnabled(true);
        mediaLogLinearLayout.setEnabled(true);
        deviceLogLinearLayout.setEnabled(true);
        messageLogLinearLayout.setEnabled(true);

        appLogTextView.setText( "App Logs\n (" + EvidenceManager.sharedInstance().appLogRecords.size() + ")" );
        mediaLogTextView.setText( "Media Logs\n (" + EvidenceManager.sharedInstance().mediaLogRecords.size() + ")" );
        deviceLogTextView.setText( "Device Logs\n (" + EvidenceManager.sharedInstance().deviceLogRecords.size() + ")" );
        messageLogTextView.setText( "Message Logs\n (" + EvidenceManager.sharedInstance().messageLogRecords.size() + ")" );

        int count = EvidenceManager.sharedInstance().appLogRecords.size()
                + EvidenceManager.sharedInstance().mediaLogRecords.size()
                + EvidenceManager.sharedInstance().deviceLogRecords.size()
                + EvidenceManager.sharedInstance().messageLogRecords.size();

        submitButton.setEnabled( count > 0 );
    }
}
