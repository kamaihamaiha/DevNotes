### 我的电脑 磁盘分区情况

- EFI 分区(编号 1；200M 66%剩余)
  - EFI(Mac 目前用的这个)
    - APPLE
      - FIRMWARE
        - IM191.fd
    - Boot
      - bootx64.efi
      - fbx64.efi
      - mmx64.efi
    - Microsoft
      - Boot
        - bootmgfw.efi （Windows Boot Manager）
      - Recovery
- ESP（300M 87%空余  黑苹果引导）
  - EFI
  - 1OC
  - APPLE
  - BOOT
    - BOOTx64.efi
  - OC
    - ACPI
    - Drivers
    - Kexts
    - Resources
    - Tools
    - config.plist
    - oldConfig.plist
    - OpenCore.efi

### EssyUEFI

#### 启动顺序

- Mac OS X
  - 分区编号：6
  - 文件路径：\8D2DB677-B07D-4E25-84FC-087E40395F69\System\Library\CoreServices\boot.efi
    - 以 mac 系统打开，在mac磁盘下的第一个分区：
      - 卷名: Mac - 数据
      - disk1s1
      - 磁盘类型: APFS 卷
      - 挂载点: /System/Volumes/Data

- New Boot Entry
  - 分区编号：5
  - 文件路径：\EFI\OC\OpenCore.efi

- Windows Boot Manager: 
  - 分区编号：1
  - 文件路径：\EFI\Microsoft\Boot\bootmgfw.efi


### Mac 下 ooc 认为的 EFI 分区

- 卷标识符: disk0s1
- EFI
  - APPLE
  - Boot
  - Microsoft
    - Boot
    - Recovery