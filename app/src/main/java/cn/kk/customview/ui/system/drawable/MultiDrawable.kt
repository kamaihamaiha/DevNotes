package cn.kk.customview.ui.system.drawable

import android.graphics.drawable.TransitionDrawable
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import cn.kk.base.activity.BaseActivity
import cn.kk.base.bean.WikiModel
import cn.kk.base.dialog.WikiBottomDialog
import cn.kk.customview.R
import java.util.*

/**
 * 可显示多种 Drawable
 */
class MultiDrawable: BaseActivity(), androidx.appcompat.widget.Toolbar.OnMenuItemClickListener {

    val MENU_ID_MORE = 0;

    var level = 0
    lateinit var mContext: BaseActivity

    val drawableInfoWiki: Array<String> by lazy {
        Arrays.asList(resources.getStringArray(R.array.drawable_info))[0]
    }
    val drawableNames: Array<String> by lazy {
        Arrays.asList(resources.getStringArray(R.array.drawable_name))[0]
    }
    companion object {
        val TYPE_BITMAP_DRAWABLE = 0
        val TYPE_SHAPE_DRAWABLE = 1
        val TYPE_LAYER_DRAWABLE = 2
        val TYPE_STATELIST_DRAWABLE = 3
        val TYPE_LEVELLIST_DRAWABLE = 4
        // 两个 Drawable 之间淡入淡出效果
        val TYPE_TRANSITION_DRAWABLE = 5
        // 将其他 Drawable 内嵌到自己当中
        val TYPE_INSET_DRAWABLE = 7
        // 根据自己的等级，将指定的 Drawable 缩放到一定比例
        val TYPE_SCALE_DRAWABLE = 8
        val TYPE_ROTATE_DRAWABLE = 9
        // 根据自己当前的等级来裁剪另一个 Drawable; 裁剪方向方向通过 android:clipOrientation 和 android:gravity 两个属性共同控制
        val TYPE_Clip_DRAWABLE = 10
        // 自定义 Drawable，通常不这么用
        val TYPE_CUSTOM_DRAWABLE = 11
    }

    override fun getLayout(): Int {
        return R.layout.activity_multi_drawable
    }

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()
        mContext = this

        // toolbar 上加 menu，单独使用 toolbar，
        baseToolbar?.inflateMenu(R.menu.menu_wiki)
        baseToolbar?.title = intent.getStringExtra(INTENT_TITLE_KEY)
        baseToolbar?.setNavigationIcon(R.drawable.icon_back)
        baseToolbar?.setNavigationOnClickListener { finish() }
        baseToolbar?.setOnMenuItemClickListener(this)

        val view_drawable_bg = findViewById<View>(R.id.view_drawable_bg)
        val et_input = findViewById<EditText>(R.id.et_input)
        val btn_multi_drawable = findViewById<Button>(R.id.btn_multi_drawable)
        val btn_rotate = findViewById<Button>(R.id.btn_rotate)
        when (ui_type) {
            // 对应的是 <bitmap> 标签
            TYPE_BITMAP_DRAWABLE -> view_drawable_bg.setBackgroundResource(R.drawable.kk_bitmap_drawable)
            // 对应的是 <shape> 标签
            TYPE_SHAPE_DRAWABLE -> { /* shape drawable 实体是：GradientDrawable*/ view_drawable_bg.setBackgroundResource(R.drawable.kk_shape_drawable) }
            // 对应的是 <layer-list> 标签
            TYPE_LAYER_DRAWABLE -> {  et_input.visibility = View.VISIBLE }
            // 对应的是 <selector> 标签
            TYPE_STATELIST_DRAWABLE -> {  btn_multi_drawable.visibility = View.VISIBLE }
            // 对应的是 <level-list> 标签
            TYPE_LEVELLIST_DRAWABLE -> {
                val drawable =
                    ContextCompat.getDrawable(mContext, R.drawable.kk_level_list_drawable)
                btn_multi_drawable.apply {
                visibility = View.VISIBLE
                    setOnClickListener {
                     background = drawable?.apply { setLevel(++level % 3) }
                        text = "level: ".plus(level % 3)
                    }

            } }
            TYPE_TRANSITION_DRAWABLE -> {
                btn_multi_drawable.visibility = View.VISIBLE
                btn_multi_drawable.setBackgroundResource(R.drawable.kk_transition_drawable)
                (btn_multi_drawable.background  as TransitionDrawable).startTransition(2000)
            }
            TYPE_INSET_DRAWABLE -> view_drawable_bg.setBackgroundResource(R.drawable.kk_inset_drawable)
            TYPE_SCALE_DRAWABLE -> {
                val scaleDrawable = ContextCompat.getDrawable(mContext, R.drawable.kk_scale_drawable)
                // level 必须大于 0
                scaleDrawable?.level = 1
                view_drawable_bg.apply {
                    visibility = View.VISIBLE
                    background = scaleDrawable
                }
            }
            TYPE_ROTATE_DRAWABLE -> {
                btn_rotate.visibility = View.VISIBLE
                val anim = AnimationUtils.loadAnimation(mContext, R.drawable.kk_rotate_drawable)
                btn_rotate.startAnimation(anim)
            }
            TYPE_Clip_DRAWABLE -> {
                view_drawable_bg.visibility = View.VISIBLE
                val clipDrawable = ContextCompat.getDrawable(mContext, R.drawable.kk_clip_drawable)
                // 0 - 10000; 0: 完全裁剪，10000：不裁剪
                clipDrawable?.level = 5000
                view_drawable_bg.background = clipDrawable
            }
            else -> {

            }
        }
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (ui_type > drawableNames.size - 1){
            showToast("暂无...")
            return true
        }
        WikiBottomDialog(mContext, WikiModel(drawableNames[ui_type], drawableInfoWiki[ui_type])).show()
        return super.onOptionsItemSelected(item!!)
    }
}