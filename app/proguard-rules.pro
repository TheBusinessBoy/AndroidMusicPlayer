# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep all classes in the main package
-keep class com.example.androidmusicplayer.** { *; }

# Keep MediaPlayer related classes
-keep class android.media.** { *; }
-keep class androidx.media.** { *; }

# Keep ExoPlayer classes (if used)
-keep class com.google.android.exoplayer2.** { *; }

# Keep FFmpeg classes (for conversion)
-keep class com.arthenica.mobileffmpeg.** { *; }

# Keep Compose classes
-keep class androidx.compose.** { *; }

# Keep ViewModel classes
-keep class androidx.lifecycle.** { *; }

# Keep Room database classes
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase { *; }

# Keep Retrofit/OkHttp classes (for downloads)
-keep class retrofit2.** { *; }
-keep class okhttp3.** { *; }

# Keep Gson classes (for JSON parsing)
-keep class com.google.gson.** { *; }

# Keep Kotlin coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep enum classes
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep Parcelable implementations
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Serializable classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

