package com.gtri.icl.nij.disclose.API;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.error.VolleyError;
import com.gtri.icl.nij.disclose.Models.Submission;
import com.android.volley.request.SimpleMultiPartRequest;

import org.json.JSONObject;
import org.json.JSONException;

public class FileUploadTask extends AsyncTask<String, Void, String>
{
    public static final String FILES_SUBMISSION_URL = "http://nij-disclose-stsd.gtri.gatech.edu/api/files";

    private String filePath;
    private String submissionId;
    public RequestQueue mRequestQueue;
    private UploadContentAPI.CompletionHandler completionHandler;

    public FileUploadTask(Activity activity, Submission submission, UploadContentAPI.CompletionHandler completionHandler)
    {
        this.filePath = submission.filePath;
        this.completionHandler = completionHandler;
        this.submissionId = submission.submission_id;
        this.mRequestQueue = Volley.newRequestQueue(activity);
    }

    @Override
    protected String doInBackground(String... params)
    {
        SimpleMultiPartRequest smr = buildSMR();

        smr.addStringParam("submission_id", submissionId);
        smr.addStringParam("submissionType", "Photo/Video");
        smr.addFile("files", filePath);

        mRequestQueue.add( smr );

        return null;
    }

    private SimpleMultiPartRequest buildSMR()
    {
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest( Request.Method.POST, FILES_SUBMISSION_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if (message.contains( "success" ))
                            {
                                completionHandler.didComplete( new APIResponse( true, "", jsonObject ));
                            }
                            else
                            {
                                completionHandler.didComplete( new APIResponse( false, "Upload Failed, invalid response: " + jsonObject.toString(), null));
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                            completionHandler.didComplete( new APIResponse( false, "Upload Failed: " + e.getMessage(), null ));
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError e)
                    {
                        e.printStackTrace();
                        completionHandler.didComplete( new APIResponse( false, "Upload Failed: " + e.getMessage(), null ));
                    }
                });

        return smr;
    }
}
