package com.gtri.icl.nij.disclose.Models;

import com.gtri.icl.nij.disclose.API.Media;

import java.io.File;

public class MediaLogRecord extends EvidenceRecord
{
    public MediaLogRecord(File file, Media.MediaType mediaType )
    {
        this.file = file;
        this.mediaType = mediaType;
        this.evidenceType = EvidenceType.MEDIA_LOG;
    }
}
