# ActivityTaskView 

[[中文文档]](https://www.jianshu.com/p/c34483bb5c0f)

![Overview.png](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/overview.png)

## 1. New UI and Function

![Overview.gif](https://github.com/rome753/ActivityTaskView/blob/master/screenshots/overview.gif)

- Show fragments in an activity
- Show lifecycle text behind Activity/Fragment
- Float window auto attach to border
- Tap float window to show tiny icon
- 0 dependency, just add 1 file to your project

**Define short name**

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

1. Install ActivityTaskView release apk, open it and grant window permission

https://github.com/rome753/ActivityTaskView/releases

2. Add ActivityTaskHelper.java file to **your project**

https://github.com/rome753/ActivityTaskView/blob/master/app/src/main/java/cc/rome753/activitytask/ActivityTaskHelper.java

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
