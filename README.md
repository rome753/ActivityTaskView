# ActivityTaskView
ActivityTaskView is a float window that shows activity task stacks in a app,
monitors all activities' lifecycle.

## Introduction

### Colors of activity lifecycles-

- ![#00000000](https://placehold.it/15/00FF00/000000?text=+) onCreate
- ![#33ff0000](https://placehold.it/15/FFFF00/000000?text=+) onStart
- ![#ffff0000](https://placehold.it/15/FF0000/000000?text=+) onResume
- ![#ff000000](https://placehold.it/15/FFFFFF/000000?text=+) onPause
- ![#33000000](https://placehold.it/15/888888/000000?text=+) onStop
- ![#00000000](https://placehold.it/15/000000/000000?text=+) onDestroy

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
