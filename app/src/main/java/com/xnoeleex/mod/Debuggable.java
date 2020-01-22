package com.xnoeleex.mod;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.provider.Settings;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Debuggable implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpParam) throws Throwable {
        final String packageName = lpParam.packageName;

        XC_MethodHook myHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Activity app = (Activity) param.thisObject;
                Intent intent = app.getIntent();
                intent.putExtra("com.android.settings.extra.DEBUGGABLE", false);
                app.setIntent(intent);
            }
        };

        if (packageName.equals("com.android.settings")) {
            String className = "";

            try{
                XposedHelpers.findClass("com.android.settings.AppPicker", lpParam.classLoader);
                className = "com.android.settings.AppPicker";
                XposedBridge.log("xNoEleEx: Debuggable ---> " + "Had found class: com.android.settings.AppPicker");
            }
            catch(XposedHelpers.ClassNotFoundError e) {
                XposedBridge.log("xNoEleEx: Debuggable ---> " + "Not found class: com.android.settings.AppPicker");
            }

            try{
                XposedHelpers.findClass("com.android.settings.development.AppPicker", lpParam.classLoader);
                className = "com.android.settings.development.AppPicker";
                XposedBridge.log("xNoEleEx: Debuggable ---> " + "Had found class: com.android.settings.development.AppPicker");
            }
            catch(XposedHelpers.ClassNotFoundError e) {
                XposedBridge.log("xNoEleEx: Debuggable ---> " + "Not found class: com.android.settings.development.AppPicker");
            }

            if(!className.equals("")){
                XposedHelpers.findAndHookMethod(
                        className,
                        lpParam.classLoader,
                        "onCreate",
                        "android.os.Bundle",
                        myHook
                );
            }
        }

        else if (packageName.equals("android")) {
            XposedBridge.hookAllMethods(android.os.Process.class, "start", new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    String niceName = (String) param.args[1];
                    int flags = (int) param.args[5];

                    if (niceName.equals(Settings.Global.getString(new ContentResolver(null) {}, Settings.Global.DEBUG_APP))) {
                        param.args[5] = flags | 1;
                        XposedBridge.log("xNoEleEx: Debuggable ---> " + niceName + ": Set flag DEBUG_ENABLE_DEBUGGER");
                    }
                }
            });
        }
    }
}

