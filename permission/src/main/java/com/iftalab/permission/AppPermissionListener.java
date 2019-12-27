package com.iftalab.permission;

interface AppPermissionListener {
    void onPermissionGranted();

    void onPermissionRejected();
}
