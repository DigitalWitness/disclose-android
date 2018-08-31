package com.gtri.icl.nij.disclose.Models;

import java.io.File;

public class MediaLogRecord extends EvidenceRecord
{
    public File file;

    public MediaLogRecord(File file )
    {
        this.file = file;

        this.evidenceType = EvidenceType.MEDIA_LOG;
    }
}
