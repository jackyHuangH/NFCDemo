package com.zenchn.nfcdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zenchn.nfcdemo.ui.BaseNfcActivity;
import com.zenchn.nfcdemo.ui.ReadMUActivity;
import com.zenchn.nfcdemo.ui.ReadTextActivity;
import com.zenchn.nfcdemo.ui.ReadUriActivity;
import com.zenchn.nfcdemo.ui.RunAppActivity;
import com.zenchn.nfcdemo.ui.RunUrlActivity;
import com.zenchn.nfcdemo.ui.WriteMUActivity;
import com.zenchn.nfcdemo.ui.WriteTextActivity;
import com.zenchn.nfcdemo.ui.WriteUriActivity;


public class MainActivity extends BaseNfcActivity {
    private TextView ifo_NFC;
    private static final String[] strs = new String[]{
            "自动打开短信界面",
            "自动打开百度页面",
            "读NFC标签中的文本数据",
            "写NFC标签中的文本数据",
            "读NFC标签中的Uri数据",
            "写NFC标签中的Uri数据",
            "读NFC标签非NDEF格式的数据",
            "写NFC标签非NDEF格式的数据",
    };

    @Override
    public void onResume() {
        super.onResume();
        if (!ifNFCAvailable()) {
            return;
        } else {
            ifo_NFC.setText("该设备支持NFC，请写入数据！");
            ListView listView = (ListView) findViewById(R.id.listview);
            listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switchActivity(position);
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ifo_NFC = (TextView) findViewById(R.id.ifo_NFC);
    }

    private void switchActivity(int position) {
        switch (position) {
            case 0: //自动打开短信界面
                startActivity(new Intent(this, RunAppActivity.class));
                break;
            case 1: //自动打开百度页面
                startActivity(new Intent(this, RunUrlActivity.class));
                break;
            case 2: //读NFC标签中的文本数据
                startActivity(new Intent(this, ReadTextActivity.class));
                break;
            case 3: //写NFC标签中的文本数据
                startActivity(new Intent(this, WriteTextActivity.class));
                break;
            case 4: //读NFC标签中的Uri数据
                startActivity(new Intent(this, ReadUriActivity.class));
                break;
            case 5: //写NFC标签中的Uri数据
                startActivity(new Intent(this, WriteUriActivity.class));
                break;
            case 6: //读NFC标签非NDEF格式的数据
                startActivity(new Intent(this, ReadMUActivity.class));
                break;
            case 7: //写NFC标签非NDEF格式的数据
                startActivity(new Intent(this, WriteMUActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 检测工作,判断设备的NFC支持情况
     *
     * @return
     */
    protected Boolean ifNFCAvailable() {
        if (mNfcAdapter == null) {
            ifo_NFC.setText("设备不支持NFC！");
            return false;
        }
        if (mNfcAdapter != null && !mNfcAdapter.isEnabled()) {
            ifo_NFC.setText("请在系统设置中先启用NFC功能！");
            return false;
        }
        return true;
    }
}

