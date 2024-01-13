package com.example.qtexample;

import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.util.Log;

import java.io.FileOutputStream;
import java.util.ArrayList;

public abstract class QtLoader {

    public final static int MINISTRO_INSTALL_REQUEST_CODE = 0xf3ee; // request code used to know when Ministro instalation is finished
    public static final int MINISTRO_API_LEVEL = 5; // Ministro api level (check IMinistro.aidl file)
    public static final int NECESSITAS_API_LEVEL = 2; // Necessitas api level used by platform plugin
    public static final int QT_VERSION = 0x050700; // This app requires at least Qt version 5.7.0

    public static final String ERROR_CODE_KEY = "error.code";
    public static final String ERROR_MESSAGE_KEY = "error.message";
    public static final String DEX_PATH_KEY = "dex.path";
    public static final String LIB_PATH_KEY = "lib.path";
    public static final String LOADER_CLASS_NAME_KEY = "loader.class.name";
    public static final String NATIVE_LIBRARIES_KEY = "native.libraries";
    public static final String ENVIRONMENT_VARIABLES_KEY = "environment.variables";
    public static final String APPLICATION_PARAMETERS_KEY = "application.parameters";
    public static final String BUNDLED_LIBRARIES_KEY = "bundled.libraries";
    public static final String MAIN_LIBRARY_KEY = "main.library";
    public static final String STATIC_INIT_CLASSES_KEY = "static.init.classes";
    public static final String NECESSITAS_API_LEVEL_KEY = "necessitas.api.level";
    public static final String EXTRACT_STYLE_KEY = "extract.android.style";
    private static final String EXTRACT_STYLE_MINIMAL_KEY = "extract.android.style.option";

    // These parameters matter in case of deploying application as system (embedded into firmware)
    public static final String SYSTEM_LIB_PATH = "/system/lib/";
    public String[] SYSTEM_APP_PATHS = {"/system/priv-app/", "/system/app/"};

    /// Ministro server parameter keys
    public static final String REQUIRED_MODULES_KEY = "required.modules";
    public static final String APPLICATION_TITLE_KEY = "application.title";
    public static final String MINIMUM_MINISTRO_API_KEY = "minimum.ministro.api";
    public static final String MINIMUM_QT_VERSION_KEY = "minimum.qt.version";
    public static final String SOURCES_KEY = "sources";               // needs MINISTRO_API_LEVEL >=3 !!!
    // Use this key to specify any 3rd party sources urls
    // Ministro will download these repositories into their
    // own folders, check http://community.kde.org/Necessitas/Ministro
    // for more details.

    public static final String REPOSITORY_KEY = "repository";         // use this key to overwrite the default ministro repsitory
    public static final String ANDROID_THEMES_KEY = "android.themes"; // themes that your application uses

    public String APPLICATION_PARAMETERS = null; // use this variable to pass any parameters to your application,
    // the parameters must not contain any white spaces
    // and must be separated with "\t"
    // e.g "-param1\t-param2=value2\t-param3\tvalue3"

    public String ENVIRONMENT_VARIABLES = "QT_USE_ANDROID_NATIVE_DIALOGS=1";
    // use this variable to add any environment variables to your application.
    // the env vars must be separated with "\t"
    // e.g. "ENV_VAR1=1\tENV_VAR2=2\t"
    // Currently the following vars are used by the android plugin:
    // * QT_USE_ANDROID_NATIVE_DIALOGS -1 to use the android native dialogs.

    public String[] QT_ANDROID_THEMES = null;     // A list with all themes that your application want to use.
    // The name of the theme must be the same with any theme from
    // http://developer.android.com/reference/android/R.style.html
    // The most used themes are:
    //  * "Theme" - (fallback) check http://developer.android.com/reference/android/R.style.html#Theme
    //  * "Theme_Black" - check http://developer.android.com/reference/android/R.style.html#Theme_Black
    //  * "Theme_Light" - (default for API <=10) check http://developer.android.com/reference/android/R.style.html#Theme_Light
    //  * "Theme_Holo" - check http://developer.android.com/reference/android/R.style.html#Theme_Holo
    //  * "Theme_Holo_Light" - (default for API 11-13) check http://developer.android.com/reference/android/R.style.html#Theme_Holo_Light
    //  * "Theme_DeviceDefault" - check http://developer.android.com/reference/android/R.style.html#Theme_DeviceDefault
    //  * "Theme_DeviceDefault_Light" - (default for API 14+) check http://developer.android.com/reference/android/R.style.html#Theme_DeviceDefault_Light

    public String QT_ANDROID_DEFAULT_THEME = null; // sets the default theme.

    public static final int INCOMPATIBLE_MINISTRO_VERSION = 1; // Incompatible Ministro version. Ministro needs to be upgraded.

    public String[] m_sources = {"https://download.qt-project.org/ministro/android/qt5/qt-5.7"}; // Make sure you are using ONLY secure locations
    public String m_repository = "default"; // Overwrites the default Ministro repository
    // Possible values:
    // * default - Ministro default repository set with "Ministro configuration tool".
    // By default the stable version is used. Only this or stable repositories should
    // be used in production.
    // * stable - stable repository, only this and default repositories should be used
    // in production.
    // * testing - testing repository, DO NOT use this repository in production,
    // this repository is used to push a new release, and should be used to test your application.
    // * unstable - unstable repository, DO NOT use this repository in production,
    // this repository is used to push Qt snapshots.
    public ArrayList<String> m_qtLibs = null; // required qt libs
    public int m_displayDensity = -1;
    private ContextWrapper m_context;
    protected ComponentInfo m_contextInfo;
    private Class<?> m_delegateClass;

    private static ArrayList<FileOutputStream> m_fileOutputStreams = new ArrayList<FileOutputStream>();
    // List of open file streams associated with files copied during installation.

    QtLoader(ContextWrapper context, Class<?> clazz) {
        m_context = context;
        m_delegateClass = clazz;
    }

    // Implement in subclass
    protected void finish() {}

    protected String getTitle() {
        return "Qt";
    }

    protected void runOnUiThread(Runnable run) {
        run.run();
    }
    protected void downloadUpgradeMinistro(String msg)
    {
        Log.e(QtApplication.QtTAG, msg);
    }

    protected abstract String loaderClassName();
    protected abstract Class<?> contextClassName();

    Intent getIntent()
    {
        return null;
    }
    // Implement in subclass
}
