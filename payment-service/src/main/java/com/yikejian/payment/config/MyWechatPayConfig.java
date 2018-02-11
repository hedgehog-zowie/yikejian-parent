package com.yikejian.payment.config;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * <code>MyWechatPayConfig</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/9 16:16
 */
public class MyWechatPayConfig implements WXPayConfig {

    private byte[] certData;

    public MyWechatPayConfig() throws Exception {
        String certPath = "D:\\一刻间\\cert\\apiclient_cert.p12";
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    @Override
    public String getAppID() {
        return "wx74e6f71fdef09122";
    }

    @Override
    public String getMchID() {
        return "1324562801";
    }

    @Override
    public String getKey() {
        return "Yikejian001842015704713502801188";
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

}
