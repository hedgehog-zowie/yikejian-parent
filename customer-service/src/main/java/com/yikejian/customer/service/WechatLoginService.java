package com.yikejian.customer.service;

import com.yikejian.customer.domain.code.Code;
import com.yikejian.customer.domain.customer.Customer;
import com.yikejian.customer.domain.message.Message;
import com.yikejian.customer.domain.user.User;
import com.yikejian.customer.domain.user.UserType;
import com.yikejian.customer.domain.wechat.SuccessResponse;
import com.yikejian.customer.exception.CustomerExceptionCode;
import com.yikejian.customer.exception.CustomerServiceException;
import com.yikejian.customer.util.CodeUtils;
import com.yikejian.customer.util.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
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
    private static final int CODE_EXPIRE = 5 * 60 * 1000;

    @Value("${weixin.token.url}")
    private String URL_TEMPLATE;
    @Value("${weixin.app.id}")
    private String APPID;
    @Value("${weixin.app.secret}")
    private String SECRET;
    @Value("${yikejian.user.api.user}")
    private String USER_URL;
    @Value("${yikejian.user.api.token}")
    private String TOKEN_URL;
    @Value("${yikejian.message.url}")
    private String MESSAGE_URL;
    @Value("${yikejian.message.code.template}")
    private String MESSAGE_TEMPLATE;

    @Autowired
    private OAuth2RestTemplate oAuth2RestTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CustomerService customerService;

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

    public OAuth2AccessToken login(String code) {
        SuccessResponse successResponse = loginToWechat(code);
        // find customer by openid
        String openId = successResponse.getOpenid();
        Customer customer = customerService.getCustomerByOpenId(openId);
        if (customer == null) {
            // save customer
            customer = new Customer(openId);
            customerService.saveCustomer(customer);
        }
        // generate password
        String password = CodeUtils.generateCode();
        // get user from user-service
        User user = new User(customer.getOpenId(), UserType.CUSTOMER, password);
        User savedUser = oAuth2RestTemplate.postForObject(USER_URL, user, User.class);
        if (savedUser == null) {
            throw new CustomerServiceException("login error");
        }
        // get token from user-service
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic dHJ1c3RlZDpzZWNyZXQ=");
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        String params = String.format("username=%s&password=%s&grant_type=%s", savedUser.getName(), savedUser.getPassword(), "password");
        HttpEntity httpEntity = new HttpEntity(params, httpHeaders);
        OAuth2AccessToken oAuth2AccessToken = restTemplate.postForObject(TOKEN_URL, httpEntity, OAuth2AccessToken.class);
        return oAuth2AccessToken;
    }

    public Code checkWechatCustomer(String openId, String mobileNumber) {
        Customer customer = customerService.getCustomerByOpenId(openId);
        customer.setMobileNumber(mobileNumber);
        customerService.saveCustomer(customer);
        // send message
        String code = CodeUtils.generateCode();
        String content = String.format(MESSAGE_TEMPLATE, code);
        Message message = new Message(mobileNumber, code, content);
        Message sendedMessage = oAuth2RestTemplate.postForObject(MESSAGE_URL, message, Message.class);
        return new Code(code, CODE_EXPIRE);
    }

}
