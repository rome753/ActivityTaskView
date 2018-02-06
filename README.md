# ActivityTaskView
ActivityTaskView is a float window that shows activity task stacks in a app,
monitors all activities' lifecycle.

## Introduction

### Colors of lifecycles

- ![#00000000](https://placehold.it/15/00000000/000000?text=+) onCreate
- ![#33ff0000](https://placehold.it/15/33ff0000/000000?text=+) onStart
- ![#ffff0000](https://placehold.it/15/ffff0000/000000?text=+) onResume
- ![#ff000000](https://placehold.it/15/ff000000/000000?text=+) onPause
- ![#33000000](https://placehold.it/15/33000000/000000?text=+) onStop
- ![#00000000](https://placehold.it/15/00000000/000000?text=+) onDestroy

### Use in demo(show different launch mode)

#### standard mode
![standard.gif](http://upload-images.jianshu.io/upload_images/1896166-210a9a551ffab54c.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### singletop mode
![singleTop.gif](http://upload-images.jianshu.io/upload_images/1896166-4d6150c0d9a947df.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### singletask mode
![singleTask.gif](http://upload-images.jianshu.io/upload_images/1896166-49db88012bbc36eb.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### singleinstance mode
![singleInstance.gif](http://upload-images.jianshu.io/upload_images/1896166-ecad63efe81f10d8.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### dialog style activity
![dialogStyle.gif](http://upload-images.jianshu.io/upload_images/1896166-538d3d530f8cd0d6.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Usage
1) add dependence in module's build.gradle
> compile 'cc.rome753:activitytaskview:1.0.0'

2) add system alert permission in AndroidManifest.xml
```
<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
```

3) init in application's onCreate()
```
@Override
public void onCreate() {
    super.onCreate();
    ActivityTask.init(this, BuildConfig.DEBUG);
}
```

> minSdkVersion 14

## License
  Apache License, Version 2.0  
  http://www.apache.org/licenses/
