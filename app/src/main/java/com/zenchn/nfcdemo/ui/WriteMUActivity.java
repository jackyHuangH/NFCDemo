package com.zenchn.nfcdemo.ui;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareUltralight;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.zenchn.nfcdemo.R;
import com.zenchn.nfcdemo.util.NFCUtil;

import java.nio.charset.Charset;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:写入NFC 非ndef格式的数据
 */
public class WriteMUActivity extends BaseNfcActivity {
    @BindView(R.id.tv_card_id)
    TextView mTvCardId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_mu);
        ButterKnife.bind(this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        String cardId = NFCUtil.readCardId(intent);
        mTvCardId.setText(String.format(Locale.CHINA, "卡片编号：%1$s", cardId));

        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        String[] techList = tag.getTechList();
        boolean haveMifareUltralight = false;
        for (String tech : techList) {
            if (tech.indexOf("MifareUltralight") >= 0) {
                haveMifareUltralight = true;
                break;
            }
        }
        if (!haveMifareUltralight) {
            Toast.makeText(this, "不支持MifareUltralight数据格式", Toast.LENGTH_SHORT).show();
            return;
        }
        writeTag(tag);
    }

    public void writeTag(Tag tag) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            //写入八个汉字，从第五页开始写，中文需要转换成GB2312格式
            ultralight.writePage(4, "北京".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(5, "上海".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(6, "广州".getBytes(Charset.forName("GB2312")));
            ultralight.writePage(7, "天津".getBytes(Charset.forName("GB2312")));
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
        } finally {
            try {
                ultralight.close();
            } catch (Exception e) {
            }
        }
    }
}
