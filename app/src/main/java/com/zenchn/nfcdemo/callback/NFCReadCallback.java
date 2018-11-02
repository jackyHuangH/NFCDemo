package com.zenchn.nfcdemo.callback;

import java.util.List;
import java.util.Map;

/**
 * @author:Hzj
 * @date :2018/11/2/002
 * desc  ：
 * record：
 */
public interface NFCReadCallback {
    void onReadSuccess(Map<String, List<String>> resultMap);

    void error();
}
