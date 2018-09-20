package com.gtri.icl.nij.disclose.API;

import org.json.JSONObject;

public class APIResponse
{
    public String error = "";
    public JSONObject jsonObject;
    public Boolean success = false;

    public APIResponse( Boolean success, String error, JSONObject jsonObject )
    {
        this.error = error;
        this.success = success;
        this.jsonObject = jsonObject;
    }}
