package cn.kk.customview.activity.work.mediasession;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.session.PlaybackState;
import android.os.IBinder;
import android.util.Pair;
import android.widget.TextView;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import cn.kk.base.activity.BaseActivity;
import cn.kk.customview.R;

public class MediaSessionDemoActivity extends BaseActivity {
    private MediaSessionDemoService mDemoService;
    private int songIndex = 0;

    @Override
    public int getLayout() {
        return R.layout.activity_media_session_demo;
    }

    @Override
    protected void doWhenOnCreate() {
        super.doWhenOnCreate();
        bindService();

        initListener();

        registerBroaddcast();
    }

    private void initListener() {
        findViewById(R.id.tv_play).setOnClickListener(v -> {
            playBtn();
        });
        findViewById(R.id.tv_pause).setOnClickListener(v -> {
            pauseBtn();
        });

        findViewById(R.id.tv_pre).setOnClickListener(v -> {
            previousBtn();
        });
        findViewById(R.id.tv_next).setOnClickListener(v -> {
            nextBtn();
        });
    }

    private void updateMedia(int state) {
        Pair<String, String> titleInfo = getTitleInfo();
        mDemoService.updateMediaSession(new MediaInfo(titleInfo.first, titleInfo.second));
        mDemoService.updateMediaSessionPlayState(state, 0);
        upInfo();
    }


    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mediaStateReceiver);
        unbindService(mConnection);
        super.onDestroy();
    }

    private void bindService(){
        bindService(new Intent(this, MediaSessionDemoService.class),mConnection , Context.BIND_AUTO_CREATE);
    }

    private void registerBroaddcast(){
        IntentFilter intentFilter = new IntentFilter(Const.MEDIA_STATE_CHANGE);
        LocalBroadcastManager.getInstance(this).registerReceiver(mediaStateReceiver, intentFilter);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
           mDemoService = ((MediaSessionDemoService.MyBinder)service).getService();
           showInfo("bind service success!");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mDemoService = null;
        }
    };

    private BroadcastReceiver mediaStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Const.MEDIA_STATE_CHANGE)) {
                int state = intent.getIntExtra("state", -1);
                switch (state){
                    case 0:
                        playBtn();
                        break;
                    case 1:
                        pauseBtn();
                        break;
                    case 2:
                        previousBtn();
                        break;
                    case 3:
                        nextBtn();
                        break;

                    default:
                        throw new IllegalStateException("Unexpected value: " + state);
                }
            }
        }
    };

    private void playBtn() {
        showMediaState("play...");
        updateMedia(PlaybackState.STATE_PLAYING);
    }

    private void pauseBtn() {
        showMediaState("pause");
        updateMedia(PlaybackState.STATE_PAUSED);
    }

    private void previousBtn() {
        showMediaState("<- play...");
        songIndex --;
        if (songIndex < 0) {
            songIndex = 0;
        }
        updateMedia(PlaybackState.STATE_PLAYING);
    }

    private void nextBtn() {
        showMediaState("-> playing");
        songIndex ++;
        updateMedia(PlaybackState.STATE_PLAYING);
    }

    private void showInfo(String info){
        TextView tvServiceState = findViewById(R.id.tv_media_info);
        Pair<String, String> titleInfoInit = getTitleInfoInit();
        tvServiceState.setText(info + "\n" + titleInfoInit.first + " - " + titleInfoInit.second);
        tvServiceState.setTextColor(Color.GREEN);
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }

    private void upInfo(){
        TextView tvServiceState = findViewById(R.id.tv_media_info);
        Pair<String, String> titleInfoInit = getTitleInfo();
        tvServiceState.setText(titleInfoInit.first + " - " + titleInfoInit.second);
        tvServiceState.setTextColor(Color.GREEN);
    }

    private Pair<String, String> getTitleInfo(){
        String orderStr = String.valueOf(songIndex + 1);
        return new Pair<>(orderStr + "_title", orderStr + "_album");
    }
    private Pair<String, String> getTitleInfoInit(){
        return new Pair<>("速查手册" ,"准备播放歌曲...");
    }
    private void showMediaState(String info){
      TextView tv = findViewById(R.id.tv_media_state);
      tv.setText(info);
    }


}