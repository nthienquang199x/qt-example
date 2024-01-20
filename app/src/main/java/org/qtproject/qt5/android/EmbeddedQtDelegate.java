//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.qtproject.qt5.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.hardware.display.DisplayManager;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.method.MetaKeyKeyListener;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.PopupMenu;

import org.qtproject.qt5.android.accessibility.QtAccessibilityDelegate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class EmbeddedQtDelegate extends QtActivityDelegate {
    private Activity m_activity = null;
    private Method m_super_dispatchKeyEvent = null;
    private Method m_super_onRestoreInstanceState = null;
    private Method m_super_onRetainNonConfigurationInstance = null;
    private Method m_super_onSaveInstanceState = null;
    private Method m_super_onKeyDown = null;
    private Method m_super_onKeyUp = null;
    private Method m_super_onConfigurationChanged = null;
    private Method m_super_onActivityResult = null;
    private Method m_super_dispatchGenericMotionEvent = null;
    private Method m_super_onWindowFocusChanged = null;
    private static final String NATIVE_LIBRARIES_KEY = "native.libraries";
    private static final String BUNDLED_LIBRARIES_KEY = "bundled.libraries";
    private static final String MAIN_LIBRARY_KEY = "main.library";
    private static final String ENVIRONMENT_VARIABLES_KEY = "environment.variables";
    private static final String APPLICATION_PARAMETERS_KEY = "application.parameters";
    private static final String STATIC_INIT_CLASSES_KEY = "static.init.classes";
    private static final String NECESSITAS_API_LEVEL_KEY = "necessitas.api.level";
    private static final String EXTRACT_STYLE_KEY = "extract.android.style";
    private static final String EXTRACT_STYLE_MINIMAL_KEY = "extract.android.style.option";
    private static String m_environmentVariables = null;
    private static String m_applicationParameters = null;
    private int m_currentRotation = -1;
    private int m_nativeOrientation = 0;
    private String m_mainLib;
    private long m_metaState;
    private int m_lastChar = 0;
    private int m_softInputMode = 0;
    private boolean m_fullScreen = false;
    private boolean m_started = false;
    private HashMap<Integer, QtSurface> m_surfaces = null;
    private HashMap<Integer, View> m_nativeViews = null;
    private QtLayout m_layout = null;
    private ImageView m_splashScreen = null;
    private boolean m_splashScreenSticky = false;
    private QtEditText m_editText = null;
    private InputMethodManager m_imm = null;
    private boolean m_quitApp = true;
    private View m_dummyView = null;
    private boolean m_keyboardIsVisible = false;
    public boolean m_backKeyPressedSent = false;
    private long m_showHideTimeStamp = System.nanoTime();
    private int m_portraitKeyboardHeight = 0;
    private int m_landscapeKeyboardHeight = 0;
    private int m_probeKeyboardHeightDelay = 50;
    private CursorHandle m_cursorHandle;
    private CursorHandle m_leftSelectionHandle;
    private CursorHandle m_rightSelectionHandle;
    private EditPopupMenu m_editPopupMenu;
    private final int ImhHiddenText = 1;
    private final int ImhSensitiveData = 2;
    private final int ImhNoAutoUppercase = 4;
    private final int ImhPreferNumbers = 8;
    private final int ImhPreferUppercase = 16;
    private final int ImhPreferLowercase = 32;
    private final int ImhNoPredictiveText = 64;
    private final int ImhDate = 128;
    private final int ImhTime = 256;
    private final int ImhPreferLatin = 512;
    private final int ImhMultiLine = 1024;
    private final int ImhDigitsOnly = 65536;
    private final int ImhFormattedNumbersOnly = 131072;
    private final int ImhUppercaseOnly = 262144;
    private final int ImhLowercaseOnly = 524288;
    private final int ImhDialableCharactersOnly = 1048576;
    private final int ImhEmailCharactersOnly = 2097152;
    private final int ImhUrlCharactersOnly = 4194304;
    private final int ImhLatinOnly = 8388608;
    private final int EnterKeyDefault = 0;
    private final int EnterKeyReturn = 1;
    private final int EnterKeyDone = 2;
    private final int EnterKeyGo = 3;
    private final int EnterKeySend = 4;
    private final int EnterKeySearch = 5;
    private final int EnterKeyNext = 6;
    private final int EnterKeyPrevious = 7;
    public static final int ApplicationSuspended = 0;
    public static final int ApplicationHidden = 1;
    public static final int ApplicationInactive = 2;
    public static final int ApplicationActive = 4;
    private static final int CursorHandleNotShown = 0;
    private static final int CursorHandleShowNormal = 1;
    private static final int CursorHandleShowSelection = 2;
    private static final int CursorHandleShowEdit = 256;
    private boolean m_optionsMenuIsVisible = false;
    private boolean m_contextMenuVisible = false;

    public EmbeddedQtDelegate() {
    }

    public void setFullScreen(boolean var1) {
        if (this.m_fullScreen != var1) {
            this.m_activity.getWindow().addFlags(2048);
            this.m_activity.getWindow().clearFlags(1024);
            this.m_activity.getWindow().getDecorView().setSystemUiVisibility(0);

            this.m_layout.requestLayout();
        }
    }

    public void updateFullScreen() {
        if (this.m_fullScreen) {
            this.m_fullScreen = false;
            this.setFullScreen(true);
        }

    }

    public boolean setKeyboardVisibility(boolean var1, long var2) {
        if (this.m_showHideTimeStamp > var2) {
            return false;
        } else {
            this.m_showHideTimeStamp = var2;
            if (this.m_keyboardIsVisible == var1) {
                return false;
            } else {
                this.m_keyboardIsVisible = var1;
                QtNative.keyboardVisibilityChanged(this.m_keyboardIsVisible);
                if (!var1) {
                    this.updateFullScreen();
                }

                return true;
            }
        }
    }

    public void resetSoftwareKeyboard() {
        if (this.m_imm != null) {
            this.m_editText.postDelayed(new Runnable() {
                public void run() {
                    EmbeddedQtDelegate.this.m_imm.restartInput(EmbeddedQtDelegate.this.m_editText);
                    EmbeddedQtDelegate.this.m_editText.m_optionsChanged = false;
                }
            }, 5L);
        }
    }

    public void showSoftwareKeyboard(final int var1, final int var2, final int var3, final int var4, final int var5, final int var6) {
        if (this.m_imm != null) {
            DisplayMetrics var7 = new DisplayMetrics();
            this.m_activity.getWindowManager().getDefaultDisplay().getMetrics(var7);
            int var8;
            if (var7.widthPixels < var7.heightPixels) {
                var8 = this.m_portraitKeyboardHeight != 0 ? this.m_portraitKeyboardHeight : var7.heightPixels * 3 / 5;
            } else {
                var8 = this.m_landscapeKeyboardHeight != 0 ? this.m_landscapeKeyboardHeight : var7.heightPixels / 3;
            }

            if (this.m_softInputMode != 0) {
                this.m_activity.getWindow().setSoftInputMode(this.m_softInputMode);
                boolean var9 = (this.m_softInputMode & 2) != 0;
                if (var9) {
                    return;
                }
            } else if (var4 > var8) {
                this.m_activity.getWindow().setSoftInputMode(17);
            } else {
                this.m_activity.getWindow().setSoftInputMode(33);
            }

            int var12 = 0;
            int var10 = 6;
            switch (var6) {
                case 1:
                    var10 = 1073741824;
                case 2:
                default:
                    break;
                case 3:
                    var10 = 2;
                    break;
                case 4:
                    var10 = 4;
                    break;
                case 5:
                    var10 = 3;
                    break;
                case 6:
                    var10 = 5;
                    break;
                case 7:
                    var10 = 7;
            }

            int var11 = 1;
            if ((var5 & 196616) != 0) {
                var11 = 2;
                if ((var5 & 131072) != 0) {
                    var11 |= 12288;
                }

                if ((var5 & 1) != 0) {
                    var11 |= 16;
                }
            } else if ((var5 & 1048576) != 0) {
                var11 = 3;
            } else if ((var5 & 384) != 0) {
                var11 = 4;
                if ((var5 & 384) != 384) {
                    if ((var5 & 128) != 0) {
                        var11 |= 16;
                    }

                    if ((var5 & 256) != 0) {
                        var11 |= 32;
                    }
                }
            } else {
                if ((var5 & 6291456) != 0) {
                    if ((var5 & 4194304) != 0) {
                        var11 |= 16;
                        if (var6 == 0) {
                            var10 = 2;
                        }
                    } else if ((var5 & 2097152) != 0) {
                        var11 |= 32;
                    }
                } else if ((var5 & 1) != 0) {
                    var11 |= 128;
                } else if ((var5 & 2) != 0 || (var5 & 64) != 0 && System.getenv("QT_ANDROID_ENABLE_WORKAROUND_TO_DISABLE_PREDICTIVE_TEXT") != null) {
                    var11 |= 144;
                }

                if ((var5 & 1024) != 0) {
                    var11 |= 131072;
                }

                if ((var5 & 262144) != 0) {
                    var12 |= 4096;
                    var11 |= 4096;
                } else if ((var5 & 524288) == 0 && (var5 & 4) == 0) {
                    var12 |= 16384;
                    var11 |= 16384;
                }

                if ((var5 & 64) != 0 || (var5 & 2) != 0 || (var5 & 1) != 0) {
                    var11 |= 524288;
                }
            }

            if (var6 == 0 && (var5 & 1024) != 0) {
                var10 = 1073741824;
            }

            this.m_editText.setInitialCapsMode(var12);
            this.m_editText.setImeOptions(var10);
            this.m_editText.setInputType(var11);
            this.m_layout.setLayoutParams(this.m_editText, new QtLayout.LayoutParams(var3, var4, var1, var2), false);
            this.m_editText.requestFocus();
            this.m_editText.postDelayed(new Runnable() {
                public void run() {
                    EmbeddedQtDelegate.this.m_imm.showSoftInput(EmbeddedQtDelegate.this.m_editText, 0, new ResultReceiver(new Handler()) {
                        protected void onReceiveResult(int var1x, Bundle var2x) {
                            switch (var1x) {
                                case 1:
                                case 3:
                                    EmbeddedQtDelegate.this.setKeyboardVisibility(false, System.nanoTime());
                                    break;
                                case 2:
                                    QtNativeInputConnection.updateCursorPosition();
                                case 0:
                                    EmbeddedQtDelegate.this.setKeyboardVisibility(true, System.nanoTime());
                                    if (EmbeddedQtDelegate.this.m_softInputMode == 0) {
                                        EmbeddedQtDelegate.this.m_layout.postDelayed(new Runnable() {
                                            public void run() {
                                                if (EmbeddedQtDelegate.this.m_keyboardIsVisible) {
                                                    DisplayMetrics var1x = new DisplayMetrics();
                                                    EmbeddedQtDelegate.this.m_activity.getWindowManager().getDefaultDisplay().getMetrics(var1x);
                                                    Rect var2x = new Rect();
                                                    EmbeddedQtDelegate.this.m_activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(var2x);
                                                    if (var1x.heightPixels != var2x.bottom) {
                                                        if (var1x.widthPixels > var1x.heightPixels) {
                                                            if (EmbeddedQtDelegate.this.m_landscapeKeyboardHeight != var2x.bottom) {
                                                                EmbeddedQtDelegate.this.m_landscapeKeyboardHeight = var2x.bottom;
                                                                EmbeddedQtDelegate.this.showSoftwareKeyboard(var1, var2, var3, var4, var5, var6);
                                                            }
                                                        } else if (EmbeddedQtDelegate.this.m_portraitKeyboardHeight != var2x.bottom) {
                                                            EmbeddedQtDelegate.this.m_portraitKeyboardHeight = var2x.bottom;
                                                            EmbeddedQtDelegate.this.showSoftwareKeyboard(var1, var2, var3, var4, var5, var6);
                                                        }
                                                    } else if (EmbeddedQtDelegate.this.m_probeKeyboardHeightDelay < 1000) {
                                                        EmbeddedQtDelegate.this.m_probeKeyboardHeightDelay = EmbeddedQtDelegate.this.m_probeKeyboardHeightDelay * 2;
                                                    }

                                                }
                                            }
                                        }, (long)EmbeddedQtDelegate.this.m_probeKeyboardHeightDelay);
                                    }
                            }

                        }
                    });
                    if (EmbeddedQtDelegate.this.m_editText.m_optionsChanged) {
                        EmbeddedQtDelegate.this.m_imm.restartInput(EmbeddedQtDelegate.this.m_editText);
                        EmbeddedQtDelegate.this.m_editText.m_optionsChanged = false;
                    }

                }
            }, 15L);
        }
    }

    public void hideSoftwareKeyboard() {
        if (this.m_imm != null) {
            this.m_imm.hideSoftInputFromWindow(this.m_editText.getWindowToken(), 0, new ResultReceiver(new Handler()) {
                protected void onReceiveResult(int var1, Bundle var2) {
                    switch (var1) {
                        case 0:
                        case 2:
                            EmbeddedQtDelegate.this.setKeyboardVisibility(true, System.nanoTime());
                            break;
                        case 1:
                        case 3:
                            EmbeddedQtDelegate.this.setKeyboardVisibility(false, System.nanoTime());
                    }

                }
            });
        }
    }

    String getAppIconSize(Activity var1) {
        int var2 = var1.getResources().getDimensionPixelSize(17104896);
        if (var2 < 36 || var2 > 512) {
            DisplayMetrics var3 = new DisplayMetrics();
            var1.getWindowManager().getDefaultDisplay().getMetrics(var3);
            var2 = var3.densityDpi / 10 * 3;
            if (var2 < 36) {
                var2 = 36;
            }

            if (var2 > 512) {
                var2 = 512;
            }
        }

        return "\tQT_ANDROID_APP_ICON_SIZE=" + var2;
    }

    public void updateSelection(int var1, int var2, int var3, int var4) {
        if (this.m_imm != null) {
            this.m_imm.updateSelection(this.m_editText, var1, var2, var3, var4);
        }
    }

    public void updateHandles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9) {
        switch (var1 & 255) {
            case 0:
                if (this.m_cursorHandle != null) {
                    this.m_cursorHandle.hide();
                    this.m_cursorHandle = null;
                }

                if (this.m_rightSelectionHandle != null) {
                    this.m_rightSelectionHandle.hide();
                    this.m_leftSelectionHandle.hide();
                    this.m_rightSelectionHandle = null;
                    this.m_leftSelectionHandle = null;
                }

                if (this.m_editPopupMenu != null) {
                    this.m_editPopupMenu.hide();
                }
                break;
            case 1:
                if (this.m_cursorHandle == null) {
                    this.m_cursorHandle = new CursorHandle(this.m_activity, this.m_layout, 1, 16843463, false);
                }

                this.m_cursorHandle.setPosition(var5, var6);
                if (this.m_rightSelectionHandle != null) {
                    this.m_rightSelectionHandle.hide();
                    this.m_leftSelectionHandle.hide();
                    this.m_rightSelectionHandle = null;
                    this.m_leftSelectionHandle = null;
                }
                break;
            case 2:
                if (this.m_rightSelectionHandle == null) {
                    this.m_leftSelectionHandle = new CursorHandle(this.m_activity, this.m_layout, 2, !var9 ? 16843461 : 16843462, var9);
                    this.m_rightSelectionHandle = new CursorHandle(this.m_activity, this.m_layout, 3, !var9 ? 16843462 : 16843461, var9);
                }

                this.m_leftSelectionHandle.setPosition(var5, var6);
                this.m_rightSelectionHandle.setPosition(var7, var8);
                if (this.m_cursorHandle != null) {
                    this.m_cursorHandle.hide();
                    this.m_cursorHandle = null;
                }

                var1 |= 256;
        }

        if (QtNative.hasClipboardText()) {
            var4 |= 4;
        } else {
            var4 &= -5;
        }

        if ((var1 & 256) == 256 && var4 != 0) {
            this.m_editPopupMenu.setPosition(var2, var3, var4, this.m_cursorHandle, this.m_leftSelectionHandle, this.m_rightSelectionHandle);
        } else if (this.m_editPopupMenu != null) {
            this.m_editPopupMenu.hide();
        }

    }

    public boolean loadApplication(Activity var1, ClassLoader var2, Bundle var3) {
        if (var3.containsKey("native.libraries") && var3.containsKey("bundled.libraries") && var3.containsKey("environment.variables")) {
            this.m_activity = var1;
            this.setActionBarVisibility(false);
            QtNative.setActivity(this.m_activity, this);
            QtNative.setClassLoader(var2);
            int var6;
            String var7;
            if (var3.containsKey("static.init.classes")) {
                String[] var4 = (String[])Objects.requireNonNull(var3.getStringArray("static.init.classes"));
                int var5 = var4.length;

                for(var6 = 0; var6 < var5; ++var6) {
                    var7 = var4[var6];
                    if (var7.length() != 0) {
                        try {
                            Class var8 = var2.loadClass(var7);
                            Object var9 = var8.newInstance();

                            Method var10;
                            try {
                                var10 = var8.getMethod("setActivity", Activity.class, Object.class);
                                var10.invoke(var9, this.m_activity, this);
                            } catch (Exception var15) {
                                Log.d("Qt JAVA", "Class " + var7 + " does not implement setActivity method");
                            }

                            try {
                                var10 = var8.getMethod("setContext", Context.class);
                                var10.invoke(var9, this.m_activity);
                            } catch (Exception var14) {
                                var14.printStackTrace();
                            }
                        } catch (Exception var16) {
                            var16.printStackTrace();
                        }
                    }
                }
            }

            QtNative.loadQtLibraries(var3.getStringArrayList("native.libraries"));
            ArrayList var17 = var3.getStringArrayList("bundled.libraries");
            String var18 = QtNativeLibrariesDir.nativeLibrariesDir(this.m_activity);
            QtNative.loadBundledLibraries(var17, var18);
            this.m_mainLib = var3.getString("main.library");
            if (null == this.m_mainLib && var17.size() > 0) {
                this.m_mainLib = (String)var17.get(var17.size() - 1);
                var17.remove(var17.size() - 1);
            }

            if (var3.containsKey("extract.android.style")) {
                String var19 = var3.getString("extract.android.style");
                new ExtractStyle(this.m_activity, var19, var3.containsKey("extract.android.style.option") && var3.getBoolean("extract.android.style.option"));
            }

            try {
                this.m_super_dispatchKeyEvent = this.m_activity.getClass().getMethod("super_dispatchKeyEvent", KeyEvent.class);
                this.m_super_onRestoreInstanceState = this.m_activity.getClass().getMethod("super_onRestoreInstanceState", Bundle.class);
                this.m_super_onRetainNonConfigurationInstance = this.m_activity.getClass().getMethod("super_onRetainNonConfigurationInstance");
                this.m_super_onSaveInstanceState = this.m_activity.getClass().getMethod("super_onSaveInstanceState", Bundle.class);
                this.m_super_onKeyDown = this.m_activity.getClass().getMethod("super_onKeyDown", Integer.TYPE, KeyEvent.class);
                this.m_super_onKeyUp = this.m_activity.getClass().getMethod("super_onKeyUp", Integer.TYPE, KeyEvent.class);
                this.m_super_onConfigurationChanged = this.m_activity.getClass().getMethod("super_onConfigurationChanged", Configuration.class);
                this.m_super_onActivityResult = this.m_activity.getClass().getMethod("super_onActivityResult", Integer.TYPE, Integer.TYPE, Intent.class);
                this.m_super_onWindowFocusChanged = this.m_activity.getClass().getMethod("super_onWindowFocusChanged", Boolean.TYPE);
                this.m_super_dispatchGenericMotionEvent = this.m_activity.getClass().getMethod("super_dispatchGenericMotionEvent", MotionEvent.class);
            } catch (Exception var13) {
                var13.printStackTrace();
                return false;
            }

            var6 = 1;
            if (var3.containsKey("necessitas.api.level")) {
                var6 = var3.getInt("necessitas.api.level");
            }

            m_environmentVariables = var3.getString("environment.variables");
            var7 = "QT_ANDROID_FONTS_MONOSPACE=Droid Sans Mono;Droid Sans;Droid Sans Fallback\tQT_ANDROID_FONTS_SERIF=Droid Serif\tNECESSITAS_API_LEVEL=" + var6 + "\tHOME=" + this.m_activity.getFilesDir().getAbsolutePath() + "\tTMPDIR=" + this.m_activity.getFilesDir().getAbsolutePath();
            var7 = var7 + "\tQT_ANDROID_FONTS=Roboto;Droid Sans;Droid Sans Fallback";
            var7 = var7 + this.getAppIconSize(var1);
            if (m_environmentVariables != null && m_environmentVariables.length() > 0) {
                m_environmentVariables = var7 + "\t" + m_environmentVariables;
            } else {
                m_environmentVariables = var7;
            }

            if (var3.containsKey("application.parameters")) {
                m_applicationParameters = var3.getString("application.parameters");
            } else {
                m_applicationParameters = "";
            }

            try {
                this.m_softInputMode = this.m_activity.getPackageManager().getActivityInfo(this.m_activity.getComponentName(), 0).softInputMode;
            } catch (Exception var12) {
                var12.printStackTrace();
            }

            DisplayManager.DisplayListener var20 = new DisplayManager.DisplayListener() {
                public void onDisplayAdded(int var1) {
                }

                public void onDisplayChanged(int var1) {
                    EmbeddedQtDelegate.this.m_currentRotation = EmbeddedQtDelegate.this.m_activity.getWindowManager().getDefaultDisplay().getRotation();
                    QtNative.handleOrientationChanged(EmbeddedQtDelegate.this.m_currentRotation, EmbeddedQtDelegate.this.m_nativeOrientation);
                }

                public void onDisplayRemoved(int var1) {
                }
            };

            try {
                DisplayManager var21 = (DisplayManager)this.m_activity.getSystemService(Context.DISPLAY_SERVICE);
                var21.registerDisplayListener(var20, (Handler)null);
            } catch (Exception var11) {
                var11.printStackTrace();
            }

            this.m_mainLib = QtNative.loadMainLibrary(this.m_mainLib, var18);
            return this.m_mainLib != null;
        } else {
            return false;
        }
    }

    public boolean startApplication(ViewGroup fragmentView) {
        try {
            Bundle var1 = this.m_activity.getIntent().getExtras();
            if (var1 != null) {
                try {
                    boolean var2 = (this.m_activity.getApplicationInfo().flags & 2) != 0;
                    if (!var2) {
                        throw new Exception();
                    }

                    if (var1.containsKey("extraenvvars")) {
                        try {
                            m_environmentVariables = m_environmentVariables + "\t" + new String(Base64.decode(var1.getString("extraenvvars"), 0), "UTF-8");
                        } catch (Exception var5) {
                            var5.printStackTrace();
                        }
                    }

                    if (var1.containsKey("extraappparams")) {
                        try {
                            m_applicationParameters = m_applicationParameters + "\t" + new String(Base64.decode(var1.getString("extraappparams"), 0), "UTF-8");
                        } catch (Exception var4) {
                            var4.printStackTrace();
                        }
                    }
                } catch (Exception var6) {
                    Log.e("Qt JAVA", "Not in debug mode! It is not allowed to use extra arguments in non-debug mode.");
                }
            }

            if (null == this.m_surfaces) {
                this.onCreate((Bundle)null, fragmentView);
            }

            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    public void onTerminate() {
        QtNative.terminateQt();
        QtNative.m_qtThread.exit();
    }

    public void onCreate(Bundle var1, ViewGroup fragmentView) {
        this.m_quitApp = true;
        Runnable var2 = null;
        if (null == var1) {
            var2 = new Runnable() {
                public void run() {
                    try {
                        QtNative.startApplication(EmbeddedQtDelegate.m_applicationParameters, EmbeddedQtDelegate.m_environmentVariables, EmbeddedQtDelegate.this.m_mainLib);
                        EmbeddedQtDelegate.this.m_started = true;
                    } catch (Exception var2) {
                        var2.printStackTrace();
                        EmbeddedQtDelegate.this.m_activity.finish();
                    }

                }
            };
        }

        this.m_layout = new QtLayout(this.m_activity, var2);
        int var3 = this.m_activity.getResources().getConfiguration().orientation;

        try {
            ActivityInfo var4 = this.m_activity.getPackageManager().getActivityInfo(this.m_activity.getComponentName(), 128);
            String var5 = "android.app.splash_screen_drawable_" + (var3 == 2 ? "landscape" : "portrait");
            if (!var4.metaData.containsKey(var5)) {
                var5 = "android.app.splash_screen_drawable";
            }

            if (var4.metaData.containsKey(var5)) {
                this.m_splashScreenSticky = var4.metaData.containsKey("android.app.splash_screen_sticky") && var4.metaData.getBoolean("android.app.splash_screen_sticky");
                int var6 = var4.metaData.getInt(var5);
                this.m_splashScreen = new ImageView(this.m_activity);
                this.m_splashScreen.setImageDrawable(this.m_activity.getResources().getDrawable(var6));
                this.m_splashScreen.setScaleType(ScaleType.FIT_XY);
                this.m_splashScreen.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                this.m_layout.addView(this.m_splashScreen);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.m_editText = new QtEditText(this.m_activity, this);
        this.m_imm = (InputMethodManager)this.m_activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.m_surfaces = new HashMap();
        this.m_nativeViews = new HashMap();
        this.m_activity.registerForContextMenu(this.m_layout);
//        this.m_activity.setContentView(this.m_layout, new ViewGroup.LayoutParams(-1, -1));
        fragmentView.addView(m_layout);
        int var8 = this.m_activity.getWindowManager().getDefaultDisplay().getRotation();
        boolean var9 = var8 == 1 || var8 == 3;
        boolean var10 = var3 == 2;
        if ((!var10 || var9) && (var10 || !var9)) {
            this.m_nativeOrientation = 1;
        } else {
            this.m_nativeOrientation = 2;
        }

        QtNative.handleOrientationChanged(var8, this.m_nativeOrientation);
        this.m_currentRotation = var8;
        this.m_layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!EmbeddedQtDelegate.this.m_keyboardIsVisible) {
                    return true;
                } else {
                    Rect var1 = new Rect();
                    EmbeddedQtDelegate.this.m_activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(var1);
                    DisplayMetrics var2 = new DisplayMetrics();
                    EmbeddedQtDelegate.this.m_activity.getWindowManager().getDefaultDisplay().getMetrics(var2);
                    int var3 = var2.heightPixels - var1.bottom;
                    int[] var4 = new int[2];
                    EmbeddedQtDelegate.this.m_layout.getLocationOnScreen(var4);
                    QtNative.keyboardGeometryChanged(var4[0], var1.bottom - var4[1], var1.width(), var3);
                    return true;
                }
            }
        });
        this.m_editPopupMenu = new EditPopupMenu(this.m_activity, this.m_layout);
    }

    public void onCreate(Bundle var1) {
        this.m_quitApp = true;
        Runnable var2 = null;
        if (null == var1) {
            var2 = new Runnable() {
                public void run() {
                    try {
                        QtNative.startApplication(EmbeddedQtDelegate.m_applicationParameters, EmbeddedQtDelegate.m_environmentVariables, EmbeddedQtDelegate.this.m_mainLib);
                        EmbeddedQtDelegate.this.m_started = true;
                    } catch (Exception var2) {
                        var2.printStackTrace();
                        EmbeddedQtDelegate.this.m_activity.finish();
                    }

                }
            };
        }

        this.m_layout = new QtLayout(this.m_activity, var2);
        int var3 = this.m_activity.getResources().getConfiguration().orientation;

        try {
            ActivityInfo var4 = this.m_activity.getPackageManager().getActivityInfo(this.m_activity.getComponentName(), 128);
            String var5 = "android.app.splash_screen_drawable_" + (var3 == 2 ? "landscape" : "portrait");
            if (!var4.metaData.containsKey(var5)) {
                var5 = "android.app.splash_screen_drawable";
            }

            if (var4.metaData.containsKey(var5)) {
                this.m_splashScreenSticky = var4.metaData.containsKey("android.app.splash_screen_sticky") && var4.metaData.getBoolean("android.app.splash_screen_sticky");
                int var6 = var4.metaData.getInt(var5);
                this.m_splashScreen = new ImageView(this.m_activity);
                this.m_splashScreen.setImageDrawable(this.m_activity.getResources().getDrawable(var6));
                this.m_splashScreen.setScaleType(ScaleType.FIT_XY);
                this.m_splashScreen.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                this.m_layout.addView(this.m_splashScreen);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        this.m_editText = new QtEditText(this.m_activity, this);
        this.m_imm = (InputMethodManager)this.m_activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        this.m_surfaces = new HashMap();
        this.m_nativeViews = new HashMap();
        this.m_activity.registerForContextMenu(this.m_layout);
        this.m_activity.setContentView(this.m_layout, new ViewGroup.LayoutParams(-1, -1));
        int var8 = this.m_activity.getWindowManager().getDefaultDisplay().getRotation();
        boolean var9 = var8 == 1 || var8 == 3;
        boolean var10 = var3 == 2;
        if ((!var10 || var9) && (var10 || !var9)) {
            this.m_nativeOrientation = 1;
        } else {
            this.m_nativeOrientation = 2;
        }

        QtNative.handleOrientationChanged(var8, this.m_nativeOrientation);
        this.m_currentRotation = var8;
        this.m_layout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (!EmbeddedQtDelegate.this.m_keyboardIsVisible) {
                    return true;
                } else {
                    Rect var1 = new Rect();
                    EmbeddedQtDelegate.this.m_activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(var1);
                    DisplayMetrics var2 = new DisplayMetrics();
                    EmbeddedQtDelegate.this.m_activity.getWindowManager().getDefaultDisplay().getMetrics(var2);
                    int var3 = var2.heightPixels - var1.bottom;
                    int[] var4 = new int[2];
                    EmbeddedQtDelegate.this.m_layout.getLocationOnScreen(var4);
                    QtNative.keyboardGeometryChanged(var4[0], var1.bottom - var4[1], var1.width(), var3);
                    return true;
                }
            }
        });
        this.m_editPopupMenu = new EditPopupMenu(this.m_activity, this.m_layout);
    }

    public void hideSplashScreen() {
        this.hideSplashScreen(0);
    }

    public void hideSplashScreen(int var1) {
        if (this.m_splashScreen != null) {
            if (var1 <= 0) {
                this.m_layout.removeView(this.m_splashScreen);
                this.m_splashScreen = null;
            } else {
                AlphaAnimation var2 = new AlphaAnimation(1.0F, 0.0F);
                var2.setInterpolator(new AccelerateInterpolator());
                var2.setDuration((long)var1);
                var2.setAnimationListener(new Animation.AnimationListener() {
                    public void onAnimationEnd(Animation var1) {
                        EmbeddedQtDelegate.this.hideSplashScreen(0);
                    }

                    public void onAnimationRepeat(Animation var1) {
                    }

                    public void onAnimationStart(Animation var1) {
                    }
                });
                this.m_splashScreen.startAnimation(var2);
            }
        }
    }

    public void initializeAccessibility() {
        new QtAccessibilityDelegate(this.m_activity, this.m_layout, this);
    }

    public void onWindowFocusChanged(boolean var1) {
        try {
            this.m_super_onWindowFocusChanged.invoke(this.m_activity, var1);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        if (var1) {
            this.updateFullScreen();
        }

    }

    public void onConfigurationChanged(Configuration var1) {
        try {
            this.m_super_onConfigurationChanged.invoke(this.m_activity, var1);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void onDestroy() {
        if (this.m_quitApp) {
            QtNative.terminateQt();
            QtNative.setActivity((Activity)null, (EmbeddedQtDelegate)null);
            QtNative.m_qtThread.exit();
            System.exit(0);
        }

    }

    public void onPause() {
        if (VERSION.SDK_INT < 24 || !this.m_activity.isInMultiWindowMode()) {
            QtNative.setApplicationState(2);
        }

    }

    public void onResume() {
        QtNative.setApplicationState(4);
        if (this.m_started) {
            QtNative.updateWindow();
            this.updateFullScreen();
        }

    }

    public void onNewIntent(Intent var1) {
        QtNative.onNewIntent(var1);
    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        try {
            this.m_super_onActivityResult.invoke(this.m_activity, var1, var2, var3);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        QtNative.onActivityResult(var1, var2, var3);
    }

    public void onStop() {
        QtNative.setApplicationState(0);
    }

    public Object onRetainNonConfigurationInstance() {
        try {
            this.m_super_onRetainNonConfigurationInstance.invoke(this.m_activity);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        this.m_quitApp = false;
        return true;
    }

    public void onSaveInstanceState(Bundle var1) {
        try {
            this.m_super_onSaveInstanceState.invoke(this.m_activity, var1);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        var1.putBoolean("FullScreen", this.m_fullScreen);
        var1.putBoolean("Started", this.m_started);
    }

    public void onRestoreInstanceState(Bundle var1) {
        try {
            this.m_super_onRestoreInstanceState.invoke(this.m_activity, var1);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        this.m_started = var1.getBoolean("Started");
    }

    public boolean onKeyDown(int var1, KeyEvent var2) {
        if (!this.m_started) {
            return false;
        } else {
            this.m_metaState = MetaKeyKeyListener.handleKeyDown(this.m_metaState, var1, var2);
            int var3 = var2.getUnicodeChar(MetaKeyKeyListener.getMetaState(this.m_metaState) | var2.getMetaState());
            this.m_metaState = MetaKeyKeyListener.adjustMetaAfterKeypress(this.m_metaState);
            if ((var3 & Integer.MIN_VALUE) != 0) {
                var3 &= Integer.MAX_VALUE;
                int var5 = KeyEvent.getDeadChar(this.m_lastChar, var3);
                var3 = var5;
            }

            if ((var1 == 24 || var1 == 25 || var1 == 91) && System.getenv("QT_ANDROID_VOLUME_KEYS") == null) {
                return false;
            } else {
                this.m_lastChar = var3;
                if (var1 == 4) {
                    this.m_backKeyPressedSent = !this.m_keyboardIsVisible;
                    if (!this.m_backKeyPressedSent) {
                        return true;
                    }
                }

                QtNative.keyDown(var1, var3, var2.getMetaState(), var2.getRepeatCount() > 0);
                return true;
            }
        }
    }

    public boolean onKeyUp(int var1, KeyEvent var2) {
        if (!this.m_started) {
            return false;
        } else if ((var1 == 24 || var1 == 25 || var1 == 91) && System.getenv("QT_ANDROID_VOLUME_KEYS") == null) {
            return false;
        } else if (var1 == 4 && !this.m_backKeyPressedSent) {
            this.hideSoftwareKeyboard();
            this.setKeyboardVisibility(false, System.nanoTime());
            return true;
        } else {
            this.m_metaState = MetaKeyKeyListener.handleKeyUp(this.m_metaState, var1, var2);
            QtNative.keyUp(var1, var2.getUnicodeChar(), var2.getMetaState(), var2.getRepeatCount() > 0);
            return true;
        }
    }

    public boolean dispatchKeyEvent(KeyEvent var1) {
        if (this.m_started && var1.getAction() == 2 && var1.getCharacters() != null && var1.getCharacters().length() == 1 && var1.getKeyCode() == 0) {
            QtNative.keyDown(0, var1.getCharacters().charAt(0), var1.getMetaState(), var1.getRepeatCount() > 0);
            QtNative.keyUp(0, var1.getCharacters().charAt(0), var1.getMetaState(), var1.getRepeatCount() > 0);
        }

        if (QtNative.dispatchKeyEvent(var1)) {
            return true;
        } else {
            try {
                return (Boolean)this.m_super_dispatchKeyEvent.invoke(this.m_activity, var1);
            } catch (Exception var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu var1) {
        var1.clear();
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu var1) {
        this.m_optionsMenuIsVisible = true;
        boolean var2 = QtNative.onPrepareOptionsMenu(var1);
        this.setActionBarVisibility(var2 && var1.size() > 0);
        return var2;
    }

    public boolean onOptionsItemSelected(MenuItem var1) {
        return QtNative.onOptionsItemSelected(var1.getItemId(), var1.isChecked());
    }

    public void onOptionsMenuClosed(Menu var1) {
        this.m_optionsMenuIsVisible = false;
        QtNative.onOptionsMenuClosed(var1);
    }

    public void resetOptionsMenu() {
        this.m_activity.invalidateOptionsMenu();
    }

    public void onCreateContextMenu(ContextMenu var1, View var2, ContextMenu.ContextMenuInfo var3) {
        var1.clearHeader();
        QtNative.onCreateContextMenu(var1);
        this.m_contextMenuVisible = true;
    }

    public void onCreatePopupMenu(Menu var1) {
        QtNative.fillContextMenu(var1);
        this.m_contextMenuVisible = true;
    }

    public void onContextMenuClosed(Menu var1) {
        if (this.m_contextMenuVisible) {
            this.m_contextMenuVisible = false;
            QtNative.onContextMenuClosed(var1);
        }
    }

    public boolean onContextItemSelected(MenuItem var1) {
        this.m_contextMenuVisible = false;
        return QtNative.onContextItemSelected(var1.getItemId(), var1.isChecked());
    }

    public void openContextMenu(final int var1, final int var2, final int var3, final int var4) {
        this.m_layout.postDelayed(new Runnable() {
            public void run() {
                EmbeddedQtDelegate.this.m_layout.setLayoutParams(EmbeddedQtDelegate.this.m_editText, new QtLayout.LayoutParams(var3, var4, var1, var2), false);
                PopupMenu var1x = new PopupMenu(EmbeddedQtDelegate.this.m_activity, EmbeddedQtDelegate.this.m_editText);
                EmbeddedQtDelegate.this.onCreatePopupMenu(var1x.getMenu());
                var1x.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem var1x) {
                        return EmbeddedQtDelegate.this.onContextItemSelected(var1x);
                    }
                });
                var1x.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    public void onDismiss(PopupMenu var1x) {
                        EmbeddedQtDelegate.this.onContextMenuClosed(var1x.getMenu());
                    }
                });
                var1x.show();
            }
        }, 100L);
    }

    public void closeContextMenu() {
        this.m_activity.closeContextMenu();
    }

    private void setActionBarVisibility(boolean var1) {
        if (this.m_activity.getActionBar() != null) {
            if (!ViewConfiguration.get(this.m_activity).hasPermanentMenuKey() && var1) {
                this.m_activity.getActionBar().show();
            } else {
                this.m_activity.getActionBar().hide();
            }

        }
    }

    public void insertNativeView(int var1, View var2, int var3, int var4, int var5, int var6) {
        if (this.m_dummyView != null) {
            this.m_layout.removeView(this.m_dummyView);
            this.m_dummyView = null;
        }

        if (this.m_nativeViews.containsKey(var1)) {
            this.m_layout.removeView((View)this.m_nativeViews.remove(var1));
        }

        if (var5 >= 0 && var6 >= 0) {
            var2.setLayoutParams(new QtLayout.LayoutParams(var5, var6, var3, var4));
        } else {
            var2.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }

        var2.setId(var1);
        this.m_layout.addView(var2);
        this.m_nativeViews.put(var1, var2);
    }

    public void createSurface(int var1, boolean var2, int var3, int var4, int var5, int var6, int var7) {
        if (this.m_surfaces.size() == 0) {
            TypedValue var8 = new TypedValue();
            this.m_activity.getTheme().resolveAttribute(16842836, var8, true);
            if (var8.type >= 28 && var8.type <= 31) {
                this.m_activity.getWindow().setBackgroundDrawable(new ColorDrawable(var8.data));
            } else {
                this.m_activity.getWindow().setBackgroundDrawable(this.m_activity.getResources().getDrawable(var8.resourceId));
            }

            if (this.m_dummyView != null) {
                this.m_layout.removeView(this.m_dummyView);
                this.m_dummyView = null;
            }
        }

        if (this.m_surfaces.containsKey(var1)) {
            this.m_layout.removeView((View)this.m_surfaces.remove(var1));
        }

        QtSurface var10 = new QtSurface(this.m_activity, var1, var2, var7);
        if (var5 >= 0 && var6 >= 0) {
            var10.setLayoutParams(new QtLayout.LayoutParams(var5, var6, var3, var4));
        } else {
            var10.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        }

        int var9 = this.getSurfaceCount();
        this.m_layout.addView(var10, var9);
        this.m_surfaces.put(var1, var10);
        if (!this.m_splashScreenSticky) {
            this.hideSplashScreen();
        }

    }

    public void setSurfaceGeometry(int var1, int var2, int var3, int var4, int var5) {
        if (this.m_surfaces.containsKey(var1)) {
            QtSurface var6 = (QtSurface)this.m_surfaces.get(var1);
            var6.setLayoutParams(new QtLayout.LayoutParams(var4, var5, var2, var3));
        } else {
            if (!this.m_nativeViews.containsKey(var1)) {
                Log.e("Qt JAVA", "Surface " + var1 + " not found!");
                return;
            }

            View var7 = (View)this.m_nativeViews.get(var1);
            var7.setLayoutParams(new QtLayout.LayoutParams(var4, var5, var2, var3));
        }

    }

    public void destroySurface(int var1) {
        View var2 = null;
        if (this.m_surfaces.containsKey(var1)) {
            var2 = (View)this.m_surfaces.remove(var1);
        } else if (this.m_nativeViews.containsKey(var1)) {
            var2 = (View)this.m_nativeViews.remove(var1);
        } else {
            Log.e("Qt JAVA", "Surface " + var1 + " not found!");
        }

        if (var2 != null) {
            if (this.m_surfaces.size() == 0 && this.m_nativeViews.size() == 0) {
                this.m_dummyView = var2;
            } else {
                this.m_layout.removeView(var2);
            }

        }
    }

    public int getSurfaceCount() {
        return this.m_surfaces.size();
    }

    public void bringChildToFront(int var1) {
        View var2 = (View)this.m_surfaces.get(var1);
        if (var2 != null) {
            int var3 = this.getSurfaceCount();
            if (var3 > 0) {
                this.m_layout.moveChild(var2, var3 - 1);
            }

        } else {
            var2 = (View)this.m_nativeViews.get(var1);
            if (var2 != null) {
                this.m_layout.moveChild(var2, -1);
            }

        }
    }

    public void bringChildToBack(int var1) {
        View var2 = (View)this.m_surfaces.get(var1);
        if (var2 != null) {
            this.m_layout.moveChild(var2, 0);
        } else {
            var2 = (View)this.m_nativeViews.get(var1);
            if (var2 != null) {
                int var3 = this.getSurfaceCount();
                this.m_layout.moveChild(var2, var3);
            }

        }
    }

    public boolean dispatchGenericMotionEvent(MotionEvent var1) {
        if (this.m_started && QtNative.dispatchGenericMotionEvent(var1)) {
            return true;
        } else {
            try {
                return (Boolean)this.m_super_dispatchGenericMotionEvent.invoke(this.m_activity, var1);
            } catch (Exception var3) {
                var3.printStackTrace();
                return false;
            }
        }
    }

    public void onRequestPermissionsResult(int var1, String[] var2, int[] var3) {
        QtNative.sendRequestPermissionsResult(var1, var2, var3);
    }
}
