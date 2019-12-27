package com.iftalab.permission;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import com.iftalab.permission.DangerousPermission.CalenderPermission;
import com.iftalab.permission.DangerousPermission.CallLogPermission;
import com.iftalab.permission.DangerousPermission.CameraPermission;
import com.iftalab.permission.DangerousPermission.ContactsPermission;
import com.iftalab.permission.DangerousPermission.LocationPermission;
import com.iftalab.permission.DangerousPermission.MicrophonePermission;
import com.iftalab.permission.DangerousPermission.PhonePermission;
import com.iftalab.permission.DangerousPermission.SMSPermission;
import com.iftalab.permission.DangerousPermission.StoragePermission;
import com.iftalab.permission.R.layout;
import androidx.appcompat.app.AppCompatActivity;

public class PermissionDialogActivity extends AppCompatActivity {
    private static final String KEY_PERMISSION_TYPE = "KEY_PERMISSION_TYPE";
    private static final String KEY_PERMISSION_LISTENER = "KEY_PERMISSION_LISTENER";
    private PermissionDialogActivity.PermissionType permissionType;
    private static AppPermissionListener appPermissionListener;
    private AppPermissionListener localAppPermissionListener;

    public PermissionDialogActivity() {
        this.permissionType = PermissionDialogActivity.PermissionType.Storage;
        this.localAppPermissionListener = new AppPermissionListener() {
            public void onPermissionGranted() {
                PermissionDialogActivity.appPermissionListener.onPermissionGranted();
                PermissionDialogActivity.this.finish();
            }

            public void onPermissionRejected() {
                PermissionDialogActivity.appPermissionListener.onPermissionRejected();
                PermissionDialogActivity.this.finish();
            }
        };
    }

    public static void takePermission(Context context, PermissionDialogActivity.PermissionType permissionType) {
        Intent intent = new Intent(context, PermissionDialogActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("KEY_PERMISSION_TYPE", permissionType);
        context.startActivity(intent);
        attachListener(new AppPermissionListener() {
            public void onPermissionGranted() {
            }

            public void onPermissionRejected() {
            }
        });
    }

    public static void takePermission(Context context, PermissionDialogActivity.PermissionType permissionType, AppPermissionListener listener) {
        Intent intent = new Intent(context, PermissionDialogActivity.class);
        intent.putExtra("KEY_PERMISSION_TYPE", permissionType);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        attachListener(listener);
    }

    private static void attachListener(AppPermissionListener listener) {
        appPermissionListener = listener;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.setContentView(R.layout.activity_permission_dialog_layout);
        this.permissionType = (PermissionDialogActivity.PermissionType)this.getIntent().getSerializableExtra("KEY_PERMISSION_TYPE");
        if (this.getIntent().hasExtra("KEY_PERMISSION_LISTENER")) {
            appPermissionListener = (AppPermissionListener)this.getIntent().getSerializableExtra("KEY_PERMISSION_LISTENER");
        }

        this.takePermission();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            this.permissionType = (PermissionDialogActivity.PermissionType)this.getIntent().getSerializableExtra("KEY_PERMISSION_TYPE");
            if (this.getIntent().hasExtra("KEY_PERMISSION_LISTENER")) {
                appPermissionListener = (AppPermissionListener)this.getIntent().getSerializableExtra("KEY_PERMISSION_LISTENER");
            }

            this.takePermission();
        }
    }

    private void takePermission() {
        switch(this.permissionType) {
            case Calender:
                CalenderPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Camera:
                CameraPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Contacts:
                ContactsPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case CallLog:
                CallLogPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Location:
                LocationPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Microphone:
                MicrophonePermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Phone:
                PhonePermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case SMS:
                SMSPermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            case Storage:
                StoragePermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
                break;
            default:
                StoragePermission.getAccess(this).requestPermission(this, this.localAppPermissionListener);
        }

    }

    public static enum PermissionType {
        Calender,
        Camera,
        Contacts,
        CallLog,
        Location,
        Microphone,
        Phone,
        SMS,
        Storage;

        private PermissionType() {
        }
    }
}
