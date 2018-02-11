package com.yikejian.payment.service;

import com.github.wxpay.sdk.WXPay;
import com.google.common.collect.Lists;
import com.yikejian.payment.config.MyWechatPayConfig;
import com.yikejian.payment.exception.PaymentExceptionCode;
import com.yikejian.payment.exception.PaymentServiceException;
import com.yikejian.payment.util.HashUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * <code>WechatPayService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/9 14:41
 */
@Service
@Deprecated
public class WechatPayService {

    private static final String NONCE_STR_KEY = "nonce_str";
    private static final String TRADE_TYPE = "trade_type";

    @Value("${weixin.pay.mch_id}")
    private String mchId;
    @Value("${weixin.pay.key}")
    private String key;
    @Value("${weixin.pay.notify_url}")
    private String notifyUrl;

    private WXPay wxpay;

    public WechatPayService() throws Exception {
        MyWechatPayConfig myWechatPayConfig = new MyWechatPayConfig();
        this.wxpay = new WXPay(new MyWechatPayConfig());
    }

    private String generateNonceStr() {
        return UUID.randomUUID().toString();
    }

    public String generateSignature(Map<String, String> params) {
        params.put(NONCE_STR_KEY, generateNonceStr());
        List<String> keys = Lists.newArrayList(
                params.entrySet().stream().
                        filter(param -> StringUtils.isNotBlank(param.getValue())).
                        map(Map.Entry::getKey).collect(Collectors.toList())
        );
        Collections.sort(keys);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            stringBuilder.append(key).append("=").append(params.get(key));
            if (i != keys.size() - 1) {
                stringBuilder.append("&");
            }
        }
        String stringA = stringBuilder.toString();
        String stringSignTemp = stringA + "&key=" + key;
        String signature;
        try {
            signature = HashUtils.md5Encode(stringSignTemp).toUpperCase();
        } catch (UnsupportedEncodingException e) {
            throw new PaymentServiceException(e.getLocalizedMessage(), PaymentExceptionCode.SIGNATURE_ERROR);
        } catch (NoSuchAlgorithmException e) {
            throw new PaymentServiceException(e.getLocalizedMessage(), PaymentExceptionCode.SIGNATURE_ERROR);
        }
        return signature;
    }

}
