package org.qtproject.qt5.android.bindings;

import android.os.Bundle;

import java.util.ArrayList;

public class QtLoader {
    void startApp(){
        Bundle loaderParams = new Bundle();
        ArrayList<String> libraryList = new ArrayList<>();
        libraryList.add("libc++_shared.so");
        libraryList.add("libQt5Core_armeabi-v7a.so");
        libraryList.add("libQt5AndroidExtras_armeabi-v7a.so");
        libraryList.add("libQt5Concurrent_armeabi-v7a.so");
        libraryList.add("libQt5Gui_armeabi-v7a.so");
        libraryList.add("libQt5Network_armeabi-v7a.so");
        libraryList.add("libQt5Positioning_armeabi-v7a.so");
        libraryList.add("libQt5Qml_armeabi-v7a.so");
        libraryList.add("libQt5QmlModels_armeabi-v7a.so");
        libraryList.add("libQt5Quick_armeabi-v7a.so");
        libraryList.add("libQt5Sensors_armeabi-v7a.so");
        libraryList.add("libQt5Widgets_armeabi-v7a.so");
        libraryList.add("libQt5QmlWorkerScript_armeabi-v7a.so");
        libraryList.add("libQt5RemoteObjects_armeabi-v7a.so");
        libraryList.add("libQt5QuickTemplates2_armeabi-v7a.so");
        libraryList.add("libQt5QuickControls2_armeabi-v7a.so");
        libraryList.add("libplugins_platforms_qtforandroid_armeabi-v7a.so");
        libraryList.add("libplugins_bearer_qandroidbearer_armeabi-v7a.so");
        libraryList.add("libplugins_position_qtposition_android_armeabi-v7a.so");
        libraryList.add("libplugins_sensors_qtsensors_android_armeabi-v7a.so");

        loaderParams.putStringArrayList("native.libraries", libraryList);

        loaderParams.putStringArray("static.init.classes", new String[]{"org.qtproject.qt5.android.positioning.QtPositioning"});
        loaderParams.putStringArray("bundled.libraries", new String[]{"crypto_1_1", "ssl_1_1"});
        loaderParams.putString("environment.variables","QT_USE_ANDROID_NATIVE_DIALOGS=1");
        loaderParams.putString("QT_ANDROID_THEME", "Theme_Holo_Light/\tQT_ANDROID_THEME_DISPLAY_DPI=420");
        loaderParams.putInt("QT_BLOCK_EVENT_LOOPS_WHEN_SUSPENDED", 1);
    }
}
