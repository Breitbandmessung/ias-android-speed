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

-include proguard-rules-base.pro


-keep interface com.zafaco.moduleSpeed.interfaces.** { *; }


-repackageclasses com.zafaco.moduleSpeed.ofs


-keepattributes InnerClasses
-keep class com.zafaco.moduleSpeed.** { *; }
-keep class com.zafaco.moduleSpeed.jni.*$* { *; }
-keep interface com.zafaco.moduleSpeed.** { *; }
-keep interface com.zafaco.moduleSpeed.jni.*$* { *; }

 -keep class com.zafaco.moduleSpeed.api.Speed {
     !private <fields>;
     public <fields>;
     public <methods>;
  }
