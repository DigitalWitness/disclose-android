package com.gtri.icl.nij.disclose.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.gtri.icl.nij.disclose.Models.AppLogRecord;
import com.gtri.icl.nij.disclose.RecyclerViewAdapter;
import com.gtri.icl.nij.disclose.Managers.FileManager;
import com.gtri.icl.nij.disclose.RecyclerItemTouchHelper;
import com.gtri.icl.nij.disclose.Managers.EvidenceManager;

import java.io.File;

public class AppLogActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, RecyclerViewAdapter.RecyclerViewAdapterDelegate
{
    public static final int REQUEST_PICK_IMAGE = 1;

    private RecyclerView recyclerView;
    private RelativeLayout noDataRelativeLayout;
    private LinearLayout recyclerViewLinearLayout;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_app_log);

        setCustomTitle("");

        noDataRelativeLayout = (RelativeLayout)findViewById(R.id.noDataRelativeLayout);
        recyclerViewLinearLayout = (LinearLayout)findViewById(R.id.recyclerViewLinearLayout);

        if (EvidenceManager.sharedInstance().appLogRecords.size() > 0)
        {
            noDataRelativeLayout.setVisibility(View.GONE);
            recyclerViewLinearLayout.setVisibility(View.VISIBLE);
        }

        recyclerViewAdapter = new RecyclerViewAdapter( this, this, EvidenceManager.sharedInstance().appLogRecords );

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
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setType("*/*");

                startActivityForResult(Intent.createChooser(intent, "Select App"), REQUEST_PICK_IMAGE);
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
                String mimeType = getContentResolver().getType(uri);

                String extension = "";

                if (mimeType.startsWith("image"))
                {
                    extension = ".jpg";
                }
                else if (mimeType.startsWith("video"))
                {
                    extension = ".mp4";
                }

                File file = FileManager.copyFile( uri, extension, this );

                EvidenceManager.sharedInstance().appLogRecords.add( new AppLogRecord( file ));

                noDataRelativeLayout.setVisibility(View.GONE);
                recyclerViewLinearLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (viewHolder instanceof RecyclerViewAdapter.RecyclerViewHolder)
        {
            recyclerViewAdapter.removeItem( viewHolder.getAdapterPosition());

            if (EvidenceManager.sharedInstance().appLogRecords.size() == 0)
            {
                noDataRelativeLayout.setVisibility( View.VISIBLE );
                recyclerViewLinearLayout.setVisibility( View.GONE );
            }
        }
    }

    public void onListItemClicked(int position)
    {
        Log.d( "xxx", "onListItemClicked: " + position );
    }
}
