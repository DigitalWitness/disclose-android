package com.gtri.icl.nij.disclose.Models;

import com.gtri.icl.nij.disclose.API.Media;

import java.io.File;

public class EvidenceRecord
{
    public enum EvidenceType
    {
        APP_LOG,
        MEDIA_LOG,
        DEVICE_LOG,
        MESSAGE_LOG
    }

    public File file;
    public EvidenceType evidenceType;
    public Media.MediaType mediaType;
}
