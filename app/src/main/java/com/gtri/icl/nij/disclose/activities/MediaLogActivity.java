package com.gtri.icl.nij.disclose.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gtri.icl.nij.disclose.R;
import com.gtri.icl.nij.disclose.Models.MediaRecord;
import com.gtri.icl.nij.disclose.RecyclerViewAdapter;
import com.gtri.icl.nij.disclose.Models.EvidenceManager;
import com.gtri.icl.nij.disclose.RecyclerItemTouchHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MediaLogActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener
{
    public static final int REQUEST_PICK_IMAGE = 1;

    private ArrayList<File> listItems;
    private RecyclerView recyclerView;
    private RelativeLayout noMediaRelativeLayout;
    private LinearLayout recyclerViewLinearLayout;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_media_log);

        setCustomTitle( "Media Log" );

        noMediaRelativeLayout = (RelativeLayout)findViewById(R.id.noMediaRelativeLayout);
        recyclerViewLinearLayout = (LinearLayout)findViewById(R.id.recyclerViewLinearLayout);

        listItems = new ArrayList<>();

        for (MediaRecord mediaRecord : EvidenceManager.sharedInstance().mediarRecords)
        {
            listItems.add( mediaRecord.file );
        }

        if (listItems.size() > 0)
        {
            noMediaRelativeLayout.setVisibility(View.GONE);
            recyclerViewLinearLayout.setVisibility(View.VISIBLE);
        }

        recyclerViewAdapter = new RecyclerViewAdapter( this, listItems );

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager( new LinearLayoutManager( this ));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter( recyclerViewAdapter );

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        Button addButton = (Button)findViewById(R.id.addButton);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("*/*");
//                startActivityForResult( Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setType("*/*");

                startActivityForResult(Intent.createChooser(intent, "Select Media"), REQUEST_PICK_IMAGE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK && requestCode == REQUEST_PICK_IMAGE)
        {
            Uri uri = data.getData();

            if (uri != null)
            {
                File file = copyFile( uri );

                listItems.add( file );

                EvidenceManager.sharedInstance().mediarRecords.add( new MediaRecord( file ));

                noMediaRelativeLayout.setVisibility(View.GONE);
                recyclerViewLinearLayout.setVisibility(View.VISIBLE);

                String mimeType = getContentResolver().getType(uri);

                if(mimeType.startsWith("image"))
                {
                }
                else if (mimeType.startsWith("video"))
                {
                }
                else
                {
                }
            }
        }
    }

    public File copyFile(Uri uri)
    {
        File dstFile = null;

        try
        {
            File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            downloadsDir.mkdirs();

            File discloseDir = new File( downloadsDir, "Disclose/" );
            discloseDir.mkdirs();

            File srcFile = new File( uri.getPath());

            dstFile = new File( discloseDir,  srcFile.getName() + ".jpg");

            InputStream in = getContentResolver().openInputStream(uri);

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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (viewHolder instanceof RecyclerViewAdapter.RecyclerViewHolder)
        {
            MediaRecord mediaRecord = EvidenceManager.sharedInstance().mediarRecords.get(position);

            EvidenceManager.sharedInstance().mediarRecords.remove( mediaRecord );

            recyclerViewAdapter.removeItem(viewHolder.getAdapterPosition());

            if (listItems.size() == 0)
            {
                noMediaRelativeLayout.setVisibility(View.VISIBLE);
                recyclerViewLinearLayout.setVisibility(View.GONE);
            }
        }
    }
}
