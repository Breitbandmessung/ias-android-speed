#-keep class com.zafaco.speed.** { *; }
#-dontwarn com.google.**
#-dontwarn com.zafaco.**
#
#-keeppackagenames org.jsoup.nodes
#
#-keepattributes SourceFile,LineNumberTable
#-keep class com.zafaco.**
#-keepclassmembers class com.zafaco.** { *; }
#-keep enum com.zafaco.**
#-keepclassmembers enum com.zafaco.** { *; }
#-keep interface com.zafaco.**
#-keepclassmembers interface com.zafaco.** { *; }
#
#-keepattributes Signature
#
## Google Map
#-keep class com.google.android.gms.maps.** { *; }
#-keep interface com.google.android.gms.maps.** { *; }

-keep class com.zafaco.speed.** {*;}
-keep interface com.zafaco.speed.** {*;}