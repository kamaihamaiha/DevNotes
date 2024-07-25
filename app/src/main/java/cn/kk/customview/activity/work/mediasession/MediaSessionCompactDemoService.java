package cn.kk.customview.activity.work.mediasession;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MediaSessionCompactDemoService extends Service {

    private MediaSessionCompat mediaSession;
    private MediaMetadataCompat.Builder mmDataBuilder;
    private PlaybackStateCompat.Builder playStatBuilder;

    private MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder {

        public MediaSessionCompactDemoService getService(){
            return MediaSessionCompactDemoService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initMediaSession();
    }

    @Override
    public void onDestroy() {
        releaseMediaSession();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    private void initMediaSession(){
         mediaSession = new MediaSessionCompat(this, "demo");
         mmDataBuilder = new MediaMetadataCompat.Builder();
         playStatBuilder = new PlaybackStateCompat.Builder();

        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS | MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setPlaybackState(playStatBuilder
                        .setActions(PlaybackStateCompat.ACTION_PLAY | PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_NEXT | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
                .setState(PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN, 0f).build());

        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                sendLocalBoardCast(MediaState.play);
            }

            @Override
            public void onPause() {
                sendLocalBoardCast(MediaState.pause);

            }

            @Override
            public void onStop() {

            }

            @Override
            public void onSkipToNext() {
                sendLocalBoardCast(MediaState.next);

            }

            @Override
            public void onSkipToPrevious() {
                sendLocalBoardCast(MediaState.pre);


            }

        });

        mediaSession.setActive(true);
    }

    private void releaseMediaSession(){
        if (mediaSession != null) {
            mediaSession.release();
        }
    }

    public void updateMediaSessionPlayState(int playbackState, long position) {
        mediaSession.setPlaybackState(playStatBuilder.setState(playbackState, position, 0f).build());
    }

    public void updateMediaSession(MediaInfo mediaInfo){
        mmDataBuilder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, mediaInfo.title);
        mmDataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM, mediaInfo.album);
        mediaSession.setMetadata(mmDataBuilder.build());
    }

    public void sendLocalBoardCast(MediaState mediaState){
        Intent intent = new Intent(Const.MEDIA_STATE_CHANGE);
        intent.putExtra("state", mediaState.value);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
