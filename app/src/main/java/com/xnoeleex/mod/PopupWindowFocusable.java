package com.xnoeleex.mod;
import android.widget.PopupWindow;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class PopupWindowFocusable implements IXposedHookLoadPackage {
    public void handleLoadPackage(LoadPackageParam lpParam) throws Throwable {
        final String packageName = lpParam.packageName;

        XposedBridge.hookAllConstructors(PopupWindow.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callStaticMethod(PopupWindow.class, "setFocusable", true);
                XposedBridge.log("xNoEleEx:PopupWindowFocusable ---> " + packageName + ": setFocusable(...args:true)");
            }
        });

        XposedBridge.hookAllMethods(PopupWindow.class, "setFocusable", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = true;
                XposedBridge.log("xNoEleEx:PopupWindowFocusable ---> " + packageName + ": setFocusable(args[0]=true)");
            }
        });
    }
}