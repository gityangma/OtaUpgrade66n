package com.yang.otaupgrade66n;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.yang.otaupgrade.OTASDKUtils;
import com.yang.otaupgrade.firware.UpdateFirewareCallBack;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        otaUpgrade();
    }

    private void otaUpgrade() {

        OTASDKUtils otaSDKUtils = new OTASDKUtils(this.getApplicationContext(), new UpdateFirewareCallBack() {
            @Override
            public void onError(int code) {

                Log.e(TAG, "升级固件失败: " + code);
//                onDataCallback.onUpgradeError();
            }

            @Override
            public void onProcess(float process) {
                Log.i(TAG, "升级固件进度: " + process);
//                onDataCallback.onUpdateProcess(process);
            }

            @Override
            public void onUpdateComplete() {
                Log.i(TAG, "升级固件完成");
//                onDataCallback.onUpdateComplete();

            }
        });
        //设置OTA升级密钥Cmd0x74为true
        otaSDKUtils.setOtaKeyCmd0x74(true);
        //设置OTA升级密钥
//        otaSDKUtils.setOtaKey(otaKey);
//        //升级固件
//        otaSDKUtils.updateFirware(device.getAddress(), uri);
    }
}