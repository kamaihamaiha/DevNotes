package cn.kk.base.media

import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import cn.kk.base.R
import cn.kk.base.activity.BaseActivity

class MyPlayerActivity: BaseActivity() {

    val player by lazy {
        ExoPlayer.Builder(this).build()
    }
    override fun getLayout(): Int {
        return R.layout.activity_my_player
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val playView = findViewById<PlayerView>(R.id.player_view)
        val myExoPlayer = MyExoPlayer(this)
        playView.player = myExoPlayer.player // Bind the player to the view.

        // Build the media item.
        val mediaItem = MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/play.mp3")
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        // Start the playback.
        player.play()

    }
}