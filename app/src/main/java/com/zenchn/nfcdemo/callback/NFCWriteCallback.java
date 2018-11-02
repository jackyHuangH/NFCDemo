package com.zenchn.nfcdemo.callback;

/**
 * @author:Hzj
 * @date :2018/11/2/002
 * desc  ：
 * record：
 */
public interface NFCWriteCallback {
    void onWriteResult(boolean success, String msg);
}
