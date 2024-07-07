package cn.kk.base.media

import android.view.KeyEvent
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import cn.kk.base.R
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity

class MyPlayerActivity: BaseActivity() {

    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

    lateinit var videoSurface: SurfaceView
    val myExoPlayer by lazy {
        ExoPlayer.Builder(this).build()
    }
    override fun getLayout(): Int {
        return R.layout.activity_my_player
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        UIHelper.setStatusBarDark(this@MyPlayerActivity)
        val rootView = findViewById<View>(R.id.rootView)
        ViewCompat.setOnApplyWindowInsetsListener(rootView, object :
            OnApplyWindowInsetsListener {
            override fun onApplyWindowInsets(
                v: View,
                windowInsets: WindowInsetsCompat
            ): WindowInsetsCompat {
                val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.updatePadding(top = insets.top)
                return WindowInsetsCompat.CONSUMED
            }
        })

        videoSurface = findViewById(R.id.video_surface)
        videoSurface.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                myExoPlayer.setVideoSurfaceHolder(holder)
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                myExoPlayer.clearVideoSurface()
            }
        })

        initPlayer()

    }

    var moveTag = false // 上下左右 按键就是true，点击事件就是 false
    var actionTag = -1
    val click_tag = 0
    val left_tag = 1
    val right_tag = 2
    val up_tag = 3
    val down_tag = 4

    var startX = 0
    var startY = 0

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.action?.let {
            when(it){
                MotionEvent.ACTION_DOWN -> {
                    moveTag = false
                    startX = ev.x.toInt()
                    startY = ev.y.toInt()
                }
                MotionEvent.ACTION_UP -> {
                    if (!moveTag) {
                        actionTag = click_tag
                    }
                    startControlPlay()
                    moveTag = false
                }
                MotionEvent.ACTION_MOVE -> {
                    moveTag = true
                    // 判断移动方向
                    if (ev.y.toInt() == startY) {
                        // 水平移动: x越来越小
                        if (ev.x.toInt() < startX) {
                            actionTag = right_tag
                        } else if (ev.x.toInt() > startX) {
                            actionTag = left_tag
                        }

                    } else if (ev.x.toInt() == startX) {
                        // 垂直移动
                        if (ev.y.toInt() < startY) {
                            actionTag = down_tag
                        } else if (ev.y.toInt() > startY) {
                            actionTag = up_tag
                        }
                    }

                }
                else -> {

                }
            }
        }
//        return true
        return super.dispatchTouchEvent(ev)
    }

    private fun getActionMsg(action: Int): String {
        return when(action){
            click_tag -> "play/pause"
            left_tag -> "left"
            right_tag -> "right"
            up_tag -> "up"
            down_tag -> "down"
            else -> "unknown"
        }
    }

    private fun startControlPlay(){
        when(actionTag){
            click_tag -> {
                if (myExoPlayer.isPlaying) {
                    myExoPlayer.pause()
                } else {
                    myExoPlayer.play()
                }
            }
            left_tag -> {
                myExoPlayer.seekTo(myExoPlayer.currentPosition - 10000)
            }
            right_tag -> {
                myExoPlayer.seekTo(myExoPlayer.currentPosition + 10000)
            }
            up_tag -> {
                myExoPlayer.seekTo(myExoPlayer.currentPosition + 60000)
            }
            down_tag -> {
                myExoPlayer.seekTo(myExoPlayer.currentPosition - 60000)
            }
            else -> {

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)

    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        return super.dispatchKeyEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (myExoPlayer.isPlaying) {
            myExoPlayer.pause()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        myExoPlayer.stop()
        myExoPlayer.release()
    }

    private fun initPlayer(){
        myExoPlayer.apply {
                setMediaSource(getHlsMediaSource())
                prepare()
//                addListener(playerListener)
            }
//        val playView = findViewById<PlayerView>(R.id.player_view)
//        playView.player = myExoPlayer
        myExoPlayer.play()
    }

    private fun getHlsMediaSource(): MediaSource {
        // Create a HLS media source pointing to a playlist uri.
        val mediaUrl = intent.getStringExtra(INTENT_MEDIA_URL_KEY)?:""
        return HlsMediaSource.Factory(dataSourceFactory).
        createMediaSource(MediaItem.fromUri(mediaUrl))
    }
}