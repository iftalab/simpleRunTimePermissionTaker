package com.iftalab.permission;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.lang.ref.WeakReference;
import java.util.List;

public class DangerousPermission {
    public DangerousPermission() {
    }

    private static void requestPermissionWithDexter(final Activity activity, final String permissionGroupName, final AppPermissionListener listener, String... permissionNames) {
        Dexter.withActivity(activity).withPermissions(permissionNames).withListener(new MultiplePermissionsListener() {
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied()) {
                    DangerousPermission.showSettingsDialog(activity, permissionGroupName);
                } else if (multiplePermissionsReport.areAllPermissionsGranted()) {
                    listener.onPermissionGranted();
                } else {
                    listener.onPermissionRejected();
                }

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    @SuppressLint({"StringFormatInvalid"})
    private static void showSettingsDialog(Context context, String permissionGroupName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.settingsExplanation, new Object[]{permissionGroupName, permissionGroupName}));
        builder.setCancelable(false);
        builder.setPositiveButton(context.getString(R.string.settings), (dialog, which) -> {
            dialog.cancel();
            openSettings(context);
        });
        builder.setNegativeButton(context.getString(R.string.cancel), (dialog, which) -> {
            dialog.cancel();
        });
        builder.show();
    }

    private static void openSettings(Context context) {
        if (getDeviceBrand().contains("meizu")) {
            openFlymeSecurityApp(context);
        } else {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            } catch (Exception var3) {
                var3.printStackTrace();
            }

        }
    }

    private static String getDeviceBrand() {
        return Build.BRAND.toLowerCase();
    }

    private static void openFlymeSecurityApp(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra("packageName", "com.iftalab.runtimepermission");

        try {
            context.startActivity(intent);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static class StoragePermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "STORAGE";
        private static WeakReference<DangerousPermission.StoragePermission> storagePermissionWeakReference = null;

        private StoragePermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.StoragePermission getAccess(Context context) {
            storagePermissionWeakReference = new WeakReference(new DangerousPermission.StoragePermission(context));
            return (DangerousPermission.StoragePermission) storagePermissionWeakReference.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE");
            }
        }
    }

    public static class SMSPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "SMS";
        private static WeakReference<DangerousPermission.SMSPermission> smsPermissionWeakReference = null;

        private SMSPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.SMSPermission getAccess(Context context) {
            smsPermissionWeakReference = new WeakReference(new DangerousPermission.SMSPermission(context));
            return (DangerousPermission.SMSPermission) smsPermissionWeakReference.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.READ_SMS") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.SEND_SMS") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.RECEIVE_SMS") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.READ_SMS", "android.permission.SEND_SMS", "android.permission.RECEIVE_SMS");
            }
        }
    }

    public static class PhonePermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "PHONE";
        private static WeakReference<DangerousPermission.PhonePermission> phonePermissionWeakReference = null;

        private PhonePermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.PhonePermission getAccess(Context context) {
            phonePermissionWeakReference = new WeakReference(new DangerousPermission.PhonePermission(context));
            return (DangerousPermission.PhonePermission) phonePermissionWeakReference.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.READ_PHONE_STATE") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.CALL_PHONE") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE");
            }
        }
    }

    public static class MicrophonePermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "MICROPHONE";
        private static WeakReference<DangerousPermission.MicrophonePermission> microphonePermission = null;

        private MicrophonePermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.MicrophonePermission getAccess(Context context) {
            microphonePermission = new WeakReference(new DangerousPermission.MicrophonePermission(context));
            return (DangerousPermission.MicrophonePermission) microphonePermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.RECORD_AUDIO") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.RECORD_AUDIO");
            }
        }
    }

    public static class LocationPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "LOCATION";
        private static WeakReference<DangerousPermission.LocationPermission> locationPermission = null;

        private LocationPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.LocationPermission getAccess(Context context) {
            locationPermission = new WeakReference(new DangerousPermission.LocationPermission(context));
            return (DangerousPermission.LocationPermission) locationPermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_FINE_LOCATION") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.ACCESS_COARSE_LOCATION") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION");
            }
        }
    }

    public static class CallLogPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "CallLog";
        private static WeakReference<DangerousPermission.CallLogPermission> callLogPermission = null;

        private CallLogPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.CallLogPermission getAccess(Context context) {
            callLogPermission = new WeakReference(new DangerousPermission.CallLogPermission(context));
            return (DangerousPermission.CallLogPermission) callLogPermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.READ_CALL_LOG") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.WRITE_CALL_LOG") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG");
            }
        }
    }

    public static class ContactsPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "CONTACTS";
        private static WeakReference<DangerousPermission.ContactsPermission> contactsPermission = null;

        private ContactsPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.ContactsPermission getAccess(Context context) {
            contactsPermission = new WeakReference(new DangerousPermission.ContactsPermission(context));
            return (DangerousPermission.ContactsPermission) contactsPermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.READ_CONTACTS") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.WRITE_CONTACTS") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS");
            }
        }
    }

    public static class CameraPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "CAMERA";
        private static WeakReference<DangerousPermission.CameraPermission> cameraPermission = null;

        private CameraPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.CameraPermission getAccess(Context context) {
            cameraPermission = new WeakReference(new DangerousPermission.CameraPermission(context));
            return (DangerousPermission.CameraPermission) cameraPermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.CAMERA") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission.requestPermissionWithDexter(activity, this.permissionGroupName, listener, "android.permission.CAMERA");
            }
        }
    }

    public static class CalenderPermission extends AppPermission {
        private Context context;
        private String permissionGroupName = "CALENDER";
        private static WeakReference<CalenderPermission> calenderPermission = null;

        private CalenderPermission(Context context) {
            this.context = context;
        }

        public static DangerousPermission.CalenderPermission getAccess(Context context) {
            calenderPermission = new WeakReference(new DangerousPermission.CalenderPermission(context));
            return (DangerousPermission.CalenderPermission) calenderPermission.get();
        }

        public boolean hasPermission() {
            return ContextCompat.checkSelfPermission(this.context, "android.permission.WRITE_CALENDAR") == 0 && ContextCompat.checkSelfPermission(this.context, "android.permission.READ_CALENDAR") == 0;
        }

        public void requestPermission(Activity activity, AppPermissionListener listener) {
            if (this.hasPermission()) {
                listener.onPermissionGranted();
            } else {
                DangerousPermission
                        .requestPermissionWithDexter(
                                activity,
                                this.permissionGroupName,
                                listener,
                                "android.permission.READ_CALENDAR",
                                "android.permission.WRITE_CALENDAR");
            }
        }
    }
}
