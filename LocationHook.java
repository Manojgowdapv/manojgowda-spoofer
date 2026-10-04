package com.yourname.spoofer;

import android.location.Location;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.io.File;
import java.util.Scanner;

public class LocationHook implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        // Skip system/framework packages to avoid performance lag
        if (lpparam.packageName.equals("android") || lpparam.packageName.equals("com.android.systemui")) {
            return;
        }

        XposedBridge.log("ManojGowda: Hooking package -> " + lpparam.packageName);

        // Hook Location.getLatitude()
        XposedHelpers.findAndHookMethod("android.location.Location", lpparam.classLoader, "getLatitude", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                String coords = readSavedCoords();
                if (coords != null) {
                    param.setResult(Double.parseDouble(coords.split(",")[0]));
                }
            }
        });

        // Hook Location.getLongitude()
        XposedHelpers.findAndHookMethod("android.location.Location", lpparam.classLoader, "getLongitude", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                String coords = readSavedCoords();
                if (coords != null) {
                    param.setResult(Double.parseDouble(coords.split(",")[1]));
                }
            }
        });

        // Hook LocationManager.getLastKnownLocation to intercept direct provider requests
        try {
            XposedHelpers.findAndHookMethod("android.location.LocationManager", lpparam.classLoader, "getLastKnownLocation", String.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    String coords = readSavedCoords();
                    if (coords != null && param.getResult() != null) {
                        Location loc = (Location) param.getResult();
                        loc.setLatitude(Double.parseDouble(coords.split(",")[0]));
                        loc.setLongitude(Double.parseDouble(coords.split(",")[1]));
                        param.setResult(loc);
                    }
                }
            });
        } catch (Exception ignore) {}
    }

    private String readSavedCoords() {
        try {
            File file = new File("/data/local/tmp/manoj_coords.txt");
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                if (scanner.hasNextLine()) {
                    return scanner.nextLine();
                }
            }
        } catch (Exception ignore) {}
        return null;
    }
}
