package com.gtri.icl.nij.disclose.API;

import android.app.Activity;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;

import org.json.JSONObject;
import org.json.JSONException;

//
// this class is the multi part request companion to the UploadContentTask
// and is responsible for uploading image/video/file content ~after~ the
// successful upload of the json meta data is uploaded first.
//

public class FileUploadTask extends AsyncTask<String, Void, String>
{
    public static final String kSubmissionUrl = "http://nij-disclose-stsd.gtri.gatech.edu/api/files";

    private Submission submission;
    private RequestQueue requestQueue;
    private APICompletionHandler completionHandler;

    public FileUploadTask(Activity activity, Submission submission, APICompletionHandler completionHandler)
    {
        this.submission = submission;
        this.completionHandler = completionHandler;
        this.requestQueue = Volley.newRequestQueue(activity);
    }

    @Override
    protected String doInBackground(String... params)
    {
        for (Media media : submission.content.media)
        {
            SimpleMultiPartRequest simpleMultiPartRequest = createSimpleMultiPartRequest();

            simpleMultiPartRequest.addFile("files", media.filePath );
            simpleMultiPartRequest.addStringParam("submissionType", "Photo/Video" );
            simpleMultiPartRequest.addStringParam("submission_id", submission.submission_id );

            requestQueue.add( simpleMultiPartRequest );
        }

        return null;
    }

    private SimpleMultiPartRequest createSimpleMultiPartRequest()
    {
        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest( Request.Method.POST, kSubmissionUrl,

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
                                completionHandler.didComplete( new APIResponse( true, null, jsonObject ));
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

        return simpleMultiPartRequest;
    }
}
