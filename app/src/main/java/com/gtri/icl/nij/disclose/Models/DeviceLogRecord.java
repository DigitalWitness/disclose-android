package com.gtri.icl.nij.disclose.Models;

import java.io.File;

public class DeviceLogRecord extends EvidenceRecord
{
    public File file;

    public DeviceLogRecord(File file )
    {
        this.file = file;

        this.evidenceType = EvidenceType.DEVICE_LOG;
    }
}