package com.example.app2;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import comman.ICommon;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "ClientTAG";
    ICommon iCommon;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iCommon = ICommon.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Bind(View view) {
        Intent intent = new Intent("com.example.admin.service.AIDL");
        bindService(createExplicitFromImplicitIntent(this,intent),serviceConnection,BIND_AUTO_CREATE);
    }

    public void Calc(View view) {
        try {

            Log.d(TAG, "Calc: "+ iCommon.calulate(10,80));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        Intent explicitIntent = new Intent(implicitIntent);
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
