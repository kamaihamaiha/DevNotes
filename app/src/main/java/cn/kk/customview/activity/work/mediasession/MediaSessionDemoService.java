package cn.kk.customview.activity.work.mediasession;

import android.app.Service;
import android.content.Intent;
import android.media.MediaMetadata;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MediaSessionDemoService extends Service {

    private MediaSession mediaSession;
    private MediaMetadata.Builder mmDataBuilder;
    private PlaybackState.Builder playStatBuilder;

    private MyBinder myBinder = new MyBinder();

    public class MyBinder extends Binder {

        public MediaSessionDemoService getService(){
            return MediaSessionDemoService.this;
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
         mediaSession = new MediaSession(this, "demo");
         mmDataBuilder = new MediaMetadata.Builder();
         playStatBuilder = new PlaybackState.Builder();

        mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession.setPlaybackState(playStatBuilder
                        .setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE | PlaybackState.ACTION_SKIP_TO_NEXT | PlaybackState.ACTION_SKIP_TO_PREVIOUS)
                .setState(PlaybackState.STATE_NONE, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 0f).build());

        mediaSession.setCallback(new MediaSession.Callback() {
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
        mmDataBuilder.putString(MediaMetadata.METADATA_KEY_TITLE, mediaInfo.title);
        mmDataBuilder.putString(MediaMetadata.METADATA_KEY_ALBUM, mediaInfo.album);
        mediaSession.setMetadata(mmDataBuilder.build());
    }

    public void sendLocalBoardCast(MediaState mediaState){
        Intent intent = new Intent(Const.MEDIA_STATE_CHANGE);
        intent.putExtra("state", mediaState.value);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

}
