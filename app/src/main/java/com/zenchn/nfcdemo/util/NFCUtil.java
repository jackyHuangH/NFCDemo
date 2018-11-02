package com.zenchn.nfcdemo.util;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;

/**
 * @author:Hzj
 * @date :2018/11/2/002
 * desc  ：nfc 连接操作工具类
 * record：
 */
public class NFCUtil {
    /**
     * 获取IC卡的序列号
     *
     * @param intent
     */
    public static String readCardId(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag != null) {
            byte[] ids = tag.getId();
            String uid = DataUtil.bytesToHexString(ids, ids.length);
            return uid;
        }
        return "";
    }
}
