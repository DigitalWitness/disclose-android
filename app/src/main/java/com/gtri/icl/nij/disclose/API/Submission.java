package com.gtri.icl.nij.disclose.API;

import java.util.ArrayList;
import java.util.UUID;

public class Submission
{
    public float latitude = 0;
    public float longitude = 0;
    public String user = "user";
    public String signature = "signature";
    public Content content = new Content();
    public String submission_id = UUID.randomUUID().toString();
}
