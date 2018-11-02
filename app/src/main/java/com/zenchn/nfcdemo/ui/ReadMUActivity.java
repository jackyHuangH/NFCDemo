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
 * <p>
 * Description:读取NFC非ndef格式数据
 * 格式可以自定义
 *
 * @author HZJ
 */
public class ReadMUActivity extends BaseNfcActivity {
    @BindView(R.id.tv_nfctext)
    TextView mTvNfctext;
    @BindView(R.id.tv_card_id)
    TextView mTvCardId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mu);
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
            mTvNfctext.setText("不支持MifareUltralight数据格式");
            return;
        }
        String data = readTag(tag);
        if (data != null) {
            Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
            mTvNfctext.setText("信息：" + data);
        }
    }

    public String readTag(Tag tag) {
        MifareUltralight ultralight = MifareUltralight.get(tag);
        try {
            ultralight.connect();
            byte[] data = ultralight.readPages(4);
            return new String(data, Charset.forName("GB2312"));
        } catch (Exception e) {
        } finally {
            try {
                ultralight.close();
            } catch (Exception e) {
            }
        }
        return null;
    }
}
