package com.gtri.icl.nij.disclose.AsyncTasks;

import android.util.Log;
import android.os.AsyncTask;
import android.content.Context;

import java.io.File;
import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import com.gtri.icl.nij.disclose.Models.DeviceLogRecord;

public class DeviceLogTask extends AsyncTask<String, Void, DeviceLogRecord>
{
    public interface CompletionHandler
    {
        void didComplete( DeviceLogRecord deviceLogRecord );
    }

    private Context context;
    private CompletionHandler completionHandler;

    public DeviceLogTask(Context context, CompletionHandler completionHandler)
    {
        this.context = context;
        this.completionHandler = completionHandler;
    }

    protected DeviceLogRecord doInBackground(final String... args)
    {
        try
        {
            String filePrefix = "All";

            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.YEAR, -1);

            DateFormat df = new SimpleDateFormat("MM-dd-yyyyHH:mm:ss");

            Date endDate = new Date();
            Date startDate = cal.getTime();

            String fileName = filePrefix + "(" + df.format(startDate) + ")(" + df.format(endDate) + ").log";

            String command = "logcat -vtime -d";

            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(process.getInputStream()));

            int size = 0;
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            String line = "";

            while ((line = bufferedReader.readLine()) != null)
            {
                outputStream.write(line.getBytes());
                size += line.length();
            }

            bufferedReader.close();
            outputStream.close();

            File file = context.getFileStreamPath( fileName );

            return new DeviceLogRecord( file );
        }
        catch (Exception e)
        {
            Log.d( "xxx", e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute( DeviceLogRecord deviceLogRecord )
    {
        // executes in foreground
        completionHandler.didComplete( deviceLogRecord );
    }
}
