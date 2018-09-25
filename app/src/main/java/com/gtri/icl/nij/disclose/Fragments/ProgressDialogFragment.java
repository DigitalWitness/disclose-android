package com.gtri.icl.nij.disclose.Fragments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.support.v4.app.DialogFragment;

import com.gtri.icl.nij.disclose.R;

public class ProgressDialogFragment extends DialogFragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_progress_dialog, container, false);

        return view;
    }
}
