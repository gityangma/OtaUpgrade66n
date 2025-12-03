package com.yang.otaupgrade.firware;

/**
 * UpdateFirewareCallBack
 */

public interface UpdateFirewareCallBack {

    //发生错误
    public void onError(int code);
    //OTA进度%
    public void onProcess(float process);
    //OTA完成
    public void onUpdateComplete();

}
