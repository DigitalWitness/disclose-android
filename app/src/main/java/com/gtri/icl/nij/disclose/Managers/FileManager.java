package com.gtri.icl.nij.disclose.Managers;

import android.net.Uri;
import android.util.Log;
import android.os.Environment;
import android.content.Context;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;

public class FileManager
{
    public static File copyFile(Uri uri, String extension, Context context )
    {
        File dstFile = null;

        try
        {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            downloadsDir.mkdirs();

            File discloseDir = new File( downloadsDir, "Disclose/" );
            discloseDir.mkdirs();

            File srcFile = new File( uri.getPath());

            dstFile = new File( discloseDir,  srcFile.getName() + extension);

            InputStream in = context.getContentResolver().openInputStream(uri);

            try
            {
                FileOutputStream out = new FileOutputStream(dstFile);

                try
                {
                    byte[] buf = new byte[1024];
                    int len;

                    while ((len = in.read(buf)) > 0)
                    {
                        out.write(buf, 0, len);
                    }
                }
                catch( Exception e )
                {
                    Log.d( "xxx", e.getLocalizedMessage());
                }
                finally
                {
                    out.close();
                }
            }
            catch( Exception e )
            {
                Log.d( "xxx", e.getLocalizedMessage());
            }
            finally
            {
                in.close();
            }
        }
        catch( Exception e )
        {
            Log.d( "xxx", e.getLocalizedMessage());
        }

        return dstFile;
    }
}
