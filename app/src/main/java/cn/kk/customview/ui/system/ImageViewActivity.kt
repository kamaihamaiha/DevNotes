package cn.kk.customview.ui.system

import android.widget.ImageView
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import kotlinx.android.synthetic.main.activity_imageview.*

class ImageViewActivity: BaseActivity() {
    var index = 1
    override fun getLayout(): Int {
       return R.layout.activity_imageview
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        btn_next.setOnClickListener {
            when (index % 8) {
                1 -> {
                    imageView.scaleType = ImageView.ScaleType.FIT_XY
                    type.text = "FIT_XY"
                }
                2 -> {
                    imageView.scaleType = ImageView.ScaleType.FIT_START
                    type.text = "FIT_START"
                }
                3 -> {
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                    type.text = "FIT_CENTER"
                }
                4 -> {
                    imageView.scaleType = ImageView.ScaleType.FIT_END
                    type.text = "FIT_END"
                }
                5 -> {
                    imageView.scaleType = ImageView.ScaleType.CENTER
                    type.text = "CENTER"
                }
                6 -> {
                    imageView.scaleType = ImageView.ScaleType.CENTER_CROP
                    type.text = "CENTER_CROP"
                }
                7 -> {
                    imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
                    type.text = "CENTER_INSIDE"
                }
            }
            index++
        }
    }
}