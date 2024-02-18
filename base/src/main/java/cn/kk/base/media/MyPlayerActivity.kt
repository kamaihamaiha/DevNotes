package cn.kk.base.media

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
import androidx.media3.ui.PlayerView
import cn.kk.base.R
import cn.kk.base.UIHelper
import cn.kk.base.activity.BaseActivity

class MyPlayerActivity: BaseActivity() {

    private val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

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
        initPlayer()

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
        val playView = findViewById<PlayerView>(R.id.player_view)
        playView.player = myExoPlayer
        myExoPlayer.play()
    }

    private fun getHlsMediaSource(): MediaSource {
        // Create a HLS media source pointing to a playlist uri.
        val mediaUrl = intent.getStringExtra(INTENT_MEDIA_URL_KEY)?:""
        return HlsMediaSource.Factory(dataSourceFactory).
        createMediaSource(MediaItem.fromUri(mediaUrl))
    }
}