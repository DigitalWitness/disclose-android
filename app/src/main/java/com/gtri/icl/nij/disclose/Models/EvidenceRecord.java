package com.gtri.icl.nij.disclose.Models;

public class EvidenceRecord
{
    public enum EvidenceType
    {
        APP_LOG,
        MEDIA_LOG,
        DEVICE_LOG,
        MESSAGE_LOG
    }

    public EvidenceType evidenceType;
}
