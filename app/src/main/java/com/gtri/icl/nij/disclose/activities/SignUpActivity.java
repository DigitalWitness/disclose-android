package com.gtri.icl.nij.disclose.activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import android.graphics.Typeface;
import android.view.WindowManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.gtri.icl.nij.disclose.R;

public class SignUpActivity  extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        TextView textView = new TextView(this);
        textView.setTextSize(22);
        textView.setText("Sign Up!");
        textView.setTextColor( 0xffffffff );
        textView.setGravity(Gravity.CENTER);
        textView.setTypeface(null, Typeface.BOLD);

        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER );

        getSupportActionBar().setCustomView(textView, params);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }

    @Override public void onBackPressed()
    {
        super.onBackPressed();

        finish();

        overridePendingTransition( R.animator.slide_from_left, R.animator.slide_to_right );
    }
}