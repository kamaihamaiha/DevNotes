package cn.kk.base.media

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer

class MyExoPlayer(val ctx: Context) {
    val player by lazy {
        ExoPlayer.Builder(ctx).build()
    }
    init {

    }


}