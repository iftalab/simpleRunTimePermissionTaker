package com.iftalab.permission;

import android.app.Activity;

import androidx.appcompat.app.AlertDialog;

public abstract class AppPermission {
    AppPermission() {
    }

    public abstract boolean hasPermission();

    public void requestPermission(Activity activity, AppPermissionListener listener, String requestExplanation) {
        if (this.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            (new AlertDialog.Builder(activity)).setMessage(requestExplanation).setCancelable(false)
                    .setPositiveButton(
                            activity.getString(R.string.ok),
                            (dialog, which) -> this.requestPermission(activity, listener)
                    ).create().show();
        }
    }

    public abstract void requestPermission(Activity activity, AppPermissionListener listener);
}
