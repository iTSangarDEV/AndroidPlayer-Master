# AndroidPlayer-Master

#Description
This library is a full implementation of a `MediaPlayer` that runs in an `Background Service` and it offers an easy way to manage the MediaPlayer methods. 

# Usage
The Service is already declared in the `AndroidManifest` of this library, so you <b>don't</b> need to declare it in your project, it is more simple, you just need to instantiate `PlayerController` class to access the background `Service`, `Notifications` and the `MediaPlayer` controllers.

Instantiate PlayerController class.

```java
PlayerController playerController = new PlayerController();
```

Connect to the Background Service. 

```java
playerController.connect(this);
```

Disconnect from service

```java
playerController.disconnect();
```

#Controll Player from Notification
Show  notification

```java
@Override
public void onPause(){
   playerController.removeNotification();
}
```

Remove notification

```java
@Override
public void onResume(){
   playerController.showNotification(this);
}

```

