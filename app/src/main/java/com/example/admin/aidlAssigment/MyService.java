package com.example.admin.aidlAssigment;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import comman.ICommon;

public class MyService extends Service {
    private ICommon iCommon;
   MyImpl myimpl = new MyImpl();

    @Override
    public IBinder onBind(Intent intent) {
      return myimpl;
    }

    public class MyImpl extends ICommon.Stub{

        @Override
        public int calulate(int num1, int num2) throws RemoteException {
            return num1+num2;
        }
    }

}
