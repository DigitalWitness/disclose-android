package com.gtri.icl.nij.disclose.Models;

import com.gtri.icl.nij.disclose.API.Media;

import java.io.File;

public class DeviceLogRecord extends EvidenceRecord
{
    public DeviceLogRecord(File file )
    {
        this.file = file;

        this.mediaType = Media.MediaType.SYSLOG;
        this.evidenceType = EvidenceType.DEVICE_LOG;
    }
}