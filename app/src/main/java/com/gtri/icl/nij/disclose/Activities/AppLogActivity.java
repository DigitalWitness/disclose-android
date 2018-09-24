package com.gtri.icl.nij.disclose.Activities;

import android.util.Log;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.gtri.icl.nij.disclose.R;
import com.gtri.icl.nij.disclose.Managers.EvidenceManager;
import com.gtri.icl.nij.disclose.RecyclerViewSupport.RecyclerViewAdapter;
import com.gtri.icl.nij.disclose.RecyclerViewSupport.RecyclerItemTouchHelper;

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

        getSupportActionBar().hide();

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
