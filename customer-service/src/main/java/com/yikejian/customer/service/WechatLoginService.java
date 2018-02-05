package com.yikejian.customer.service;

import com.yikejian.customer.domain.wechat.SuccessResponse;
import com.yikejian.customer.exception.CustomerExceptionCode;
import com.yikejian.customer.exception.CustomerServiceException;
import com.yikejian.customer.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * <code>WeixinLoginService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/2/2 16:05
 */
@Service
public class WechatLoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WechatLoginService.class);

    @Value("weixin.token.url")
    private String URL_TEMPLATE;
    @Value("weixin.app.id")
    private String APPID;
    @Value("weixin.app.secret")
    private String SECRET;

    private RestTemplate restTemplate;

    @Autowired
    public WechatLoginService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private SuccessResponse loginToWechat(String code) {
        if (StringUtils.isBlank(code)) {
            throw new CustomerServiceException(CustomerExceptionCode.ILLEGAL_CODE, "login error, code is empty.");
        }
        String url = String.format(URL_TEMPLATE, APPID, SECRET, code);
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        if (HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            String responseString = (String) responseEntity.getBody();
            LOGGER.info(responseString);
            SuccessResponse successResponse;
            successResponse = JsonUtils.fromJson(responseString, SuccessResponse.class);
            if (StringUtils.isEmpty(successResponse.getOpenid())) {
                LOGGER.info(responseString);
                throw new CustomerServiceException(CustomerExceptionCode.WECHAT_LOGIN_ERROR, responseString);
            }
            return successResponse;
        } else {
            throw new CustomerServiceException(CustomerExceptionCode.NET_ERROR, responseEntity.getStatusCode().toString());
        }
    }

    private void saveCustomerAsUser(String username){

    }

}
