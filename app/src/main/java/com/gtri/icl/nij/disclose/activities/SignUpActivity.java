package com.gtri.icl.nij.disclose.activities;

import android.os.Bundle;
import android.view.WindowManager;

import com.gtri.icl.nij.disclose.R;

public class SignUpActivity  extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_sign_up);

        setCustomTitle("Sign Up!");
    }
}
