# ActivityTaskView
ActivityTaskView is a popup window that shows activity task stacks in a app,
monitors all activities' lifecycle.

## Introduction

### Colors of activity lifecycles
* <font color="green">onCreate</font>
* <font color="yellow">onStart</font>
* <font color="red">onResume</font>
* <font color="white">onPause</font>
* <font color="gray">onStop</font>
* <font color="black">onDestroy</font>

### Use in demo(show different launch mode)

#### standard mode
![standard](http://img.blog.csdn.net/20170418111814059?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9tZTc1Mw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### singletop mode
![singletop](http://img.blog.csdn.net/20170418111936118?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9tZTc1Mw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### singletask mode
![singletask](http://img.blog.csdn.net/20170418112005124?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9tZTc1Mw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### singleinstance mode
![singleinstance](http://img.blog.csdn.net/20170418112209329?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9tZTc1Mw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

#### dialog style activity
![dialog](http://img.blog.csdn.net/20170418112229361?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcm9tZTc1Mw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

## Usage
1) add this in build.gradle
> compile 'cc.rome753:activitytaskview:0.8.5'

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
