package com.gtri.icl.nij.disclose.activities;

import android.util.Log;
import android.os.Bundle;
import android.view.Window;
import android.app.Activity;
import android.view.Surface;
import android.content.Intent;
import android.view.TextureView;
import android.media.MediaPlayer;
import android.view.WindowManager;
import android.graphics.SurfaceTexture;

import com.gtri.icl.nij.disclose.R;

/**
 * Created by rmitchell64 on 2/9/17.
 */

public class VideoLogDetailActivity extends Activity implements TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener
{
    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private TextureView mTextureView;

    private String mVideoFileName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = this.getIntent();
        mVideoFileName = intent.getStringExtra( "fileName" );

        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        this.getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_video_log_detail);

        mTextureView = (TextureView)findViewById(R.id.textureView);

        mTextureView.setSurfaceTextureListener(this);

        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        releaseMediaPlayer();

        finish();
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface)
    {
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height)
    {
        mSurface = new Surface(surfaceTexture);

        try
        {
            mMediaPlayer.reset();
            mMediaPlayer.setLooping( false );
            mMediaPlayer.setSurface( mSurface );
            mMediaPlayer.setDataSource( mVideoFileName );
            mMediaPlayer.setOnPreparedListener( this );
            mMediaPlayer.setOnCompletionListener( this );
            mMediaPlayer.setOnErrorListener( this );
            mMediaPlayer.prepareAsync();
        }
        catch (Exception e)
        {
            Log.w( "xxx", "Media load failed");
        }
    }

    public void onPrepared(MediaPlayer mp)
    {
        mMediaPlayer.start();
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface)
    {
        if (mSurface != null)
        {
            releaseMediaPlayer();
            mSurface.release();
            mSurface = null;
        }
        return true;
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height)
    {
    }

    public void onCompletion(MediaPlayer mp)
    {
        releaseMediaPlayer();

        finish();
    }

    @Override
    public boolean onError(MediaPlayer player, int what, int extra)
    {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
            Log.w("xxx", "No media in stream");
        } else if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
            Log.w("xxx", "Media service died unexpectedly");
        } else {
            Log.w("xxx", "Unknown media error");
        }
        return true;
    }

    private void releaseMediaPlayer()
    {
        if (mMediaPlayer != null)
        {
            mMediaPlayer.reset();
            mMediaPlayer.setSurface(null);
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
