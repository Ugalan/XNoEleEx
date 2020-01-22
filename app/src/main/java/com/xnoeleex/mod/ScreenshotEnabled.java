package com.xnoeleex.mod;
import android.os.Build;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.List;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class ScreenshotEnabled implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpParam) throws Throwable {
        final String packageName = lpParam.packageName;

        XposedHelpers.findAndHookMethod("android.view.Window", lpParam.classLoader, "setFlags", int.class, int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Integer flags = (Integer) param.args[0];
                flags &= ~WindowManager.LayoutParams.FLAG_SECURE;
                param.args[0] = flags;
                XposedBridge.log(String.format("xNoEleEx:ScreenshotEnabled ---> %s: android.view.Window: setFlags(%s)", packageName, flags));
            }
        });

        if (Build.VERSION.SDK_INT >= 17) {
            XposedHelpers.findAndHookMethod("android.view.SurfaceView", lpParam.classLoader, "setSecure", boolean.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = false;
                    XposedBridge.log(String.format("xNoEleEx:ScreenshotEnabled ---> %s: android.view.SurfaceView: setSecure(false)", packageName));
                }
            });
        }
    }
}
