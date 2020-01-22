package com.xnoeleex.mod;
import android.webkit.WebView;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class WebViewDebuggingEnabled implements IXposedHookLoadPackage {
    public void handleLoadPackage(LoadPackageParam lpParam) throws Throwable {
        final String packageName = lpParam.packageName;

        XposedBridge.hookAllConstructors(WebView.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedHelpers.callStaticMethod(WebView.class, "setWebContentsDebuggingEnabled", true);
                XposedBridge.log("xNoEleEx: WebViewDebuggingEnabled ---> " + packageName + ": setWebContentsDebuggingEnabled(...args:true)");
            }
        });

        XposedBridge.hookAllMethods(WebView.class, "setWebContentsDebuggingEnabled", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = true;
                XposedBridge.log("xNoEleEx: WebViewDebuggingEnabled ---> " + packageName + ": setWebContentsDebuggingEnabled(args[0]=true)");
            }
        });
    }
}