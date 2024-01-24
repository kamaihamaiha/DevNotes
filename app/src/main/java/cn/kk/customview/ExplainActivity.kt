package cn.kk.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cn.kk.customview.widget.ExpandView

class ExplainActivity: AppCompatActivity() {
val TEST_DATA = "《西游记》是中国神魔小说的经典之作，达到了古代长篇浪漫主义小说的巅峰，与《三国演义》《水浒传》《红楼梦》并称为中国古典四大名著。《西游记》自问世以来在民间广为流传，各式各样的版本层出不穷，明代刊本有六种，清代刊本、抄本也有七种，典籍所记已佚版本十三种。鸦片战争以后，大量中国古典文学作品被译为西文，《西游记》渐渐传入欧美，被译为英、法、德、意、西、手语、世（世界语）、斯（斯瓦西里语）、俄、捷、罗、波、日、朝、越等语言。"
val TEST_DATA2 = "《西游记》是中国神魔小说的经典之作，达到了古代长篇浪漫主义广为流传，，作品被译为西文，里语）、俄、捷、罗、波、日、朝、越等语言。"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explain)

        val explainView = findViewById<ExpandView>(R.id.explainView)
        explainView.setData(TEST_DATA)
    }
}