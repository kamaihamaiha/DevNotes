package cn.kk.customview.ui.system.material

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import cn.kk.customview.R

/**
 * 使用 Toolbar
 */
class ToolbarActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
//        supportActionBar?.title = null
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.title
        toolbar.setNavigationOnClickListener { finish() }
    }
}