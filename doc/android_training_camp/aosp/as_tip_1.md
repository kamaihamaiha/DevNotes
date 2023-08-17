## AOSP 使用 Android Studio 加载部分模块


- 源码根目录下新建 settintgs.gradle

```groovy
include ":frameworks:base"
```

- 源码根目录下创建 build.gradle
```groovy
buildscript {
    repositories {
        maven { url 'https://maven.aliyun.com/repository/public/' }
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:7.2.0'
    }
}

allprojects {
    repositories {
        maven { url 'https://maven.aliyun.com/repository/public/' }
        google()
        jcenter()
    }
}

apply plugin: "idea"
idea {
    module {
        excludeDirs = [
                file(".repo"),
                file("abi"),
                file("art"),
                file("bionic"),
                file("bootable"),
                file("build"),
                file("cts"),
                file("dalvik"),
                file("developers"),
                file("development"),
                file("docs"),
                file("external"),
                file("hardware"),
                file("kernel"),
                file("libcore"),
                file("libnativehelper"),
                file("ndk"),
                file("out"),
                file("packages"),
                file("pdk"),
                file("platform_testing"),
                file("prebuilts"),
                file("sdk"),
                file("system"),
                file("test"),
                file("toolchain"),
                file("tools")
        ]
    }
}

```

### 加载 frameworks base 模块

- 在 frameworks/base 目录下新建 build.gradle

```groovy
apply plugin: 'java'
sourceSets {
    main.java.srcDirs += 'core/java'
    main.java.srcDirs += 'graphics/java'
    main.java.srcDirs += 'keystore/java'
    main.java.srcDirs += 'location/java'
    main.java.srcDirs += 'lowpan/java'
    main.java.srcDirs += 'media/java'
    main.java.srcDirs += 'media/apex/java'
    main.java.srcDirs += 'opengl/java'
    main.java.srcDirs += 'sax/java'
    main.java.srcDirs += 'services/accessibility/java'
    main.java.srcDirs += 'services/appprediction/java'
    main.java.srcDirs += 'services/appwidget/java'
    main.java.srcDirs += 'services/autofill/java'
    main.java.srcDirs += 'services/backup/java'
    main.java.srcDirs += 'services/companion/java'
    main.java.srcDirs += 'services/contentcapture/java'
    main.java.srcDirs += 'services/contentsuggestions/java'
    main.java.srcDirs += 'services/core/java'
    main.java.srcDirs += 'services/coverage/java'
    main.java.srcDirs += 'services/devicepolicy/java'
    main.java.srcDirs += 'services/midi/java'
    main.java.srcDirs += 'services/net/java'
    main.java.srcDirs += 'services/print/java'
    main.java.srcDirs += 'services/restrictions/java'
    main.java.srcDirs += 'services/robotests/java'
    main.java.srcDirs += 'services/startop/java'
    main.java.srcDirs += 'services/systemcaptions/java'
    main.java.srcDirs += 'services/usage/java'
    main.java.srcDirs += 'services/usb/java'
    main.java.srcDirs += 'services/voiceinteraction/java'
    main.java.srcDirs += 'services/java'
    main.java.srcDirs += 'telecomm/java'
    main.java.srcDirs += 'telephony/java'
    main.java.srcDirs += 'wifi/java'
    main.java.srcDirs += '../../out/soong/.intermediates/frameworks/base/framework/android_common/gen/aidl/frameworks/base/core/java'
   main.java.srcDirs += '../../out/soong/.intermediates/frameworks/base/core/res/framework-res/android_common/gen/aapt2/R'
}

```

### 加载 bootable 模块

- 在 bootable 下新建 build.gradle

```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 29
    defaultConfig {
        applicationId "com.android.recovery"
        minSdkVersion 28
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        externalNativeBuild {
            cmake {
                cppFlags "-std=c++11"
            }
        }
    }
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"
            version "3.10.2"
        }
    }
}

```

- 在 bootable 下，新建 src/main/AndroidManifest.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.android.recovery">
</manifest>

```
- 在 bootable 下，创建 CMakeLists.txt
```cmake
cmake_minimum_required(VERSION 2.6)
project(recovery)
set(CMAKE_CXX_STANDARD 11)

include_directories(../system/core/base/include)
include_directories(../system/core/libcutils/include)
include_directories(../hardware/interfaces/health/2.0/utils/libhealthhalutils/include)
include_directories(../system/core/libziparchive/include)
include_directories(../system/core/liblog/include)
include_directories(../external/selinux/libselinux/include)

include_directories(recovery/applypatch/include)
include_directories(recovery/bootloader_message/include)
include_directories(recovery/edify/include)
include_directories(recovery/fuse_sideload/include)
include_directories(recovery/install/include)
include_directories(recovery/minui/include)
include_directories(recovery/otautil/include)
include_directories(recovery/recovery_ui/include)
include_directories(recovery/update_verifier/include)
include_directories(recovery/updater/include)

FILE(GLOB_RECURSE recovery_cpp_list "./*.cpp")
add_library(recovery ${recovery_cpp_list})
set_target_properties(recovery PROPERTIES LINKER_LANGUAGE CXX)

```

### 加载 system 模块

- 在 system 目录下, 新建 build.gradle

```groovy
apply plugin: 'com.android.application'

android {
    compileSdkVersion 29
    defaultConfig {
        applicationId "com.android.system"
        minSdkVersion 28
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        externalNativeBuild {
            cmake {
                cppFlags "-std=c++11"
            }
        }
    }
    externalNativeBuild {
        cmake {
            path "CMakeLists.txt"
            version "3.10.2"
        }
    }
}
```

- 在 system 目录下, 新建 CMakeLists.txt
```cmake
cmake_minimum_required(VERSION 3.0)

project(system)

set(CMAKE_CXX_STANDARD 11)


file(GLOB_RECURSE all_header_files RELATIVE "${CMAKE_CURRENT_SOURCE_DIR}" "*.h")

foreach(header ${all_header_files})
    get_filename_component(header_path "${header}" PATH)
    list(APPEND all_include_dirs "${CMAKE_CURRENT_SOURCE_DIR}/${header_path}")
endforeach()

include_directories(${all_include_dirs})

FILE(GLOBAL_RECURSE cpp_list "*.cpp")
add_library(${PROJECT_NAME} ${cpp_list})
set_target_properties(${PROJECT_NAME} PROPERTIES LINKER_LANGUAGE CXX)
```



