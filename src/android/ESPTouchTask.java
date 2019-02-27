/*
 * Copyright (c) 2015 - 广东小哈科技股份有限公司
 * All rights reserved.
 *
 * Created on 2019-02-27
 */
package com.huchiwei.cordova.esptouch.esptouch;

import android.content.Context;
import android.os.AsyncTask;
import java.util.List;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;

import com.huchiwei.cordova.esptouch.ESPTouchTaskResult;
import com.huchiwei.cordova.esptouch.ESPTouchTaskCallback;

/**
 * ESPTouch异步配网任务
 *
 * @author huchiwei
 * @version 1.0.0
 */
public class ESPTouchTask extends AsyncTask<byte[], Void, List<IEsptouchResult>> {

    // ============================================================
    // fields =====================================================

    private Context context;
    private IEsptouchTask mEsptouchTask;
    private ESPTouchTaskCallback mCallback;

    // without the lock, if the user tap confirm and cancel quickly enough,
    // the bug will arise. the reason is follows:
    // 0. task is starting created, but not finished
    // 1. the task is cancel for the task hasn't been created, it do nothing
    // 2. task is created
    // 3. Oops, the task should be cancelled, but it is running
    private final Object mLock = new Object();

    // ==================================================================
    // constructor ======================================================

    public ESPTouchTask(Context context, ESPTouchTaskCallback callback) {
        this.context = context;
        this.mCallback = callback;
    }

    // ==================================================================
    // methods ==========================================================

    /**
     * 后台运行任务
     * @param params 参数
     *
     * @return
     */
    @Override
    protected List<IEsptouchResult> doInBackground(byte[]... params) {
        int taskResultCount;
        synchronized (mLock) {
            // wifi名称
            byte[] apSsid = params[0];
            // wifi mac地址
            byte[] apBssid = params[1];
            // wifi密码
            byte[] apPassword = params[2];
            // 设备数量
            byte[] deviceCountData = params[3];
            // 广播类型
            byte[] broadcastData = params[4];

            taskResultCount = deviceCountData.length == 0 ? -1 : Integer.parseInt(new String(deviceCountData));
            mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, context);
            mEsptouchTask.setPackageBroadcast(broadcastData[0] == 1);
        }
        return mEsptouchTask.executeForResults(taskResultCount);
    }

    /**
     * 任务执行成功回调
     * @param results 执行结果
     */
    @Override
    protected void onPostExecute(List<IEsptouchResult> results) {
        if (null == results || results.size() <= 0) {
            mCallback.handlerESPTouchTaskResult(new ESPThouchTaskResult("未接收到配网结果"));
            return;
        }

        // 检查任务是否被取消且没有结果
        IEsptouchResult firstResult = results.get(0);
        if (firstResult.isCancelled()) {
            mCallback.handlerESPTouchTaskResult(new ESPThouchTaskResult("配网任务已被取消"));
            return;
        }
        if (!firstResult.isSuc()) {
            mCallback.handlerESPTouchTaskResult(new ESPThouchTaskResult("配网失败，退出重试"));
            return;
        }


        // 回调成功结果
        ESPThouchTaskResult espThouchTaskResult = new ESPThouchTaskResult();
        espThouchTaskResult.setDeviceBssid(firstResult.getBssid().toUpperCase());
        if (null != firstResult.getInetAddress()) {
            espThouchTaskResult.setDeviceIp(firstResult.getInetAddress().getHostAddress());
        }
        mCallback.handlerESPTouchTaskResult(espThouchTaskResult);
    }

    /**
     * 取消任务
     */
    public void cancelTask() {
        cancel(true);
        if (mEsptouchTask != null) {
            mEsptouchTask.interrupt();
        }
    }
}
