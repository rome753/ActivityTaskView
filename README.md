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
![standard](https://github.com/rome753/ActivityTaskView/raw/master/screenshots/standard.gif)

#### singletop mode
![singletop](https://github.com/rome753/ActivityTaskView/raw/master/screenshots/singletop.gif)

#### singletask mode
![singletask](https://github.com/rome753/ActivityTaskView/raw/master/screenshots/singletask.gif)

#### singleinstance mode
![singleinstance](https://github.com/rome753/ActivityTaskView/raw/master/screenshots/singleinstance.gif)

#### dialog style activity
![dialog](https://github.com/rome753/ActivityTaskView/raw/master/screenshots/dialog.gif)

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
