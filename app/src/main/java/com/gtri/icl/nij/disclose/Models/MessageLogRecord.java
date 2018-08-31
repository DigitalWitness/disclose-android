package com.gtri.icl.nij.disclose.Models;

import java.io.File;

public class MessageLogRecord extends EvidenceRecord
{
    public File file;

    public MessageLogRecord(File file )
    {
        this.file = file;

        this.evidenceType = EvidenceType.MESSAGE_LOG;
    }
}