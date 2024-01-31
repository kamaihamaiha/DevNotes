## JVM

课程看到 13:44

- 和字节码绑定
- 因此可以处理 Java、Kotlin、Groovy

### 字节码

#### 做个小实验

- 创建一个Java类，Hello.java
- 编译成字节码文件: javac Hello.java，得到 Hello.class
- 通过十六进制方式查看编译的字节码文件: hexdump Hello.class
- 反编译字节码文件：`javap Hello.class`

内容如下:

```text
0000000 feca beba 0000 3700 1d00 000a 0006 090f
0000010 1000 1100 0008 0a12 1300 1400 0007 0715
0000020 1600 0001 3c06 6e69 7469 013e 0300 2928
0000030 0156 0400 6f43 6564 0001 4c0f 6e69 4e65
0000040 6d75 6562 5472 6261 656c 0001 6d04 6961
0000050 016e 1600 5b28 6a4c 7661 2f61 616c 676e
0000060 532f 7274 6e69 3b67 5629 0001 530a 756f
0000070 6372 4665 6c69 0165 0a00 6548 6c6c 2e6f
0000080 616a 6176 000c 0007 0708 1700 000c 0018
0000090 0119 1900 6548 6c6c 2c6f 4920 6120 206d
00000a0 2061 6568 6c6c 206f 616a 6176 0721 1a00
00000b0 000c 001b 011c 1000 6e63 6b2f 2f6b 6562
00000c0 6e61 482f 6c65 6f6c 0001 6a10 7661 2f61
00000d0 616c 676e 4f2f 6a62 6365 0174 1000 616a
00000e0 6176 6c2f 6e61 2f67 7953 7473 6d65 0001
00000f0 6f03 7475 0001 4c15 616a 6176 692f 2f6f
0000100 7250 6e69 5374 7274 6165 3b6d 0001 6a13 
0000110 7661 2f61 6f69 502f 6972 746e 7453 6572
0000120 6d61 0001 7007 6972 746e 6e6c 0001 2815
0000130 6a4c 7661 2f61 616c 676e 532f 7274 6e69
0000140 3b67 5629 2100 0500 0600 0000 0000 0200
0000150 0100 0700 0800 0100 0900 0000 1d00 0100
0000160 0100 0000 0500 b72a 0100 00b1 0000 0001
0000170 000a 0000 0006 0001 0000 0003 0009 000b
0000180 000c 0001 0009 0000 0025 0002 0001 0000
0000190 b209 0200 0312 00b6 b104 0000 0100 0a00
00001a0 0000 0a00 0200 0000 0500 0800 0600 0100
00001b0 0d00 0000 0200 0e00                    
00001b8
```
- 其中第一列是序号；其他的都是内容，16进制表示

#### 查看Java 和 jvm的字节码规范  

- [官网](https://docs.oracle.com/javase/specs/index.html)


#### 使用 javap 反编译字节码文件

```shell
avap -c Hello.class
```

得到结果如下：
```text
Compiled from "Hello.java"
public class cn.kk.bean.Hello {
  public cn.kk.bean.Hello();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
       3: ldc           #3                  // String Hello, I am a hello java!
       5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
       8: return
}
```

```shell
javap -v Hello.class
```

得到结果如下：
```text
Classfile /Users/kk/dev/code/DevNotes/doc/hencoder/jvm/Hello.class
  Last modified 2023年10月19日; size 440 bytes
  MD5 checksum eedd86fb4f4981c3681c7d8b65e45c76
  Compiled from "Hello.java"
public class cn.kk.bean.Hello
  minor version: 0
  major version: 55
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #5                          // cn/kk/bean/Hello
  super_class: #6                         // java/lang/Object
  interfaces: 0, fields: 0, methods: 2, attributes: 1
Constant pool:
   #1 = Methodref          #6.#15         // java/lang/Object."<init>":()V
   #2 = Fieldref           #16.#17        // java/lang/System.out:Ljava/io/PrintStream;
   #3 = String             #18            // Hello, I am a hello java!
   #4 = Methodref          #19.#20        // java/io/PrintStream.println:(Ljava/lang/String;)V
   #5 = Class              #21            // cn/kk/bean/Hello
   #6 = Class              #22            // java/lang/Object
   #7 = Utf8               <init>
   #8 = Utf8               ()V
   #9 = Utf8               Code
  #10 = Utf8               LineNumberTable
  #11 = Utf8               main
  #12 = Utf8               ([Ljava/lang/String;)V
  #13 = Utf8               SourceFile
  #14 = Utf8               Hello.java
  #15 = NameAndType        #7:#8          // "<init>":()V
  #16 = Class              #23            // java/lang/System
  #17 = NameAndType        #24:#25        // out:Ljava/io/PrintStream;
  #18 = Utf8               Hello, I am a hello java!
  #19 = Class              #26            // java/io/PrintStream
  #20 = NameAndType        #27:#28        // println:(Ljava/lang/String;)V
  #21 = Utf8               cn/kk/bean/Hello
  #22 = Utf8               java/lang/Object
  #23 = Utf8               java/lang/System
  #24 = Utf8               out
  #25 = Utf8               Ljava/io/PrintStream;
  #26 = Utf8               java/io/PrintStream
  #27 = Utf8               println
  #28 = Utf8               (Ljava/lang/String;)V
{
  public cn.kk.bean.Hello();
    descriptor: ()V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 3: 0

  public static void main(java.lang.String[]);
    descriptor: ([Ljava/lang/String;)V
    flags: (0x0009) ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=1, args_size=1
         0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: ldc           #3                  // String Hello, I am a hello java!
         5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
         8: return
      LineNumberTable:
        line 5: 0
        line 6: 8
}
SourceFile: "Hello.java"
```