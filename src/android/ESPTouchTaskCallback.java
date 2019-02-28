/*
 * Copyright (c) 2015 - 广东小哈科技股份有限公司
 * All rights reserved.
 *
 * Created on 2019-02-27
 */
package org.apache.cordova.esptouch;

/**
 * ESPTouch配网任务回调接口
 *
 * @author huchiwei
 * @version 1.0.0
 */
public interface ESPTouchTaskCallback {
    /**
     * 回调处理配网结果
     * @param result 配网结果
     */
    void handlerESPTouchTaskResult(ESPTouchTaskResult result);
}
