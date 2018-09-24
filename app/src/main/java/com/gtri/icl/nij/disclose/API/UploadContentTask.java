package com.gtri.icl.nij.disclose.API;

import android.util.Base64;
import android.util.Log;
import android.app.Activity;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.gtri.icl.nij.disclose.Managers.SignatureManager;

import org.json.JSONObject;

//------------------------------------------------------------------------------
// Usage: (executes in background...)
//
// new UploadContentTask( this, submission, new APICompletionHandler()
// {
//      @Override
//      public void didComplete( APIResponse response )
//      {
//          // executes in foreground...
//      }
// }).execute();
//------------------------------------------------------------------------------

public class UploadContentTask extends AsyncTask<String, Void, APIResponse>
{
    private static final String kEndPoint = "/submission";

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
            submission.signature = Base64.encodeToString( SignatureManager.createSignature( submission.content.media ), Base64.DEFAULT );

            Gson gson = new Gson();

            JSONObject jsonObject = new JSONObject( gson.toJson( submission ));

            Log.d( "xxx", jsonObject.toString());

            return APIManager.postRequest( kEndPoint, jsonObject );
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
