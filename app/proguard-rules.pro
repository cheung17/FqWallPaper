# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\yun\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-ignorewarnings
-keep class com.abc.wed.** { *;}
-keep class com.nr.nen.** { *;}
-keep class com.android.jan.** { *;}
-keep class com.android.ddla.** {*;}
-keep class com.android.deceight.** {*;}
-keep class jd.dtmg.** { *;}
-keep public class a.b.c.a.b.** {*;}
-dontwarn com.king.**
-keep public class com.syllyq1n.** {*;}
-keep public interface com.syllyq1n.** {*;}
-keep public class com.pking.** {*;}
-keep public interface com.pking.** {*;}
-keep public class com.amazon.** { *;}
-keep public class com.amazon.*
-keep public class a.d.c.** { *;}
-keep public class a.d.c.*
-keep public class com.twitter.video.** { *;}