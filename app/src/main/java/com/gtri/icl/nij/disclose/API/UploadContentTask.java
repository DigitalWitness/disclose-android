package com.gtri.icl.nij.disclose.API;

import android.util.Log;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;

import org.json.JSONObject;

// Usage: (executes in background...)
//
// new UploadContentTask( this, submission, new UploadContentAPI.CompletionHandler()
// {
//      @Override
//      public void didComplete( APIResponse response )
//      {
//          // executes in foreground...
//      }
// }).doExecute();

public class UploadContentTask extends AsyncTask<String, Void, APIResponse>
{
    private static final String endPoint = "/submission";

    private Activity context;
    private Submission submission;
    private APICompletionHandler completionHandler;

    public UploadContentTask( Activity context, Submission submission, APICompletionHandler completionHandler )
    {
        this.context = context;
        this.submission = submission;
        this.completionHandler = completionHandler;
    }

    @Override
    protected APIResponse doInBackground( String... params )
    {
        try
        {
            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject( gson.toJson( submission ));

            Log.d( "xxx", jsonObject.toString());

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
        // executes in foreground...

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
