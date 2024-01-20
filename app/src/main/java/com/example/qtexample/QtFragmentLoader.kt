package com.example.qtexample

import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import dalvik.system.DexClassLoader
import org.qtproject.qt5.android.EmbeddedQtDelegate
import org.qtproject.qt5.android.QtActivityDelegate

class QtFragmentLoader(private val activity: Activity) : QtLoader(QtFragment::class.java) {
    fun onCreate(savedInstanceState: Bundle?, fragmentView:ViewGroup) {
        try {
            m_contextInfo = activity.packageManager
                .getActivityInfo(activity.componentName, PackageManager.GET_META_DATA)
            val theme = (m_contextInfo as ActivityInfo).themeResource
            for (f in Class.forName("android.R\$style").declaredFields) {
                if (f.getInt(null) == theme) {
                    QT_ANDROID_THEMES = arrayOf(f.name)
                    QT_ANDROID_DEFAULT_THEME = f.name
                    break
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            finish()
            return
        }
        try {
            activity.setTheme(
                Class.forName("android.R\$style").getDeclaredField(QT_ANDROID_DEFAULT_THEME)
                    .getInt(null)
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        activity.requestWindowFeature(Window.FEATURE_ACTION_BAR)
        if (QtApplication.m_delegateObject != null && QtApplication.onCreate != null) {
            QtApplication.invokeDelegateMethod(QtApplication.onCreate, savedInstanceState)
            return
        }
        m_displayDensity = activity.resources.displayMetrics.densityDpi
        ENVIRONMENT_VARIABLES += ("\tQT_ANDROID_THEME=" + QT_ANDROID_DEFAULT_THEME
                + "/\tQT_ANDROID_THEME_DISPLAY_DPI=" + m_displayDensity + "\t")
        if (null == activity.lastNonConfigurationInstance) {
            ENVIRONMENT_VARIABLES += if (m_contextInfo.metaData.containsKey("android.app.background_running")
                && m_contextInfo.metaData.getBoolean("android.app.background_running")
            ) {
                "QT_BLOCK_EVENT_LOOPS_WHEN_SUSPENDED=0\t"
            } else {
                "QT_BLOCK_EVENT_LOOPS_WHEN_SUSPENDED=1\t"
            }
            if (m_contextInfo.metaData.containsKey("android.app.auto_screen_scale_factor")
                && m_contextInfo.metaData.getBoolean("android.app.auto_screen_scale_factor")
            ) {
                ENVIRONMENT_VARIABLES += "QT_AUTO_SCREEN_SCALE_FACTOR=1\t"
            }

            start(fragmentView)
        }
    }

    private fun start(fragmentView:ViewGroup){
        println("QQQQQQQQ4")
        val loaderParams = Bundle()

        val nativeLibraryPrefix = activity.applicationInfo.nativeLibraryDir + "/"
        val qtLibs = arrayListOf("c++_shared", "Qt5Core_arm64-v8a", "Qt5AndroidExtras_arm64-v8a", "Qt5Concurrent_arm64-v8a", "Qt5Gui_arm64-v8a",
            "Qt5Network_arm64-v8a", "Qt5Positioning_arm64-v8a", "Qt5Qml_arm64-v8a", "Qt5QmlModels_arm64-v8a", "Qt5Quick_arm64-v8a", "Qt5Sensors_arm64-v8a",
            "Qt5Widgets_arm64-v8a", "Qt5QmlWorkerScript_arm64-v8a", "Qt5RemoteObjects_arm64-v8a", "Qt5QuickTemplates2_arm64-v8a","Qt5QuickControls2_arm64-v8a")

        val localLibs = arrayListOf("libplugins_platforms_qtforandroid_arm64-v8a.so", "libplugins_bearer_qandroidbearer_arm64-v8a.so",
            "libplugins_position_qtposition_android_arm64-v8a.so", "libplugins_sensors_qtsensors_android_arm64-v8a.so")

        val libraryList = arrayListOf<String>()
        libraryList.addAll(qtLibs.map { "${nativeLibraryPrefix}lib$it.so" }.toMutableList())
        libraryList.addAll(localLibs.map { "$nativeLibraryPrefix$it" })

        loaderParams.putInt("error.code", 0)
        loaderParams.putString("dex.path", String())
        loaderParams.putString("loader.class.name", "org.qtproject.qt5.android.QtActivityDelegate")
        loaderParams.putStringArray("static.init.classes", arrayOf("org.qtproject.qt5.android.positioning.QtPositioning"))
        loaderParams.putStringArrayList("native.libraries", libraryList)
        loaderParams.putString("environment.variables", "QT_USE_ANDROID_NATIVE_DIALOGS=1\tQT_ANDROID_THEME=Theme_Holo_Light/\tQT_ANDROID_THEME_DISPLAY_DPI=560\tQT_BLOCK_EVENT_LOOPS_WHEN_SUSPENDED=1\tQT_BUNDLED_LIBS_PATH=$nativeLibraryPrefix\tMINISTRO_ANDROID_STYLE_PATH=/data/user/0/${activity.packageName}/qt-reserved-files/android-style/560/\tQT_ANDROID_THEMES_ROOT_PATH=/data/user/0/${activity.packageName}/qt-reserved-files/android-style/")

        loaderParams.putString("main.library", "stellariumMobile_arm64-v8a")
        loaderParams.putStringArrayList("bundled.libraries", arrayListOf("crypto_1_1", "ssl_1_1"))
        loaderParams.putInt("necessitas.api.level", 2)


        val classLoader = DexClassLoader(loaderParams.getString("dex.path")!!, activity.getDir("outdex", Context.MODE_PRIVATE).absolutePath,
            loaderParams.getString("lib.path"), activity.classLoader)

        val activityDelegate = EmbeddedQtDelegate()
        activityDelegate.loadApplication(activity, classLoader, loaderParams)
        QtApplication.setQtContextDelegate(QtActivity::class.java, activityDelegate)
        activityDelegate.startApplication(fragmentView)
    }
}