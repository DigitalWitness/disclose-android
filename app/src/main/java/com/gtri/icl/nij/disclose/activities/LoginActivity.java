package com.gtri.icl.nij.disclose.activities;

import android.os.Bundle;
import android.app.Activity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.animation.AlphaAnimation;

import com.gtri.icl.nij.disclose.R;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        {
            ImageView imageView = findViewById( R.id.imageView );
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setStartOffset(1000);
            alphaAnimation.setFillAfter(true);
            imageView.startAnimation(alphaAnimation);
        }
        {
            RelativeLayout relativeLayout = findViewById( R.id.relativeLayout );
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setStartOffset(2000);
            alphaAnimation.setFillAfter(true);
            relativeLayout.startAnimation(alphaAnimation);
        }
    }
}
