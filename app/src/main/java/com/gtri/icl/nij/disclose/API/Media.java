package com.gtri.icl.nij.disclose.API;

public class Media
{
    public enum MediaType
    {
        PHOTO,
        VIDEO,
        SYSLOG
    }

    public String filePath;
    public String mediaType;

    public Media( MediaType mediaType, String filePath )
    {

        this.filePath = filePath;
        this.mediaType = mediaType.toString();
    }
}
