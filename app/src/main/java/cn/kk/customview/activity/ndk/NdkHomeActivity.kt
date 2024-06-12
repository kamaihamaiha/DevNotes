package cn.kk.customview.activity.ndk

import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import cn.kk.base.activity.BaseActivity
import cn.kk.customview.R
import cn.kk.ndk.bean.Animal
import cn.kk.ndk.jni.*

class NdkHomeActivity: BaseActivity() {
    override fun getLayout(): Int = R.layout.activity_ndk_home

    override fun doWhenOnCreate() {
        super.doWhenOnCreate()

        val dynamicRegisterDemo = DynamicRegisterDemo()
        val jniReferenceDemo = JNIReferenceDemo()

        val tv: TextView = findViewById(R.id.sample_text)
        val hello = Hello()
        tv.text = """
            ${hello.saveHello()}
            ${One().info}
            ${StringDemo().info}
            ${StringDemo().getInfoWidthParams("Kitty!")}
            ${dynamicRegisterDemo.getName("动态注册: ")}${dynamicRegisterDemo.add(1, 8)}
            引用类型转换：${jniReferenceDemo.getName(arrayOf("Anne", "Bush"))}
            """.trimIndent()
        StringDemo().pushInfo("dog")

        val jniAccessFieldDemo = JNIAccessFieldDemo()
        val animal = Animal(3, "Kitty")
        showInfo(animal.name + ": " + animal.age)

        // JNI 调用 Java 的方法

        // JNI 调用 Java 的方法
        val jniAccessMethodDemo = JNIAccessMethodDemo()

        // JNI 调用 Java 回调方法，还有子线程中调用

        // JNI 调用 Java 回调方法，还有子线程中调用
        val jniCallbackMethod = JNICallbackMethod()

        // JNI 调用 Java 构造方法，创建 Java 对象

        // JNI 调用 Java 构造方法，创建 Java 对象
        val jniConstructorClassDemo = JNIConstructorClassDemo()

        // JNI 不同引用

        // JNI 不同引用
        val jniRefType = JNIRefType()

        // JNI 异常处理

        // JNI 异常处理
        val jniException = JNIException()

        // JNI 创建线程

        // JNI 创建线程
        val jniThreadDemo = JNIThreadDemo()

        val btn_change = findViewById<Button>(R.id.btn_change)
        btn_change.setOnClickListener {
            jniAccessFieldDemo.modifyAge(animal)
            jniAccessFieldDemo.modifyName(animal)

            jniAccessMethodDemo.callAnimalMethodGetWeight(animal)
            jniAccessMethodDemo.callAnimalStaticMethodGetType(animal)
            jniAccessMethodDemo.callAnimalMethodGetFoods(animal)

            showInfo(animal.name + ": " + animal.age)

            jniCallbackMethod.nativeCallback {
                Log.d(
                    TAG,
                    "nativeCallback thread: " + Thread.currentThread().name
                )
            }

            jniCallbackMethod.nativeThreadCallback {
                Log.d(
                    TAG,
                    "nativeThreadCallback thread: " + Thread.currentThread().name
                )
            }

            val human_1 = jniConstructorClassDemo.invokeHumanConstructor()
            val human_2 = jniConstructorClassDemo.allocObjectConstructor()

            Log.d(TAG, "jniConstructorClassDemo: human_1: " + human_1.name)
            Log.d(TAG, "jniConstructorClassDemo: human_2: " + human_2.name)

            val globalRef = jniRefType.globalRef()
            val localRef = jniRefType.localRef()

            Log.d(TAG, "jniRefType: $globalRef")
            Log.d(TAG, "localRef: $localRef")

            jniException.invokeJavaException()
            try {
                jniException.throwNativeException()
            } catch (exception: IllegalArgumentException) {
                Log.d(TAG, "throwNativeException: " + exception.message)
            }

            // 调用 JNI startThread

            // 调用 JNI startThread
            jniThreadDemo.startThread()

            jniThreadDemo.startThreadWithParams()

            jniThreadDemo.joinThread()
        }

    }

    fun showInfo(info: String?) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show()
    }
}