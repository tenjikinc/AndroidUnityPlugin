## build

```bash
gradlew jar
```

## usage

Unity側

buildで生成されたjarを [UnityRoot]/Assets/Plugin/Android/ にコピー

### Toast

```csharp
using (var util = new AndroidJavaClass("jp.tenjik.unity.util.ToastUtil"))
{
    util.CallStatic("showLong", "Toast long length.");
    util.CallStatic("showShort", "Toast short length.");
}
```

### Version取得

```csharp
#if UNITY_ANDROID
/// <summary>
/// Androidのバージョンコードを取得する
/// </summary>
private static int GetAppVersionCode_Android()
{
    int code;
    using (var util = new AndroidJavaClass("jp.tenjik.unity.util.PackageInfoUtil"))
        code = util.CallStatic<int>("getVersionCode");
    return code;
}

/// <summary>
/// Androidのバージョンネームを取得する
/// </summary>
private static string GetAppVersionName_Android()
{
    string name;
    using (var util = new AndroidJavaClass("jp.tenjik.unity.util.PackageInfoUtil"))
        name = util.CallStatic<string>("getVersionName");
    return name;
}
#endif
```
