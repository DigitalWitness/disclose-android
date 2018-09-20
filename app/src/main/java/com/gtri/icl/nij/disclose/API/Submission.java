package com.gtri.icl.nij.disclose.API;

import java.util.UUID;

//------------------------------------------------------------------------------
// This main reason for this class is to support the creation of the JSON
// object (in the desired format) that is uploaded.  The JSON creation is
// based on the GSON library.
//
// For example:
//
//   JSONObject jsonObject = new JSONObject( new Gson().toJson( submission ));
//
// will serialize this entire object to a JSONObject.
//------------------------------------------------------------------------------

public class Submission
{
    public float latitude = 0;
    public float longitude = 0;
    public String user = "user";
    public String signature = "signature";
    public Content content = new Content();
    public String submission_id = UUID.randomUUID().toString();
}
