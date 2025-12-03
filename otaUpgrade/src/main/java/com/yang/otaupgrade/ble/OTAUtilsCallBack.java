package com.yang.otaupgrade.ble;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;

/**
 * OTAUtilsCallBack
 */

public interface OTAUtilsCallBack {

    public void onDeviceSearch(BluetoothDevice device, int rssi, byte[] scanRecord);
    public void onConnectChange(boolean isConnected);
    public void onProcess(float process);
    public void onError(int code);
    public void onOTAFinish();
    public void onResourceFinish();
    public void onReBootSuccess();

    public void onPhyUpdate(BluetoothGatt gatt, int txPhy, int rxPhy, int status);

    public void onStartSecurityData();
    //public void onMtuChanged(BluetoothGatt gatt, int mtu, int status);
}
