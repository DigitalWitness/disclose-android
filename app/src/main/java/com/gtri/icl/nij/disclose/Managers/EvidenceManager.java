package com.gtri.icl.nij.disclose.Managers;

import com.gtri.icl.nij.disclose.Models.EvidenceRecord;

import java.util.ArrayList;

public class EvidenceManager
{
    public ArrayList<EvidenceRecord> appLogRecords;
    public ArrayList<EvidenceRecord> mediaLogRecords;
    public ArrayList<EvidenceRecord> deviceLogRecords;
    public ArrayList<EvidenceRecord> messageLogRecords;

    private static EvidenceManager instance = new EvidenceManager();

    private EvidenceManager()
    {
        clearAll();
    }

    public void clearAll()
    {
        appLogRecords = new ArrayList<>();
        mediaLogRecords = new ArrayList<>();
        deviceLogRecords = new ArrayList<>();
        messageLogRecords = new ArrayList<>();
    }

    public static EvidenceManager sharedInstance()
    {
        return instance;
    }
}
