package com.iftalab.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

public class SignaturePermissions {
    public SignaturePermissions() {
    }

    public static class SystemAlertWindow {
        static final int PERMISSION_REQUEST_CODE = 150;

        public SystemAlertWindow() {
        }

        public static boolean canDrawOverlays(Context context) {
            if (Build.VERSION.SDK_INT < 23) {
                return true;
            } else if (Build.VERSION.SDK_INT <= 27 && Build.VERSION.SDK_INT >= 26) {
                if (Settings.canDrawOverlays(context)) {
                    return true;
                } else {
                    try {
                        WindowManager mgr = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                        if (mgr == null) {
                            return false;
                        } else {
                            View viewToAdd = new View(context);
                            WindowManager.LayoutParams params = new WindowManager.LayoutParams(0, 0, 2038, 24, -2);
                            viewToAdd.setLayoutParams(params);
                            mgr.addView(viewToAdd, params);
                            mgr.removeView(viewToAdd);
                            return true;
                        }
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        return false;
                    }
                }
            } else {
                return Settings.canDrawOverlays(context);
            }
        }

        public static void takePermission(Activity activity) {
            (new AlertDialog.Builder(activity)).setMessage(activity.getString(R.string.overlay_permission)).setPositiveButton(R.string.ok, (dialog, which) -> {
                openSettings(activity);
                dialog.dismiss();
            }).setNegativeButton(R.string.no, (dialog, which) -> {
                dialog.dismiss();
            }).create().show();
        }

        private static void openSettings(Activity activity) {
            if (Build.BRAND.toLowerCase().contains("meizu")) {
                openFlyMeSecurityApp(activity);
            } else {
                openAppSettings(activity);
            }

        }

        private static void openAppSettings(Activity activity) {
            if (Build.VERSION.SDK_INT >= 23) {
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                Uri uri = Uri.fromParts("package", activity.getPackageName(), (String) null);
                intent.setData(uri);
                activity.startActivityForResult(intent, 150);
            } else {
                Toast.makeText(activity, activity.getString(R.string.pleaseGrantOverlayPermissionFromSettingForThisApp), Toast.LENGTH_LONG).show();
            }

        }

        private static void openFlyMeSecurityApp(Activity context) {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory("android.intent.category.DEFAULT");
            intent.putExtra("packageName", "com.iftalab.runtimepermission");

            try {
                context.startActivityForResult(intent, 150);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }
}
