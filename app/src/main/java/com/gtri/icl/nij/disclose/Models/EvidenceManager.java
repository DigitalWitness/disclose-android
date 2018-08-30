package com.gtri.icl.nij.disclose.Models;

import java.util.ArrayList;

public class EvidenceManager
{
    private static EvidenceManager instance = new EvidenceManager();

    private EvidenceManager()
    {
        mediarRecords = new ArrayList<>();
    }

    public static EvidenceManager sharedInstance()
    {
        return instance;
    }

    public ArrayList<MediaRecord> mediarRecords;
}
