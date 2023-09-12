## build error

### build android-7.0.0_r1

- error1:   destroy_build_var_cache:unset:4: var_cache_ 2ND_TARGET_GCC_VERSION ANDROID_BUILD_PATHS print report_config TARGET_ARCH TARGET_DEVICE TARGET_GCC_VERSION : invalid parameter name
- error2:


#### error1

error:
  ```text
  # when execute: lunch aosp_x86-eng
  ============================================
  PLATFORM_VERSION_CODENAME=REL
  PLATFORM_VERSION=7.0
  TARGET_PRODUCT=aosp_x86
  TARGET_BUILD_VARIANT=eng
  TARGET_BUILD_TYPE=release
  TARGET_BUILD_APPS=
  TARGET_ARCH=x86
  TARGET_ARCH_VARIANT=x86
  TARGET_CPU_VARIANT=
  TARGET_2ND_ARCH=
  TARGET_2ND_ARCH_VARIANT=
  TARGET_2ND_CPU_VARIANT=
  HOST_ARCH=x86_64
  HOST_2ND_ARCH=x86
  HOST_OS=linux
  HOST_OS_EXTRA=Linux-5.15.0-79-generic-x86_64-with-Ubuntu-20.04-focal
  HOST_CROSS_OS=windows
  HOST_CROSS_ARCH=x86
  HOST_CROSS_2ND_ARCH=x86_64
  HOST_BUILD_TYPE=release
  BUILD_ID=NRD90M
  OUT_DIR=out
  ============================================
  destroy_build_var_cache:unset:4: var_cache_ 2ND_TARGET_GCC_VERSION ANDROID_BUILD_PATHS print report_config TARGET_ARCH TARGET_DEVICE TARGET_GCC_VERSION : invalid parameter name
  ```
- fix
  ```shell
  # Why do this?
  setopt shwordsplit
  # 去除所有本地化的设置，让命令能正确执行
  export LC_ALL=C
  
  # execute lunch again:
  aosp_x86-eng
  ```
  
#### error2

```text
[ 34% 12168/35514] Ensure Jack server is installed and started
FAILED: /bin/bash -c "(prebuilts/sdk/tools/jack-admin install-server prebuilts/sdk/tools/jack-launcher.jar prebuilts/sdk/tools/jack-server-4.8.ALPHA.jar  2>&1 || (exit 0) ) && (JACK_SERVER_VM_ARGUMENTS=\"-Dfile.encoding=UTF-8 -XX:+TieredCompilation\" prebuilts/sdk/tools/jack-admin start-server 2>&1 || exit 0 ) && (prebuilts/sdk/tools/jack-admin update server prebuilts/sdk/tools/jack-server-4.8.ALPHA.jar 4.8.ALPHA 2>&1 || exit 0 ) && (prebuilts/sdk/tools/jack-admin update jack prebuilts/sdk/tools/jacks/jack-2.28.RELEASE.jar 2.28.RELEASE || exit 47; prebuilts/sdk/tools/jack-admin update jack prebuilts/sdk/tools/jacks/jack-3.36.CANDIDATE.jar 3.36.CANDIDATE || exit 47; prebuilts/sdk/tools/jack-admin update jack prebuilts/sdk/tools/jacks/jack-4.7.BETA.jar 4.7.BETA || exit 47 )"
Jack server already installed in "/home/kk/.jack-server"
Communication error with Jack server (35), try 'jack-diagnose' or see Jack server log
SSL error when connecting to the Jack server. Try 'jack-diagnose'
SSL error when connecting to the Jack server. Try 'jack-diagnose'
[ 34% 12168/35514] host Java: bouncycastle-host (out/host/common/obj/JAVA_LIBRARIES/bouncycastle-host_intermediates/classes)
warning: [options] bootstrap class path not set in conjunction with -source 1.7
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
Note: Some input files use unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
1 warning
[ 34% 12168/35514] target  C++: trunks_test <= system/tpm/trunks/mock_tpm.cc
ninja: build stopped: subcommand failed.
make: *** [build/core/ninja.mk:149: ninja_wrapper] Error 1

#### make failed to build some targets (15:32 (mm:ss)) ####

```

- [about blog](https://www.cnblogs.com/crushgirl/p/16053594.html)


  
