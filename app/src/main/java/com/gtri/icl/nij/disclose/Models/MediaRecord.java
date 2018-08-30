package com.gtri.icl.nij.disclose.Models;

import android.media.Image;
import android.net.Uri;

import java.io.File;

public class MediaRecord
{
    public File file;
    public Image thumbnailImage;

    public MediaRecord( File file )
    {
        this.file = file;
    }
}
