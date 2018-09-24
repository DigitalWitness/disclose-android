package com.gtri.icl.nij.disclose.Activities;

import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.view.WindowManager;
import android.provider.MediaStore;
import android.media.ThumbnailUtils;
import android.graphics.BitmapFactory;

import com.gtri.icl.nij.disclose.R;

public class MediaLogDetailActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_media_log_detail);

        setCustomTitle("");

        Intent intent = getIntent();

        String pathName = intent.getStringExtra( "PathName" );

        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail( pathName, MediaStore.Video.Thumbnails.MICRO_KIND );

        if (bitmap == null)
        {
            bitmap = BitmapFactory.decodeFile( pathName );
        }

        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setImageBitmap( bitmap );
    }
}
