package com.ev.dashboard.usbmanager;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ev.usbcomm.driver.UsbSerialDriver;
import com.ev.usbcomm.driver.UsbSerialPort;
import com.ev.usbcomm.driver.UsbSerialProber;
import com.ev.usbcomm.util.SerialInputOutputManager;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

public class USBManager extends Service implements SerialInputOutputManager.Listener{
    private String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private SerialInputOutputManager usbIoManager;
    private SendUSBData sendUSBData;

    @Override
    public void onCreate() {
        super.onCreate();
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);
        Log.i("Available Devices", Arrays.toString(new List[]{availableDrivers}));
        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            PendingIntent mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(
                    ACTION_USB_PERMISSION), 0);
            manager.requestPermission(driver.getDevice(), mPermissionIntent);
        }

        UsbSerialPort port = driver.getPorts().get(0); // Most devices have just one port (port 0)
        try {
            port.open(connection);
            port.setParameters(57600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            usbIoManager = new SerialInputOutputManager(port, this);
            Executors.newSingleThreadExecutor().submit(usbIoManager);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sendUSBData = (SendUSBData) this;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onNewData(byte[] data) {
        sendUSBData.onReceivedUSBData(data);
    }

    @Override
    public void onRunError(Exception e) {

    }
}
