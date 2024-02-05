# ActivityTaskView

[[中文文档]](https://www.jianshu.com/p/c34483bb5c0f)

![Overview.png](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/overview.png)

## 1. New UI and Function

![Overview.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/overview.gif)

WebTools - Show in PC browser
- Show fragments in an activity
- Show lifecycle text behind Activity/Fragment
- No need to install app

ActivityTaskView APP - Show in Android float window
- Show fragments in an activity
- Show lifecycle text behind Activity/Fragment
- Float window auto attach to border
- Tap float window to show tiny icon

Name | Short name
-----|-----------
Activity | A…
Fragment | F…
SaveInstanceState | SIS


## 2. Launch mode demo

#### standard mode
![standard.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/s.gif)

#### singletop mode
![singleTop.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/s-to.gif)

#### singletask mode
![singleTask.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/s-ta.gif)

#### singleinstance mode
![singleInstance.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/s-in.gif)

## 3. Use in your project

### Use WebTools(Recommend)
1. Add jitpack to your project's build.gradle.
```
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```

2. Add dependency to your project, app module's build.gradle(No need to init, auto init with App StartUp).
```
    debugImplementation "com.github.rome753.ActivityTaskView:lib:1.0"
```

3. Enter WebTools directory, and run `npm install` to install dependencies, then run `node server.js`.
> For windows, you can click run.bat to run 'node server.js'. For Mac, click run.command(chmod +x first).

4. Launch your app, and lifecycle will be showed in the browser.


### Use ActivityTaskView APP
1. Add jitpack to your project's build.gradle.
```
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```

2. Add dependency to your project, app module's build.gradle(No need to init, auto init with App StartUp).
```
    debugImplementation "com.github.rome753.ActivityTaskView:lib:1.0"
```

3. Install ActivityTaskView release apk, open it and grant window permission

https://github.com/rome753/ActivityTaskView/releases

4. Launch your app, and lifecycles will be showed in the float window.

## License
Apache License, Version 2.0  
http://www.apache.org/licenses/
