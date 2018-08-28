package com.gtri.icl.nij.disclose.activities;

import android.view.Gravity;
import android.widget.TextView;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.gtri.icl.nij.disclose.R;

public class BaseActivity extends AppCompatActivity
{
    public void setCustomTitle( String title )
    {
        TextView textView = new TextView(this);
        textView.setTextSize(22);
        textView.setText(title);
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
