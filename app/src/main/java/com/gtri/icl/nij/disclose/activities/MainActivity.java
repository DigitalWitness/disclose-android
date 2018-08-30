package com.gtri.icl.nij.disclose.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
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

import com.gtri.icl.nij.disclose.Models.EvidenceManager;
import com.gtri.icl.nij.disclose.R;

public class MainActivity extends BaseActivity
{
    private Button submitButton;

    private LinearLayout mediaLogLinearLayout;
    private LinearLayout socialLogLinearLayout;
    private LinearLayout deviceLogLinearLayout;
    private LinearLayout messageLogLinearLayout;

    private TextView mediaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        setCustomTitle( "DISCLOSE" );

        requestAllPermissions();

        mediaTextView = (TextView)findViewById(R.id.mediaTextView);

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
                startActivity( intent );

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

    public static final int REQUEST_READ_PHONE_STATE = 10;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 4040;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;
    private static final String[] permissions = new String[] {Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS};

    private static final int REQUEST_WRITE_EXTERNAL_STORAGE=1;


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

    private static void requestWritePermission(final Context context)
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity)context,Manifest.permission.WRITE_EXTERNAL_STORAGE))
        {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            new AlertDialog.Builder(context)
                    .setMessage("Mother may I")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            ActivityCompat.requestPermissions((Activity) context,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    }).show();

        }
        else
            {
            // permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        }
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

        mediaTextView.setText( "Photo/Video (" + EvidenceManager.sharedInstance().mediarRecords.size() + ")" );
    }
}
