-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主机： 192.168.5.247:3306
-- 生成日期： 2024-06-26 09:07:29
-- 服务器版本： 8.2.0
-- PHP 版本： 8.2.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `blog`
--

-- --------------------------------------------------------

--
-- 表的结构 `categories`
--

CREATE TABLE `categories` (
  `category_key` int UNSIGNED NOT NULL COMMENT '唯一标识',
  `category_title` varchar(10) NOT NULL COMMENT '分类名',
  `introduce` varchar(100) NOT NULL COMMENT '分类介绍',
  `icon` varchar(20) NOT NULL COMMENT 'icon',
  `color` char(8) NOT NULL DEFAULT '#fff' COMMENT '颜色',
  `path_name` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='分类表';

--
-- 转存表中的数据 `categories`
--

INSERT INTO `categories` (`category_key`, `category_title`, `introduce`, `icon`, `color`, `path_name`) VALUES
(9, '教程', '存放一些教程用的分类', '', '#44E1D7', '教程');

-- --------------------------------------------------------

--
-- 表的结构 `images`
--

CREATE TABLE `images` (
  `image_key` int UNSIGNED NOT NULL COMMENT '唯一标识',
  `image_url` varchar(300) NOT NULL COMMENT '图片链接'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='图库表';

--
-- 转存表中的数据 `images`
--

INSERT INTO `images` (`image_key`, `image_url`) VALUES
(108, 'http://192.168.5.158:9000/blog/de32070410f34da0a0bb4b3fd9913269.png'),
(109, 'http://192.168.5.158:9000/blog/6c6e7639e3584a9d8c8ed3f01fa9ebcb.jpg'),
(111, 'http://192.168.5.158:9000/blog/a7aa9b82a25d4de18627d2d27fa12ee2.jpg'),
(114, 'http://192.168.5.158:9000/blog/483629f426c943d4bdeee654c6a96e94.jpg'),
(116, 'http://192.168.5.158:9000/blog/a210eb98a43942eda0a6d985db56dae5.jpg'),
(123, 'http://192.168.5.158:9000/blog/ddbce13e443b4926962ca3f0d8651373.jpg'),
(125, 'http://192.168.5.158:9000/blog/4c9889c267a94e78a67b07cd3b6fa45b.jpg'),
(157, 'http://192.168.5.158:9000/blog/de9274238cd541b6888f5a5a9d183fdc.jpg'),
(160, 'http://192.168.5.158:9000/blog/63c8b0f592e246d499e06f97360142fe.jpg'),
(161, 'http://192.168.5.158:9000/blog/d4882eebcc8c436bae2e663f7d755e83.jpg'),
(162, 'http://192.168.5.158:9000/blog/cab465eb73cd4de9a312eab5c8973ec4.jpg'),
(163, 'http://192.168.5.158:9000/blog/fb7a1fa1dd144802857654372b5f485a.png'),
(164, 'http://192.168.5.158:9000/blog/aa7072dd50c64ae7a71297b6580de416.png'),
(171, 'http://192.168.5.158:9000/blog/1808965db78b414fbd1278534c98f9e9.jpg'),
(172, 'http://192.168.5.158:9000/blog/2d38d038a12a4a0aa8b9be2573007f4a.jpg'),
(173, 'http://192.168.5.158:9000/blog/d09d451e9a5f4664b0cd515e7f311aa4.jpg'),
(176, 'http://192.168.5.158:9000/blog/26301cd576ce463cb1c16f2fe49a17de.jpg'),
(177, 'http://192.168.5.158:9000/blog/13b28ad19667416195df2490c12c9c8e.jpg'),
(178, 'http://192.168.5.158:9000/blog/c98d269f7f9f4c338fb29ddd7b1aa97d.jpg'),
(179, 'http://192.168.5.158:9000/blog/4ecd24b3f16a4264ad10e2de924b1125.png'),
(181, 'http://192.168.5.158:9000/blog/0fe6dd906c9b4c6ca351d1c3afd639f1.jpg'),
(182, 'http://192.168.5.158:9000/blog/fc3f44ce9c2a445190dd9f76f8a5a473.jpg'),
(183, 'http://192.168.5.158:9000/blog/6e94ce9b01ee42d4983d182242e9a9e7.jpg'),
(184, 'http://192.168.5.158:9000/blog/322ae39c7e334593a5b44857a3b01e88.jpg'),
(185, 'http://192.168.5.158:9000/blog/d2714437e1324afb94ba61ca5e3c6d7c.jpg'),
(186, 'http://192.168.5.158:9000/blog/8cddab16d93c49fd800517ea0e398577.jpg'),
(188, 'http://192.168.5.158:9000/blog/23d35555226144d2b665171244da733d.jpg'),
(195, 'http://192.168.5.158:9000/blog/3e15f461b1e04177a0e437cfc9895274.jpg'),
(196, 'http://192.168.5.158:9000/blog/166c557ebb3945e7b80d17b86a5f2650.jpg'),
(197, 'http://192.168.5.158:9000/blog/169f9574e0f84f70a0594a152820cef1.jpg'),
(198, 'http://192.168.5.158:9000/blog/35c6936815794fabaad24e7edf07e486.png'),
(199, 'http://192.168.5.158:9000/blog/8b53387d56614a78a331f3e83c540ff2.jpg'),
(200, 'http://192.168.5.158:9000/blog/43415410ca1843bd80c74967a360897e.jpg');

-- --------------------------------------------------------

--
-- 表的结构 `menu`
--

CREATE TABLE `menu` (
  `menu_id` bigint NOT NULL COMMENT '主键',
  `menu_name` varchar(64) NOT NULL COMMENT '菜单名称',
  `menu_type` smallint NOT NULL DEFAULT '0' COMMENT '	菜单的类型(1为普通菜单2为目录3为内嵌iFrame4为外链跳转)',
  `router_name` varchar(255) NOT NULL COMMENT '路由名称（需保持和前端对应的vue文件中的name保持一致defineOptions方法中设置的name）',
  `component` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '组件地址',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(255) DEFAULT NULL COMMENT '组件路径（对应前端项目view文件夹中的路径）',
  `is_button` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否按钮',
  `permission` varchar(128) DEFAULT NULL COMMENT '权限标识',
  `meta_info` varchar(1024) NOT NULL DEFAULT '{}' COMMENT '路由元信息',
  `status` smallint NOT NULL DEFAULT '1' COMMENT '菜单状态（1启用 0停用）',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

--
-- 转存表中的数据 `menu`
--

INSERT INTO `menu` (`menu_id`, `menu_name`, `menu_type`, `router_name`, `component`, `parent_id`, `path`, `is_button`, `permission`, `meta_info`, `status`, `remark`) VALUES
(1, '系统管理', 2, '', NULL, 0, '/system', 0, '', '{\"title\":\"系统管理\",\"icon\":\"line-md:cog-filled-loop\",\"showParent\":true,\"rank\":1,\"roles\":[\"admin\"]}', 1, NULL),
(2, '文件管理', 2, '', NULL, 0, '/files', 0, '', '{\"title\":\"文件管理\",\"icon\":\"solar:folder-with-files-outline\",\"rank\":2}', 1, NULL),
(3, '文章管理', 2, '', NULL, 0, '/notes', 0, '', '{\"title\":\"文章管理\",\"icon\":\"majesticons:note-text\",\"rank\":3}', 1, NULL),
(4, '分类/标签管理', 2, '', NULL, 0, '/classification', 0, '', '{\"title\":\"分类/标签管理\",\"icon\":\"carbon:collapse-categories\",\"showParent\":true,\"rank\":4}', 1, NULL),
(100, '用户管理', 1, 'User', 'user/index', 1, '/system/user/index', 0, '', '{\"title\": \"用户管理\",\"icon\": \"line-md:account\",\"showParent\":true,\"roles\":[\"admin\"]}', 1, NULL),
(200, '图片上传', 1, 'Upload', 'upload/index', 2, '/files/upload/index', 0, '', '{\"title\":\"图片上传\",\"icon\":\"ic:twotone-cloud-upload\",\"rank\":1}', 1, NULL),
(201, '图片预览', 1, 'Preview', 'preview/index', 2, '/files/preview/index', 0, '', '{\"title\":\"图片预览\",\"icon\":\"mdi:folder-multiple-image\",\"rank\":2}', 1, NULL),
(202, '文件列表', 1, 'FileList', 'fileList/index', 2, '/files/file-list/index', 0, '', '{\"title\":\"文件列表\",\"icon\":\"ic:baseline-format-list-bulleted\",\"rank\":3}', 1, NULL),
(300, '全部文章', 1, 'AllNotes', 'allnotes/index', 3, '/notes/allnotes/index', 0, '', '{\"title\":\"全部文章\",\"icon\":\"solar:notebook-minimalistic-bold\",\"rank\":1}', 1, NULL),
(301, '编辑文章', 1, 'EditNotes', 'editor/index', 3, '/notes/editnotes/index', 0, '', '{\"title\":\"编辑文章\",\"icon\":\"material-symbols:sticky-note-outline\",\"rank\":3}', 1, NULL),
(302, '全部说说', 1, 'Talks', 'talks/index', 3, '/notes/talks/index', 0, '', '{\"title\":\"全部说说\",\"icon\":\"clarity:talk-bubbles-line\",\"rank\":2}', 1, NULL),
(401, '分类管理', 1, 'AllCategories', 'allcategories/index', 4, '/classification/allcategories/index', 0, '', '{\"title\":\"全部分类\",\"icon\":\"carbon:classification\",\"showParent\":true,\"rank\":1}', 1, NULL),
(402, '标签管理', 1, 'AllTags', 'alltags/index', 4, '/classification/alltags/index', 0, '', '{\"title\":\"全部标签\",\"icon\":\"ic:outline-collections-bookmark\",\"showParent\":true,\"rank\":1}', 1, NULL);

-- --------------------------------------------------------

--
-- 表的结构 `notes`
--

CREATE TABLE `notes` (
  `note_key` int UNSIGNED NOT NULL COMMENT '唯一标识',
  `note_title` varchar(50) NOT NULL COMMENT '文章标题',
  `note_content` text NOT NULL COMMENT '内容',
  `description` text NOT NULL COMMENT '文章描述',
  `cover` varchar(300) NOT NULL COMMENT '封面',
  `note_category` int NOT NULL COMMENT '文章分类key',
  `note_tags` varchar(50) DEFAULT NULL COMMENT '文章标签',
  `status` varchar(10) NOT NULL DEFAULT 'public' COMMENT '发布状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  `update_time` datetime DEFAULT NULL,
  `is_top` int DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='文章表';

--
-- 转存表中的数据 `notes`
--

INSERT INTO `notes` (`note_key`, `note_title`, `note_content`, `description`, `cover`, `note_category`, `note_tags`, `status`, `create_time`, `update_time`, `is_top`) VALUES
(8, 'ReDroid教学：用Docker跑Android系统，在x86电脑玩ARM手机游戏', '> **本文转载自[Ivon的部落格](https://ivonblog.com/posts/redroid-android-docker/)**\n\nReDroid (Remote anDroid) 是自架「云手机」的方案，透过docker在电脑上跑Android系统容器，再利用Scrcpy的镜射萤幕功能连线到Android桌面。\n\n![](https://ivonblog.com/posts/redroid-android-docker/images/FQXt3GC.webp)\n\nReDroid也是在电脑上用**开源软件**跑Android APP的解决方案。因为別说云手机了，很多Android手游模擬器都是**闭源软件**哪。相较之下，ReDroid除了ARM转译器以外都是开源的。更棒的是ReDroid支援GPU加速＋ARM转x86的转译器，这样就可以玩大多数手机3D游戏了。\n\n对Linux用户来说，这更是除了 Waydroid和Android-x86以外，在Linux电脑高效率跑Android APP的方法。並且它比Waydroid更適合当作云手机使用。\n\n本文将讨论如何在x86架构的Linux电脑，用ReDroid玩ARM架构的手机游戏。我们会在ReDroid映像档加入ARM转译器＋Google服务框架，以达成最佳使用体验。\n\n## 1. ReDroid需要用到的软件\n\n如果要用ReDroid玩手机游戏，建议x86架构的电脑至少要有8GB以上RAM，因为有时ARM在转译成x86指令时会佔用大量RAM。\n\nReDroid在[Github](https://github.com/remote-android/redroid-doc/blob/master/deploy/README.md)上有各大Linux发行版的安装说明。\n\n1. 在Arch Linux，要更换内核为`linux-zen`。在安装`linux-zen`内核之后，更新GRUB选项。重开机，在GRUB选单的Advanced Options选取以`linux-zen`内核开机。\n    ```bash\n    # 如果有Nvidia驱动，记得將其换成DKMS版本\n    sudo pacman -R nvidia\n    sudo pacman -S nvidia-dkms\n    # 確认没有安装binder_linux-dkms\n    yay -R binder_linux-dkms\n    \n    # 安装linux-zen核心\n    sudo pacman -S linux-zen linux-zen-headers\n    sudo mkinitcpio -p linux-zen\n    sudo grub-mkconfig -o /boot/grub/grub.cfg\n    ```\n2. 接着安装[Docker](https://ivonblog.com/posts/install-docker-engine-on-linux/)，用于执行容器。\n3. 安装ADB和Scrcpy。Scrcpy现已包含传输音效的功能。\n   ```bash\n    sudo pacman -S android-tools\n    sudo pacman -S scrcpy\n   ```\n## 2. 建立ReDroid虚拟机\n仅Nvidia显示卡需要，因Nvidia闭源驱动无法让ReDroid使用GPU加速，需要跑一个QEMU虚擬机，再在里面装ReDroid透过Virtio-gpu达成硬体加速。不然的话ReDroid会走软件渲染。\n\n1. [下载Ubuntu 22.04](https://releases.ubuntu.com/jammy/)，安装QEMU ＋ Virt Manager，建立一个64GB的Ubuuntu虚擬机\n   ```bash\n   qemu-img create -f qcow2 ubuntu.qcow2 64GB\n   qemu-system-x86_64 -boot d -cdrom \"ubuntu-22.04.1-desktop-amd64.iso\" -enable-kvm -smp 4 -device intel-hda -device hda-duplex  -device virtio-vga-gl  -net nic -net user,hostfwd=tcp::5555-:5555    -cpu host  -m 4096  -display sdl,gl=on -hda ubuntu.qcow2\n   ```\n2. 开机进虚擬机，然后再装Docker。\n   ```bash\n   qemu-system-x86_64 c  -enable-kvm -smp 4 -device intel-hda -device hda-duplex  -device virtio-vga-gl  -net nic -net user,hostfwd=tcp::5555-:5555    -cpu host  -m 4096  -display sdl,gl=on -hda ubuntu.qcow2\n   ```\n\n## 3. 启动ReDroid\n\n大部分电脑是x86架构，只能执行x86版的Android APP，然而很多手机游戏只有ARM版，所以ReDroid需要ARM转译器。\n\nRedroid作者发布的`redroid:11.0.0-latest`和`redroid:12.0.0-latest`映像档已內建Google开发的ARM转译器libndk，我试过只有Android 11比较稳定，Gapps也可以用，所以下面以Android 11为例。\n\n1. 建立存放资料的目录，新增docker-compose\n   ```bash\n   mkdir ~/redroid\n   cd redroid\n   vim docker-compose.yml\n   ```\n2. 填入以下內容\n   ```bash\n   version: \"3\"\n   services:\n     redroid:\n       image: redroid/redroid:11.0.0-latest\n       stdin_open: true\n       tty: true\n      privileged: true\n      ports:\n         - \"5555:5555\"\n      volumes:\n       # 资料存放在目前目录下\n         - ./redroid-11-data:/data\n       command:\n       # 启用GPU硬体加速\n         - androidboot.redroid_gpu_mode=auto\n       # 设定libndk相关\n         - ro.product.cpu.abilist0=x86_64,arm64-v8a,x86,armeabi-v7a,armeabi\n         - ro.product.cpu.abilist64=x86_64,arm64-v8a\n         - ro.product.cpu.abilist32=x86,armeabi-v7a,armeabi\n         - ro.dalvik.vm.isa.arm=x86\n         - ro.dalvik.vm.isa.arm64=x86_64\n         - ro.enable.native.bridge.exec=1\n         - ro.dalvik.vm.native.bridge=libndk_translation.so\n         - ro.ndk_translation.version=0.2.2\n   ```\n3. 启动服务\n   ```bash\n   sudo docker compose up -d\n   ```\n4. 用ADB连线至本机的ReDroid：\n   ```bash\n   adb connect localhost:5555\n\n   # 如果连不上，用以下指令看一下容器內部发生什么问题\n   sudo docker exec <容器ID> logcat\n   sudo docker logs <容器ID>\n   ```\n5. 执行Scrcpy，连线到Android桌面：\n   ```bash\n   scrcpy -s localhost:5555 --audio-codec=raw\n   ```\n6. 这样就会看到Android的桌面了。\n   \n   ![](https://ivonblog.com/posts/redroid-android-docker/images/rPc7feZ.webp)\n\n## 4. 安装Google服务框架\n作者说Google服务框架是专有软体无法內建，那么就得自行安装了。第一个方法是重新编译映像档，第二个是手动安装OpenGApps。\n\n不推荐第一个方法，耗时而且作者提供的GApps编译教学又有其他APP侦测不到的问题。\n\n这里採用第二个方法：手动安装，过程参考自：[Install GApps Manually - Google Groups](https://groups.google.com/g/android-rpi/c/xb2fwTQbUYw)\n\n1. 到[OpenGapps](https://opengapps.org/)下载x86_64架构的Android 11 GApps，选择最小化的pico版。\n   \n2. 解压缩，会看到以下目录\n   ```bash\n   open_gapps-x86_64-11.0-pico-20220503\n   ├── Core\n   ├── GApps\n   ├── META-INF\n   ├── Optional\n   ```\n\n3. 在解压缩的目录`open_gapps-x86_64-11.0-pico-20220503`下面新增`system`目录。\n\n4. 接著，將`Core`和`GApps`目录里面的`.lz`档案都解压缩，並將里面的APK目录按照对应的安装目录放到`system`目录。例如`GApps/googletts-x86_64/nodpi/app/`下的`GoogleTTS`目录要放到`/system/app`。\n\n5. 放好之后，`system`下的目录结构应该会长这样：\n   ```bash\n   system\n    ├── app\n    │   ├── GoogleCalendarSyncAdapter\n    │   │   └── GoogleCalendarSyncAdapter.apk\n    │   ├── GoogleContactsSyncAdapter\n    │   │   └── GoogleContactsSyncAdapter.apk\n    │   ├── GoogleExtShared\n    │   │   └── GoogleExtShared.apk\n    │   └── GoogleTTS\n    │       └── GoogleTTS.apk\n    ├── etc\n    │   ├── default-permissions\n    │   │   ├── default-permissions.xml\n    │   │   └── opengapps-permissions-q.xml\n    │   ├── permissions\n    │   │   ├── com.google.android.dialer.support.xml\n    │   │   ├── com.google.android.maps.xml\n    │   │   ├── com.google.android.media.effects.xml\n    │   │   ├── privapp-permissions-google.xml\n    │   │   └── split-permissions-google.xml\n    │   ├── preferred-apps\n    │   │   └── google.xml\n    │   └── sysconfig\n    │       ├── dialer_experience.xml\n    │       ├── google_build.xml\n    │       ├── google_exclusives_enable.xml\n    │       ├── google-hiddenapi-package-whitelist.xml\n    │       └── google.xml\n    ├── framework\n    │   ├── com.google.android.dialer.support.jar\n    │   ├── com.google.android.maps.jar\n    │   └── com.google.android.media.effects.jar\n    ├── priv-app\n    │   ├── AndroidAutoPrebuiltStub\n    │   │   └── AndroidAutoPrebuiltStub.apk\n    │   ├── AndroidMigratePrebuilt\n    │   │   └── AndroidMigratePrebuilt.apk\n    │   ├── CarrierSetup\n    │   │   └── CarrierSetup.apk\n    │   ├── ConfigUpdater\n    │   │   └── ConfigUpdater.apk\n    │   ├── GoogleBackupTransport\n    │   │   └── GoogleBackupTransport.apk\n    │   ├── GoogleExtServices\n    │   │   └── GoogleExtServices.apk\n    │   ├── GoogleFeedback\n    │   │   └── GoogleFeedback.apk\n    │   ├── GoogleOneTimeInitializer\n    │   │   └── GoogleOneTimeInitializer.apk\n    │   ├── GooglePackageInstaller\n    │   │   └── GooglePackageInstaller.apk\n    │   ├── GooglePartnerSetup\n    │   │   └── GooglePartnerSetup.apk\n    │   ├── GoogleRestore\n    │   │   └── GoogleRestore.apk\n    │   ├── GoogleServicesFramework\n    │   │   └── GoogleServicesFramework.apk\n    │   ├── Phonesky\n    │   │   └── Phonesky.apk\n    │   ├── PrebuiltGmsCore\n    │   │   └── PrebuiltGmsCore.apk\n    │   └── SetupWizard\n    │       └── SetupWizard.apk\n    └── product\n        └── overlay\n            └── PlayStoreOverlay.apk\n\n   ```\n\n6. 执行以下指令取得root权限：\n   ```bash\n   adb connect localhost:5555\n   adb -s localhost:5555 root\n   adb -s localhost:5555 remount\n   adb -s localhost:5555 shell \"rm -rf system/priv-app/PackageInstaller\"\n   ```\n\n7. 接著將`system`目录推送到ReDroid系统，並赋予权限：\n   ```bash\n   adb -s localhost:5555 push system /\n   adb -s localhost:5555 shell \"pm grant com.google.android.gms android.permission.ACCESS_COARSE_LOCATION\"\n   adb -s localhost:5555 shell \"pm grant com.google.android.gms android.permission.ACCESS_FINE_LOCATION\"\n   adb -s localhost:5555 shell \"pm grant com.google.android.setupwizard android.permission.READ_PHONE_STATE\"\n   adb -s localhost:5555 shell \"pm grant com.google.android.setupwizard android.permission.READ_CONTACTS\"\n   adb reboot\n   ```\n\n8. 重新启动ReDroid容器：\n   ```bash\n   cd ~/redroid\n   sudo docker compose down\n   sudo docker compose up -d\n   ```\n\n9. 启动Scrcpy\n   ```bash\n   scrcpy -s localhost:5555 --audio-codec=raw\n   ```\n\n10. 开启系统设定 → 应用程式，点选右上角显示系统应用程式，將Google Play服务和Play商店的权限都开启。\n\n11. 执行以下指令取得Android装置ID，到[Google网站註册装置](https://www.google.com/android/uncertified)，等个30分钟后重新启动Redroid容器，才能登入Google Play。\n\n    ```bash\n     adb -s localhost:5555 root\n     adb -s localhost:5555 shell \'sqlite3 /data/data/com.google.android.gsf/databases/gservices.db \\\n      \"select * from main where name = \\\"android_id\\\";\"\'\n    ```\n\n## 5.  ReDroid安装APK\n\n目前即使有安装libndk，Android 11的Play商店还是不给下载ARM架构的APP，请配合APKPure之类的应用程式商店安装APP。\n\n除了用容器內部的瀏览器下载APK外，你还可以用ADB安装APK至ReDroid容器。比方说到[ApkMirror](https://www.apkmirror.com/apk/line-corporation/line/)下载Line的APK，接著用ADB安装：\n\n```bash\nadb -s localhost:5555 install \"jp.naver.line.android.apk\"\n```\n你也可以用ADB的`pull`和`push`指令传输档案。\n\n## 6. ReDroid如何「开关机」\n如果要將ReDroid关机，將Scrcpy视窗关闭后，停止容器：\n```bash\ncd ~/redroid\nsudo docker compose down\n```\n\n之后可以再用此指令启动ReDroid。ReDroid容器的\'/data\'资料位於\'~/redroid/redroid-11-data\'目录，可以用来备份多个系统的档案。\n```bash\ncd ~/redroid\nsudo docker compose up -d\nadb connect localhost:5555\nscrcpy -s localhost:5555\n```', 'ReDroid教学：用Docker跑Android系统，在x86电脑玩ARM手机游戏', 'http://192.168.5.158:9000/blog/fb7a1fa1dd144802857654372b5f485a.png', 9, '10,11', 'public', '2024-05-07 11:31:02', '2024-05-08 07:16:44', 1),
(9, '利用 Docker 部署云手机和碧蓝航线脚本', '> 本文转载自[zerozawa](https://www.zerozawa.top/2023/%E5%88%A9%E7%94%A8-docker-%E9%83%A8%E7%BD%B2%E4%BA%91%E6%89%8B%E6%9C%BA%E5%92%8C%E7%A2%A7%E8%93%9D%E8%88%AA%E7%BA%BF%E8%84%9A%E6%9C%AC/)\n\n## 安装工具\n首先得要安装需要的一些工具：docker、adb、scrcpy 等。\n- Docker\n   > Docker 是一种打包、传输和运行任何程序作为轻量级容器的实用工具.\n     ```bash\n     # 安装 docker\n    sudo pacman -S docker docker-compose\n    # 启动 docker\n    sudo systemctl start docker.service\n    # docker 添加到启动项\n    sudo systemctl enable docker.service\n    # 将用户添加到 Docker 组中，否则只有sudo才能调用docker\n    sudo usermod -aG docker ${USER}\n    # 建议重启\n    reboot\n    # 测试下 docker\n    docker run hello-world\n    ```\n- ADB\nAndroid 调试桥（ADB）是一种命令行工具，可用于安装、卸载和调试应用程序，传输文件和访问设备的 shell。\n    ```bash\n    # 安装 adb\n    sudo pacman -S android-tools\n    ```\n- scrcpy\nscrcpy 是一个免费的开源屏幕镜像程序，它允许以 Windows 、 macOS 或 Linux 台式计算机查看并控制 Android 设备，可轻松实现快速的屏幕镜像。\n    ```bash\n    # 安装 scrcpy\n    sudo pacman -S scrcpy\n    # 不熟悉命令行的也可以使用带有图形界面的qtscrcpy，或者安装额外的guiscrcpy\n    yay -S qtscrcpy guiscrcpy # 选一个安装就行，或者都不安装，就用命令行\n    ```\n## 部署云手机\n这里要使用一个叫做 Redroid 的镜像，我们使用 docker-compose 基于这个镜像构建容器。\n- 使用转译器\n普通的镜像是无法运行 arm 架构的程序的，所幸碧蓝航线支持 X86_64 架构，所以无需使用转译器，对于需要转译器的同学，以下是一点点参考（就玩几个特定游戏的并且支持 X86 的建议别上转译器，吃的内存多了一倍）：\n先找一个目录，新建一个文件，名为`dockerfile`，内容如下：\n    ```dockerfile\n    # docker build . -t redroid:11.0.0-nb\n    FROM redroid/redroid:11.0.0-latest\n    \n    ADD git/Droid-NDK-Extractor/working/extracted/native-bridge.tar /\n    ```\n    然后在这个目录下执行：\n   ```bash\n   git clone https://github.com/sickcodes/Droid-NDK-Extractor.git\n   cd Droid-NDK-Extractor\n   chmod +x android-extract-ndk.sh\n   ./android-extract-ndk.sh x86_64\n   cd working/extracted\n   mkdir native-bridge\n   cd native-bridge\n   sudo tar -xpf ../native-bridge.tar\n   sudo chmod 0644 system/etc/init/ndk_translation_arm64.rc\n   sudo chmod 0755 system/bin/arm\n   sudo chmod 0755 system/bin/arm64\n   sudo chmod 0755 system/lib/arm\n   sudo chmod 0755 system/lib64/arm64\n   sudo chmod 0644 system/etc/binfmt_misc/*\n   sudo tar -cpf native-bridge.tar system\n   mv native-bridge.tar ..\n   cd ..\n   sudo rm -r native-bridge\n   ```\n   这样一来就得到 ARM 转译器`native-bridge.tar`了。\n   使用这个转译器与`redroid:11.0.0-latest`镜像构建出一个新的镜像。\n   回到`dockerfile`存在的目录，将 `native-bridge.tar` 复制到此处,运行\n   ```bash\n   sudo docker build . -t redroid-11-nb\n   ```\n   然后编辑 `docker-compose.yaml` 文件，填入 docker 启动参数：\n   ```yaml\n   services:\n     redroid:\n       privileged: true\n       volumes:\n         - ./data:/data\n       ports:\n         - 5555:5555\n       command:\n         - androidboot.redroid_gpu_mode=host\n         - ro.product.cpu.abilist=x86_64,arm64-v8a,x86,armeabi-v7a,armeabi\n         - ro.product.cpu.abilist64=x86_64,arm64-v8a\n         - ro.product.cpu.abilist32=x86,armeabi-v7a,armeabi\n         - ro.dalvik.vm.isa.arm=x86\n         - ro.dalvik.vm.isa.arm64=x86_64\n         - ro.enable.native.bridge.exec=1\n         - ro.dalvik.vm.native.bridge=libndk_translation.so\n         - ro.ndk_translation.version=0.2.2\n         - ro.secure=0 # 允许 ADB Root 调试\n       container_name: redroid\n       image: \'redroid/redroid:11.0.0-latest\' # 对X86的镜像来说上面的参数多了，但是加了也没事\n       # image: \'redroid:11.0.0-nb\' 有转译需求的话使用这个\n       network_mode: bridge\n   ```\n- 启动容器\n   ```bash\n    # 建议别 -d，云手机还是很吃资源的，需要的时候再手动打开\n    docker compose up\n   ```\n- 显示\n   ```bash\n   # 连接手机\n   adb connect localhost:5555\n   # 显示安卓桌面\n   scrcpy -s localhost:5555\n   ```\n## 部署 Alas 脚本\n- 安装\n   ```bash\n   git clone https://github.com/LmeSzinc/AzurLaneAutoScript\n   cd AzurLaneAutoScript\n   ```\n- 然后运行\n   ```bash\n   # 官网是使用podman-compose，\n   # 注意使用podman的话设置开机启动比较麻烦，\n   # 而且本人使用podman并没有成功启动\n   podman-compose up\n   # 我使用的还是docker\n   # 没有自启动需求的可以不加 -d, 但是记得要自启得在compose文件加restart: always\n   docker compose up -d\n   ```\n', '利用 Docker 部署云手机和碧蓝航线脚本', 'http://192.168.5.158:9000/blog/13b28ad19667416195df2490c12c9c8e.jpg', 9, '11,10', 'public', '2024-05-11 22:51:19', '2024-05-11 22:56:08', 0),
(10, '在PVE的LXC容器中直通核心显卡', '> 本文转载自[在PVE的LXC容器中直通核心显卡](https://blog.hellowood.dev/posts/%E5%9C%A8pve%E7%9A%84lxc%E5%AE%B9%E5%99%A8%E4%B8%AD%E7%9B%B4%E9%80%9A%E6%A0%B8%E5%BF%83%E6%98%BE%E5%8D%A1/)\n\n### 在 ProxmoxVE 的 LXC 容器中直通核心显卡\n在 ProxmoxVE 平台中使用 LXC 容器使用 Docker 部署 [frigate](https://frigate.video/) 时(或其他需要GPU的容器如Jellyfin等)，需要使用 GPU 对 ffmpeg 进行加速，因此需要将宿主机 N5105 的核心显卡挂载到 LXC 容器到 Docker 容器中\n### 安装核显驱动\n- 查看设备\n  如果能够看到 PCI 设备中包含核心显卡，说明设备识别正常\n  ```bash\n  lspci | grep VGA\n  00:02.0 VGA compatible controller: Intel Corporation JasperLake [UHD Graphics] (rev 01)\n  ```\n- 查看驱动\n  可以看到 card0 和 renderD128 都存在，说明驱动正常\n  ```bash\n  ls /dev/dri/\n  by-path  card0	renderD128\n  ```\n  通常不需要安装驱动，如果设备没有正确识别，可以参考[](https://dgpu-docs.intel.com/driver/installation.html#ubuntu-install-steps)进行安装\n### 创建 LXC 容器\n如图，在 PVE的控制界面，选择创建 CT 容器；配置中取消 “无特权容器” 的勾选，模板选择 CentOS 或 Ubuntu 等均可\n![](https://img.hellowood.dev/picture/homelab-pve-lxc-intel-graphics-mount-1.png)\n![](https://img.hellowood.dev/picture/homelab-pve-lxc-intel-graphics-mount-2.png)\n创建完成后，即可看到容器的 ID，即VMID，这里是 104\n### 修改核心显卡直通\n修改核心显卡直通，需要使用 PVE 宿主机的命令行修改 LXC 容器的配置文件\n- 添加核心显卡直通\n  使用 nano 编辑容器对应的配置文件，容器ID 104对应的文件是`104.conf`，路径是`/etc/pve/lxc/`\n  ```bash\n  nano /etc/pve/lxc/104.conf\n  ```\n  打开后默认的配置如下：\n  ```conf\n  arch: amd64\n  cores: 2\n  hostname: frigate\n  memory: 2048\n  net0: name=eth0,bridge=vmbr0,firewall=1,hwaddr=A6:43:11:F3:EE:78,ip=dhcp,type=veth\n  ostype: ubuntu\n  rootfs: local-lvm:vm-104-disk-0,size=8G\n  swap: 512\n  ```\n  需要添加以下内容\n  ```conf\n  lxc.apparmor.profile: unconfined\n  lxc.cgroup.devices.allow: a\n  lxc.cap.drop:\n  lxc.cgroup2.devices.allow: c 226:0 rwm\n  lxc.cgroup2.devices.allow: c 226:128 rwm\n  lxc.mount.entry: /dev/dri/card0 dev/dri/card0 none bind,optional,create=file\n  lxc.mount.entry: /dev/dri/renderD128 dev/dri/renderD128 none bind,optional,create=file\n  ```\n  这些配置参数是针对 Linux 容器（通常是 LXC 容器）的一些安全和资源控制设置，用于限制容器内部的行为和访问。以下是每个配置项的作用解释：\n  `lxc.apparmor.profile: unconfined`：该配置指定了`AppArmor`（应用程序安全性配置框架）的配置文件名称，这里设置为 “unconfined”，用于允许容器内的进程具有更高的系统权限\n\n  `lxc.cgroup.devices.allow: a`： 允许容器内的进程访问所有的 cgroup 设备。\n\n  `lxc.cap.drop`: 此配置项为空，容器内的进程将继承主机系统的默认能力设置。\n\n  `lxc.cgroup2.devices.allow: c 226:0 rwm` 和`lxc.cgroup2.devices.allow: c 226:128 rwm`：允许容器内的进程对设备号为 226:0 和 226:128 的字符设备节点拥有读、写和映射（rwm）的权限。用于允许容器内的进程访问特定的设备，如图形加速设备。\n\n  `lxc.mount.entry: /dev/dri/card0 dev/dri/card0 none bind,optional,create=file`和`lxc.mount.entry: /dev/dri/renderD128 dev/dri/renderD128 none bind,optional,create=file`：将主机系统上的两个设备节点 /dev/dri/card0 和 /dev/dri/renderD128 挂载到容器内的相同位置，用于允许容器内的应用程序访问图形硬件加速功能，以便执行图形相关的任务\n\n  修改完成后保存，启动 LXC 容器\n### 检查核显\n等容器启动成功后，进入容器，使用命令行检查`/dev/dri`路径下挂载的文件，`card0`和`renderD128`都正常\n```bash\nls /dev/dri/\ncard0  renderD128\n```\n查看 PCI 也能看到核心显卡，说明挂载成功\n```bash\nlspci | grep VGA\n00:02.0 VGA compatible controller: Intel Corporation JasperLake [UHD Graphics] (rev 01)\n```\n### 将显卡挂载到 Docker 容器中\n使用 docker-compose 部署 frigate，在配置文件中将设备`/dev/dri/renderD128`挂载到容器中即可\n```yaml\nservices:\n  frigate:\n    container_name: frigate\n    privileged: true\n    restart: unless-stopped\n    image: ghcr.io/blakeblackshear/frigate:stable\n    shm_size: \"256mb\"\n    devices:\n      - /dev/dri/renderD128\n```\n这样，容器就可以正常使用核显进行 ffmpeg 硬件加速了\n### 挂载 TUN 设备\n如果你需要在 LXC 容器中使用 WireGuard/ TailScale/Clash 等依赖 TUN 网络的设备，还需要添加以下内容：\n```conf\nlxc.cgroup2.devices.allow: c 10:200 rwm\nlxc.mount.entry: /dev/net/tun dev/net/tun none bind,create=file\n```\n否则会报错，提示找不到 Socket，这是因为 LXC 容器默认不会挂载 TUN 设备，所以无法访问\n```bash\nfailed to connect to local tailscaled (which appears to be running as tailscaled, pid 94512). Got error: Failed to connect to local Tailscale daemon for /localapi/v0/status; systemd tailscaled.service not running. Error: dial unix /var/run/tailscale/tailscaled.sock: connect: no such file or directory\n```', '在PVE的LXC容器中直通核心显卡', 'http://192.168.5.158:9000/blog/4ecd24b3f16a4264ad10e2de924b1125.png', 9, '10,12', 'public', '2024-05-11 23:06:39', '2024-05-11 23:15:49', 0);

-- --------------------------------------------------------

--
-- 表的结构 `tag_level_1`
--

CREATE TABLE `tag_level_1` (
  `tag_key` int UNSIGNED NOT NULL COMMENT '唯一标识',
  `title` varchar(20) NOT NULL COMMENT '标签名称',
  `level` int NOT NULL DEFAULT '2' COMMENT '一级标签',
  `color` char(8) NOT NULL DEFAULT '#ffffff' COMMENT '标签颜色'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='一级标签表';

--
-- 转存表中的数据 `tag_level_1`
--

INSERT INTO `tag_level_1` (`tag_key`, `title`, `level`, `color`) VALUES
(10, 'Linux', 2, '#FFFFFF'),
(11, 'Redroid', 2, '#91E33F'),
(12, 'PVE', 2, '#EFB520');

-- --------------------------------------------------------

--
-- 表的结构 `talks`
--

CREATE TABLE `talks` (
  `talk_key` int UNSIGNED NOT NULL COMMENT '唯一标识',
  `talk_title` varchar(50) NOT NULL COMMENT '说说标题',
  `content` text NOT NULL COMMENT '说说内容',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='说说表';

--
-- 转存表中的数据 `talks`
--

INSERT INTO `talks` (`talk_key`, `talk_title`, `content`, `create_time`, `update_time`) VALUES
(3, '测试', '这是一个测试', '2024-04-23 11:37:07', '2024-05-07 12:04:07');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE `user` (
  `id` int NOT NULL COMMENT '用户id',
  `username` varchar(100) NOT NULL COMMENT '账号',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `user_avatar` varchar(300) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png' COMMENT '用户头像地址',
  `role` varchar(20) NOT NULL COMMENT '用户身份',
  `email` varchar(40) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '电子邮箱',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户';

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `phone`, `user_avatar`, `role`, `email`, `update_time`, `create_time`) VALUES
(2, 'admin', 'fkaabdYwwVFRy2x4n3W8qQ==', '13800000003', 'http://192.168.5.158:9000/blog/3e15f461b1e04177a0e437cfc9895274.jpg', '管理员', '4@qq.com', '2024-06-05 16:11:55', '2024-04-28 12:36:10'),
(8, 'test1', 'F1cdjHfDnt6v+10N0pG+gg==', '13891000123', 'http://192.168.5.158:9000/blog/166c557ebb3945e7b80d17b86a5f2650.jpg', '用户', '123@qq.com', '2024-06-07 17:16:24', '2024-05-02 00:22:35'),
(13, '123456', 'duT/yw2+vDmBuRdBuev98w==', '1', 'http://192.168.5.158:9000/blog/43415410ca1843bd80c74967a360897e.jpg', '用户', '2659108771@qq.com', '2024-06-26 15:21:36', '2024-06-26 15:16:11');

--
-- 转储表的索引
--

--
-- 表的索引 `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`category_key`),
  ADD UNIQUE KEY `categorie_title` (`category_title`);

--
-- 表的索引 `images`
--
ALTER TABLE `images`
  ADD PRIMARY KEY (`image_key`);

--
-- 表的索引 `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`menu_id`);

--
-- 表的索引 `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`note_key`),
  ADD UNIQUE KEY `title` (`note_title`),
  ADD KEY `categories_title` (`note_category`);

--
-- 表的索引 `tag_level_1`
--
ALTER TABLE `tag_level_1`
  ADD PRIMARY KEY (`tag_key`),
  ADD UNIQUE KEY `title` (`title`);

--
-- 表的索引 `talks`
--
ALTER TABLE `talks`
  ADD PRIMARY KEY (`talk_key`);

--
-- 表的索引 `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `phone` (`phone`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `categories`
--
ALTER TABLE `categories`
  MODIFY `category_key` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识', AUTO_INCREMENT=10;

--
-- 使用表AUTO_INCREMENT `images`
--
ALTER TABLE `images`
  MODIFY `image_key` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识', AUTO_INCREMENT=201;

--
-- 使用表AUTO_INCREMENT `menu`
--
ALTER TABLE `menu`
  MODIFY `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键', AUTO_INCREMENT=403;

--
-- 使用表AUTO_INCREMENT `notes`
--
ALTER TABLE `notes`
  MODIFY `note_key` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识', AUTO_INCREMENT=11;

--
-- 使用表AUTO_INCREMENT `tag_level_1`
--
ALTER TABLE `tag_level_1`
  MODIFY `tag_key` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识', AUTO_INCREMENT=14;

--
-- 使用表AUTO_INCREMENT `talks`
--
ALTER TABLE `talks`
  MODIFY `talk_key` int UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '唯一标识', AUTO_INCREMENT=16;

--
-- 使用表AUTO_INCREMENT `user`
--
ALTER TABLE `user`
  MODIFY `id` int NOT NULL AUTO_INCREMENT COMMENT '用户id', AUTO_INCREMENT=14;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
