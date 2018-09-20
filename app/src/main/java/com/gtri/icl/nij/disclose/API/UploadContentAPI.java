package com.gtri.icl.nij.disclose.API;

import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.gtri.icl.nij.disclose.Models.Submission;

import org.json.JSONObject;

public class UploadContentAPI extends AsyncTask<String, Void, APIResponse>
{
    private final String endPoint = "/submission";

    public interface CompletionHandler
    {
        void didComplete( APIResponse apiResponse );
    }

    private Activity context;

    public UploadContentAPI setContext( Activity context )
    {
        this.context = context;

        return this;
    }

    private Submission submission;

    public UploadContentAPI setSubmission( Submission submission )
    {
        this.submission = submission;

        return this;
    }

    private CompletionHandler completionHandler;

    public UploadContentAPI setCompletionHandler( CompletionHandler completionHandler )
    {
        this.completionHandler = completionHandler;

        return this;
    }

    public UploadContentAPI doExecute()
    {
        execute();  // triggers doInBackground...

        return this;
    }

    @Override
    protected APIResponse doInBackground( String... params )
    {
        try
        {
            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject( gson.toJson( submission ));

            return APIManager.postRequest( endPoint, jsonObject );
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute( APIResponse apiResponse )
    {
        if (apiResponse.success)
        {
            new FileUploadTask( context, submission, completionHandler ).execute();
        }
        else
        {
            completionHandler.didComplete( apiResponse );
        }
    }
}
