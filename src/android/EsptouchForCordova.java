/*
 * Copyright (c) 2015 - 广东小哈科技股份有限公司
 * All rights reserved.
 *
 * Created on 2019-02-27
 */
package com.huchiwei.cordova.esptouch;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.espressif.iot.esptouch.util.ByteUtil;
import com.espressif.iot.esptouch.util.EspNetUtil;

import com.huchiwei.cordova.esptouch.ESPTouchTaskResult;
import com.huchiwei.cordova.esptouch.ESPTouchTask;
import com.huchiwei.cordova.esptouch.ESPTouchTaskCallback;

/**
 * ESPTouch配网Android调用入口
 *
 * @author huchiwei
 * @version 1.0.0
 */
public class EsptouchForCordova extends CordovaPlugin implements ESPTouchTaskCallback {

    private ESPTouchTask mEspTouchTask;
    private CallbackContext mCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.mCallbackContext = callbackContext;

        if (action.equals("start")) {
            this.start(args, callbackContext);
        } else if (action.equals("stop")) {
            this.stop();
        }
        return false;
    }

    @Override
    public void handlerESPTouchTaskResult(ESPThouchTaskResult result) {
        PluginResult pluginResult = null;
        if (result.isSuccess()) {
            pluginResult = new PluginResult(PluginResult.Status.OK, "{'deviceMac': "+ result.getDeviceBssid() +", 'deviceIp': "+ result.getDeviceIp() +"}")
        } else {
            pluginResult = new PluginResult(PluginResult.Status.ERROR, "{'errMsg': "+ result.getErrMsg() +"}")
        }
        pluginResult.setKeepCallback(true); // keep callback after this call
        this.mCallbackContext.sendPluginResult(result);
    }

    /**
     * 开始配网
     *
     * @param args 前端传入参数列表
     */
    private void start(JSONArray args) {
        byte[] wifiName = ByteUtil.getBytesByString(args[0]);
        byte[] wifiPassword = ByteUtil.getBytesByString(args[1]);
        byte[] wifiMac = EspNetUtil.parseBssid2bytes(args[2]);
        byte[] deviceCount = new String("1").getBytes();
        byte[] broadcast = new String("1").getBytes();

        // 如果不为空，则先终止配网任务
        if(mEspTouchTask != null) {
            mEspTouchTask.cancelTask();
        }
        mEspTouchTask = new ESPTouchTask(getContext(), this);
        mEspTouchTask.execute(ssid, bssid, password, deviceCount, broadcast);
    }

    /**
     * 如果不为空，则先终止配网任务
     */
    private void stop(CallbackContext callbackContext) {
        if(mEspTouchTask != null) {
            mEspTouchTask.cancelTask();
        }
        callbackContext.success();
    }
}
