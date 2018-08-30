package com.gtri.icl.nij.disclose;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>
{
    Activity context;
    ArrayList<File> listItems;

    public RecyclerViewAdapter( Activity context, ArrayList<File> listItems )
    {
        this.context = context;
        this.listItems = listItems;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView titleTextView;
        public LinearLayout foregroundView;
        public RelativeLayout backgroundView;

        public RecyclerViewHolder( View itemView )
        {
            super(itemView);

            imageView = (ImageView)itemView.findViewById( R.id.imageView );
            titleTextView = (TextView)itemView.findViewById( R.id.titleTextView );
            backgroundView = (RelativeLayout) itemView.findViewById( R.id.background_view );
            foregroundView = (LinearLayout) itemView.findViewById( R.id.foreground_view );
        }
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType )
    {
        LayoutInflater layoutInflater = LayoutInflater.from( context );

        View view = layoutInflater.inflate( R.layout.list_item_media, parent, false );

        return new RecyclerViewHolder( view );
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position )
    {
        File file = listItems.get(position);

        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail( file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND );

        if (bitmap != null)
        {
            recyclerViewHolder.imageView.setImageBitmap( bitmap );
        }
        else
        {
            recyclerViewHolder.imageView.setImageURI( Uri.fromFile(file));
        }

        recyclerViewHolder.titleTextView.setText( file.getAbsolutePath());
    }

    @Override
    public int getItemCount()
    {
        return listItems.size();
    }

    public void removeItem(int position)
    {
        listItems.remove(position);

        notifyItemRemoved(position);
    }
}