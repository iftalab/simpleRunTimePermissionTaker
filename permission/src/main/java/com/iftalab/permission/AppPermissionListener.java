package com.iftalab.permission;

public interface AppPermissionListener {
    void onPermissionGranted();

    void onPermissionRejected();
}
