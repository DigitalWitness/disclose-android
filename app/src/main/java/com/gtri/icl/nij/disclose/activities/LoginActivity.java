package com.gtri.icl.nij.disclose.activities;

import android.view.View;
import android.os.Bundle;
import android.widget.Toast;
import android.app.Activity;
import android.view.KeyEvent;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.view.inputmethod.EditorInfo;
import android.view.animation.AlphaAnimation;

import com.gtri.icl.nij.disclose.R;

public class LoginActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        // animate out the spash screen
        {
            ImageView imageView = findViewById( R.id.imageView );
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(1500);
            alphaAnimation.setStartOffset(500);
            alphaAnimation.setFillAfter(true);
            imageView.startAnimation(alphaAnimation);
        }

        // animate in the login screen
        {
            RelativeLayout relativeLayout = findViewById( R.id.relativeLayout );
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(1500);
            alphaAnimation.setStartOffset(1000);
            alphaAnimation.setFillAfter(true);
            relativeLayout.startAnimation(alphaAnimation);
        }

        // email entry handler

        final EditText passwordEditText = (EditText)findViewById(R.id.passwordEditText);

        ((EditText)findViewById(R.id.emailEditText)).setOnEditorActionListener( new EditText.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    passwordEditText.requestFocus();
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        // password entry handler

        ((EditText)findViewById(R.id.passwordEditText)).setOnEditorActionListener( new EditText.OnEditorActionListener()
        {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
            {
                if (actionId == EditorInfo.IME_ACTION_DONE)
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
                    return true;
                }
                else
                {
                    return false;
                }
            }
        });

        // signup tap handler

        ((TextView)findViewById(R.id.signUpTextView)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );
            }
        });

        // forgot password tap handler

        ((TextView)findViewById(R.id.forgotPasswordTextView)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(LoginActivity.this, "Please check your email for further instructions.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
