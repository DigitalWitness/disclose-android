package com.gtri.icl.nij.disclose.Models;

import java.io.File;

public class AppLogRecord extends EvidenceRecord
{
    public AppLogRecord(File file )
    {
        this.file = file;

        this.evidenceType = EvidenceType.APP_LOG;
    }
}