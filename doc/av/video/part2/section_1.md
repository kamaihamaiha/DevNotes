## 从参数的角度看视频图像

- 像素
- 分辨率
- 位深
- Stride
- 帧率
- 码率

### 像素

像素是图像的基本单元，一个个像素就组成了图像。你可以认为像素就是图像中的一个点。

### 分辨率

图像（或视频）的分辨率是指图像的大小或尺寸。我们一般用像素个数来表示图像的尺寸。比如说一张 1920x1080 的图像，   
前者 1920 指的是该图像的宽度方向上有 1920 个像素点，而后者 1080 指的是图像的高度方向上有 1080 个像素点。      

视频行业常见的分辨率有 QCIF（176x144）、CIF（352x288）、D1（704x576 或 720x576），   
还有我们比较熟悉的 360P（640x360）、720P（1280x720）、1080P（1920x1080）、4K（3840x2160）、8K（7680x4320）等。

### 位深

- 一般来说，我们看到的彩色图像中，都有三个通道，这三个通道就是 R、G、B 通道。简单来说就是，彩色图像中的像素是有三个颜色值的，分别是红、绿、蓝三个值
- 通常 R、G、B 各占 8 个位，也就是一个字节。
- 8 个位能表示 256 种颜色值，那 3 个通道的话就是 256 的 3 次方个颜色值，总共是 1677 万种颜色。

位深越大，我们能够表示的颜色值就越多。

### Stride

Stride 也可以称之为跨距，是图像存储的时候有的一个概念。它指的是图像存储时内存中每行像素所占用的空间。   
为了能够快速读取一行像素，我们一般会对内存中的图像实现内存对齐，比如 16 字节对齐。   

举个例子，我们现在有一张 RGB 图像，分辨率是 1278x720。我们将它存储在内存当中，一行像素需要 1278x3=3834 个字节，3834 除以 16 无法整除。   
因此，没有 16 字节对齐。所以如果需要对齐的话，我们需要在 3834 个字节后面填充 6 个字节，也就是 3840 个字节做 16 字节对齐，这样这幅图像的 Stride 就是 3840 了。   

所以，一定要在处理图片的时候注意这个 Stride 值。如果出现一条条斜线的花屏或者说解码后图像的颜色不对的情况，我们需要先确认一下这个 Stride 值对不对。

### 帧率

1 秒钟内图像的数量就是帧率。据研究表明，一般帧率达到 10～12 帧每秒，人眼就会认为是流畅的了。当然，可能会有个体差异。   

通常，我们在电影院看的电影帧率一般是 24fps（帧每秒），监控行业常用 25fps   
帧率高，代表着每秒钟处理的图像数量会很高，从而需要的设备性能就比较高。

### 码率

码率是指视频在单位时间内的数据量的大小。

- 一般是 1 秒钟内的数据量，其单位一般是 Kb/s 或者 Mb/s。
- 通常，我们用压缩工具压缩同一个原始视频的时候，码率越高，图像的失真就会越小，视频画面就会越清晰。
- 并不是码率越高，清晰度就会越高。
    - 因为视频的压缩是一个非常复杂的过程。
    - 视频压缩之后的清晰度还跟压缩时选用的压缩算法，以及压缩时使用的压缩速度有关。