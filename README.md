## build

```
gradlew unitypluginsdk:makeJar
```

### output jar path

unitypluginsdk/release/laiblitzLibrary.jar

## usage

Unity side

Copy to Unity's plugin folder

```
cp unitypluginsdk/release/laiblitzLibrary.jar [UnityRoot]/Assets/Plugin/Android/
```

### Toast

```csharp
using (var util = new AndroidJavaClass("jp.co.laiblitz.android.unity.ToastUtil"))
{
    util.CallStatic("showLong", "Toast long length.");
    util.CallStatic("showShort", "Toast short length.");
}
```

### Get version code

```csharp
#if UNITY_ANDROID
/// <summary>
/// Get android version code
/// </summary>
private static int GetAppVersionCode_Android()
{
    int code;
    using (var util = new AndroidJavaClass("jp.co.laiblitz.android.unity.PackageInfoUtil"))
        code = util.CallStatic<int>("getVersionCode");
    return code;
}

/// <summary>
/// Get android version name
/// </summary>
private static string GetAppVersionName_Android()
{
    string name;
    using (var util = new AndroidJavaClass("jp.co.laiblitz.android.unity.PackageInfoUtil"))
        name = util.CallStatic<string>("getVersionName");
    return name;
}
#endif
```
### Get customScheme

```csharp
/// <summary>
/// get customscheme
/// </summary>
/// <param name="isClean">with cache clear</param>
public static string GetCustomScheme(bool isClean = false)
{
#if UNITY_EDITOR
    return string.Empty;
#else
    var jObj = new AndroidJavaObject("jp.co.laiblitz.android.ApplicationCache");
    var instance = jObj.CallStatic<AndroidJavaObject>("getInstance");
    var customScheme = instance.Get<string>("customScheme");

    if (isClean)
        instance.Set<string>("customScheme", null);

    return customScheme;
#endif
    }
}
```

AndroidManifest.xml

```xml
<!-- URL scheme (com.example.unity://) -->
<activity android:name="jp.co.laiblitz.android.IntentReceiveActivity">
  <intent-filter>
    <action android:name="android.intent.action.VIEW" />
    <category android:name="android.intent.category.DEFAULT" />
    <category android:name="android.intent.category.BROWSABLE" />
    <data android:scheme="com.example.unity" />
  </intent-filter>
</activity>
```


### Local notification

```csharp
using UnityEngine;
using System.Runtime.InteropServices;
using System;

namespace App.Util
{
    /// <summary>
    /// Local notification utility
    /// </summary>
    public static class LocalNotificationUtil
    {
#if UNITY_EDITOR
#elif UNITY_ANDROID
        private static readonly string javaPackageNamge = "jp.co.laiblitz.android.unity.NotificationUtil";

        private static AndroidJavaObject javaObj
        {
            get
            {
                if (_javaObj == null)
                    _javaObj = new AndroidJavaObject(javaPackageNamge);
                return _javaObj;
            }
        }
        private static AndroidJavaObject _javaObj = null;
#endif

        /// <summary>
        /// Add schedule
        /// </summary>
        /// <param name="notificationId">Identification id</param>
        /// <param name="title">message title</param>
        /// <param name="message">message body</param>
        /// <param name="delay">notification delay time</param>
        public static void SetLocalNotification(int notificationId, string title, string message, int delay)
        {
#if UNITY_EDITOR
#elif UNITY_ANDROID
            javaObj.CallStatic("setLocalNotification", notificationId, title, message, delay);
#endif
        }

        /// <summary>
        /// Delete added schedule
        /// </summary>
        /// <param name="notificationId">Identification id</param>
        public static void CancelLocalNotification(int notificationId)
        {
#if UNITY_EDITOR
#elif UNITY_ANDROID
            javaObj.CallStatic("cancelLocalNotification", notificationId);
#endif
        }
    }
}

```
