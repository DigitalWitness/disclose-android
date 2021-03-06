package com.gtri.icl.nij.disclose.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
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
import com.gtri.icl.nij.disclose.Models.DeviceLogRecord;
import com.gtri.icl.nij.disclose.Managers.EvidenceManager;
import com.gtri.icl.nij.disclose.AsyncTasks.DeviceLogTask;
import com.gtri.icl.nij.disclose.RecyclerViewSupport.RecyclerViewAdapter;
import com.gtri.icl.nij.disclose.RecyclerViewSupport.RecyclerItemTouchHelper;

public class DeviceLogActivity extends BaseActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener, RecyclerViewAdapter.RecyclerViewAdapterDelegate
{
    public static final int REQUEST_PICK_IMAGE = 1;

    private Button addButton;
    private RecyclerView recyclerView;
    private RelativeLayout noDataRelativeLayout;
    private LinearLayout recyclerViewLinearLayout;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setCustomTitle("");

        setContentView(R.layout.activity_device_log);

        getSupportActionBar().hide();

        addButton = (Button)findViewById(R.id.addButton);
        noDataRelativeLayout = (RelativeLayout)findViewById(R.id.noDataRelativeLayout);
        recyclerViewLinearLayout = (LinearLayout)findViewById(R.id.recyclerViewLinearLayout);

        if (EvidenceManager.sharedInstance().deviceLogRecords.size() > 0)
        {
            addButton.setVisibility(View.GONE);
            noDataRelativeLayout.setVisibility(View.GONE);
            recyclerViewLinearLayout.setVisibility(View.VISIBLE);
        }

        recyclerViewAdapter = new RecyclerViewAdapter( this, this, EvidenceManager.sharedInstance().deviceLogRecords );

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager( new LinearLayoutManager( this ));
        recyclerView.setItemAnimator( new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter( recyclerViewAdapter );

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        addButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                addButton.setVisibility(View.GONE);

                new DeviceLogTask( DeviceLogActivity.this, new DeviceLogTask.CompletionHandler()
                {
                    @Override
                    public void didComplete( DeviceLogRecord deviceLogRecord )
                    {
                        if (deviceLogRecord != null)
                        {
                            noDataRelativeLayout.setVisibility( View.GONE );
                            recyclerViewLinearLayout.setVisibility( View.VISIBLE );

                            EvidenceManager.sharedInstance().deviceLogRecords.add( deviceLogRecord );
                        }
                        else
                        {
                            Toast.makeText( DeviceLogActivity.this, "Device Log Creation Failed.", Toast.LENGTH_LONG).show();

                            addButton.setVisibility( View.VISIBLE );
                        }
                    }
                }).execute();
            }
        });

        Button backButton = (Button)findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();

                overridePendingTransition( R.animator.slide_from_left, R.animator.slide_to_right );
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position)
    {
        if (viewHolder instanceof RecyclerViewAdapter.RecyclerViewHolder)
        {
            recyclerViewAdapter.removeItem( viewHolder.getAdapterPosition());

            if (EvidenceManager.sharedInstance().deviceLogRecords.size() == 0)
            {
                addButton.setVisibility(View.VISIBLE);
                noDataRelativeLayout.setVisibility( View.VISIBLE );
                recyclerViewLinearLayout.setVisibility( View.GONE );
            }
        }
    }

    public void onListItemClicked(int position)
    {
        DeviceLogRecord deviceLogRecord = (DeviceLogRecord)EvidenceManager.sharedInstance().deviceLogRecords.get(position);

        Intent intent = new Intent(this, DeviceLogDetailActivity.class);
        intent.putExtra( "PathName", deviceLogRecord.file.getAbsolutePath());

        startActivity(intent);

        overridePendingTransition( R.animator.slide_from_right, R.animator.slide_to_left );

        recyclerViewAdapter.notifyItemChanged(position);
    }
}
