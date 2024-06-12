package cn.kk.customview

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import cn.kk.customview.adapter.ViewPagerAdapter
import cn.kk.customview.widget.CountIndicatorView


/**
 * 等级拖动条
 */
class ViewPagerLoopActivity: AppCompatActivity() {
    var fakeSize: Int = 0
    var realSize: Int = 0
    var currentPosition = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrollbar)

        val simpleList = mutableListOf<String>()
        simpleList.add("锄禾日当午")
        simpleList.add("汗滴禾下土")
        simpleList.add("谁知盘中餐")
        simpleList.add("粒粒皆辛苦")


        realSize = simpleList.size
        fakeSize = realSize + 2

        val tempList = transformListAndAddTwo(simpleList as ArrayList<String>)

        val simpleAdapter = ViewPagerAdapter(tempList)

        val defaultIndex = 2

        val indicatorView = findViewById<CountIndicatorView>(R.id.indicatorView)
        indicatorView.numberSelected = 0
        indicatorView.numberOfItems = fakeSize - 2

        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.apply {
            adapter = simpleAdapter
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            setCurrentItem(defaultIndex, false)

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)


                    if (position >= 2){
                        indicatorView.numberSelected = position - 2
                    } else {
                        if (position == 1){
                            indicatorView.numberSelected = realSize -1
                        }
                        if (position == 0){
                            indicatorView.numberSelected = realSize -2
                        }
                    }
                    currentPosition = position

                    printLog(position.toString())
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        if (currentPosition == 0) {
                            viewPager.setCurrentItem(fakeSize - 2, false)
                        } else if (currentPosition == fakeSize - 1) {
                            // 滑动到了最后一个了
                            viewPager.setCurrentItem(1, false)
                        }
                    } else if (state == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == fakeSize) {
                        viewPager.setCurrentItem(2, false)
                    }
                }
            })
        }
    }

    /**
     * add two lasts item at beginning on the new array
     * @param itemList
     * @return
     */
    fun transformListAndAddTwo(itemList: ArrayList<String>): ArrayList<String> {
        val size: Int = itemList.size
        val listTemp: ArrayList<String> = ArrayList(size + 2)
        for (iPL in 0..size + 2) {
            listTemp.add(itemList[(iPL + size - 2) % size])
        }
        return listTemp
    }

    fun printLog(msg: String){
        Log.d(this.javaClass.simpleName, "printLog: $msg")
    }
}