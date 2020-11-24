package com.witted.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;

import com.blankj.utilcode.util.NetworkUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 监听反馈当前网络连接状态
 */
public class NetReceiver extends BroadcastReceiver {

    private volatile static NetReceiver receiver;
    private NetworkCallback networkCallback;
    private ConnectivityManager.NetworkCallback callback;
    private volatile boolean registered = false;

    public static NetReceiver getInstance() {
        if (receiver == null) {
            synchronized (NetReceiver.class) {
                if (receiver == null) {
                    receiver = new NetReceiver();
                }
            }
        }
        return receiver;
    }

    private NetReceiver() {

    }

    public void register(Activity activity, NetworkCallback networkCallback) {
        if (registered) {
            return;
        }
        this.networkCallback = networkCallback;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            callback = new ConnectivityManager.NetworkCallback() {

                @Override
                public void onAvailable(@NotNull Network network) {
                    super.onAvailable(network);
                    networkCallback.onNetChanged(true);
                }

                @Override
                public void onLost(@NotNull Network network) {
                    super.onLost(network);
                    networkCallback.onNetChanged(false);
                }
            };
            connManager.registerDefaultNetworkCallback(callback);
        } else {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            activity.registerReceiver(receiver, intentFilter);
        }
        registered = true;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if ((ConnectivityManager.CONNECTIVITY_ACTION).equals(intent.getAction())) {
            networkCallback.onNetChanged(NetworkUtils.isConnected());
        }
    }

    /**
     * 取消网络监听
     */
    public void unRegister(Activity activity) {
        registered = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ConnectivityManager connManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            connManager.unregisterNetworkCallback(callback);
        } else {
            activity.unregisterReceiver(receiver);
        }
    }

    public interface NetworkCallback {
        /**
         * 网络是否可用
         */
        void onNetChanged(boolean hasNet);

    }

}