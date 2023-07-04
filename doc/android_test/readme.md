## Android 测试

[官方文档](https://developer.android.com/training/testing?hl=zh-cn)

```kotlin

 val context = Robolectric.buildActivity(Activity::class.java).get().applicationContext
        val wordInputViewNew = WordInputViewNew(context)
        wordInputViewNew.inflateSentence("<span class=\"key\">\"bißchen</span> world!")

        // 测试用例
        val checkEquals = wordInputViewNew.checkEquals("bißchen", "bisschen", false, true)

        assertEquals(true, checkEquals)
```