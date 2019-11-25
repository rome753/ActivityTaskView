# ActivityTaskView

![AcitivtyTask.png](https://upload-images.jianshu.io/upload_images/1896166-3055e957eb03b6d4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## Introduction
[中文文档](https://www.jianshu.com/p/c34483bb5c0f)

### New UI and Function

![ActivityTask.gif](https://upload-images.jianshu.io/upload_images/1896166-4a0425e42ae702c1.gif?imageMogr2/auto-orient/strip)

- Show fragment tree of an activity
- Show lifecycle text behind Activity/Fragment
- Float window auto attach to border
- Tap float window to show tiny icon, and tap to show again

**Define short name**

Name | Short name
-----|-----------
Activity | A…
Fragment | F…
SaveInstanceState | SIS


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
1. Install ActivityTaskView release apk, open it and grant window permission

https://github.com/rome753/ActivityTaskView/releases

2. Add ActivityTaskHelper.java file to you project

https://github.com/rome753/ActivityTaskView/blob/master/app/src/main/java/cc/rome753/demo/ActivityTaskHelper.java

3. Init ActivityTaskHelper in your application's onCreate()
```
    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            ActivityTaskHelper.init(this);
        }
    }
```

4. Launch your app, and lifecycles will be showed in the float window.

## License
  Apache License, Version 2.0  
  http://www.apache.org/licenses/
