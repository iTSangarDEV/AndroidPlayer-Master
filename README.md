# AndroidPlayer-Master
#Description
This library is a full implementation of a `Background MediaPlayer` and it offers a easy way to access and manage the MediaPlayer `Service`. 

# Usage
Service is already declared in the `AndroidManifest` file of this library, so you don't need to declare it in your project manifest, it is more simple, you just need to instantiate `PlayerController` class to access the background `Service`, `Notifications` and the `MediaPlayer` controllers.

Instantiate and Connect

```java
PlayerController playerController = new PlayerController();
playerController.connect(this);
```

Disconnect

```java
playerController.disconnect();
```

#Controll Player from Notification
Show 

```java
playerController.showNotification(this);
```

Remove

```java
playerController.removeNotification();
```

