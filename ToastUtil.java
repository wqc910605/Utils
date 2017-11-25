package com.wwf.opensourcechina.utils;

import android.widget.Toast;

import com.wwf.opensourcechina.application.OSCApplication;

/**
 * Created by wwf on 2017/10/12.
 */

public class ToastUtil {
    private static Toast sToast = null;

    public static void show(final String msg) {

        ThreadUtil.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (sToast == null) {
                    sToast = sToast.makeText(OSCApplication.getContext(), msg, Toast.LENGTH_SHORT);
                }else{
					 sToast.setText(msg);
				}
                sToast.show();
            }
        });

    }
}
