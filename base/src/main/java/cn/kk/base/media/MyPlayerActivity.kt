package cn.kk.base.media

import androidx.media3.common.MediaItem
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.ui.PlayerView
import cn.kk.base.R
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


        initPlayer()

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