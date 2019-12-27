package com.iftalab.permission;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class DeclaredPermissionChecker {
    //for example, permission can be "WRITE_EXTERNAL_STORAGE"
    public static boolean hasPermissionDeclared(Context context, String permission) {
        if (!permission.startsWith("android.permission.")) {
            permission = "android.permission." + permission;
        }
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_PERMISSIONS);
            if (info.requestedPermissions != null) {
                for (String p : info.requestedPermissions) {
                    if (p.equals(permission)) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
