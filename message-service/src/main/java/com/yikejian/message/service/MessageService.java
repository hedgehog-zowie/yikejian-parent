package com.yikejian.message.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.yikejian.message.domain.message.Message;
import com.yikejian.message.exception.MessageServiceException;
import com.yikejian.message.repository.MessageRepository;
import com.yikejian.message.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * <code>ProductService</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 9:56
 */
@Service
public class MessageService {

    @Value("${yikejian.message.connectTimeout}")
    private String connectTimeout;
    @Value("${yikejian.message.readTimeout}")
    private String readTimeout;
    @Value("${yikejian.message.product}")
    private String product;
    @Value("${yikejian.message.domain}")
    private String domain;
    @Value("${yikejian.message.accessKeyId}")
    private String accessKeyId;
    @Value("${yikejian.message.accessKeySecret}")
    private String accessKeySecret;
    @Value("${yikejian.message.region}")
    private String region;
    @Value("${yikejian.message.sign}")
    private String sign;
    @Value("${yikejian.message.templateCode}")
    private String templateCode;

    private MessageRepository messageRepository;
    private IAcsClient acsClient;
    private static boolean inited = false;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    private void init() {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", connectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", readTimeout);
        //初始化ascClient
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId,
                accessKeySecret);
        try {
            DefaultProfile.addEndpoint(region, region, product, domain);
        } catch (ClientException e) {
            throw new MessageServiceException(e.getLocalizedMessage());
        }
        acsClient = new DefaultAcsClient(profile);
    }

    @HystrixCommand
    public Message sendMessage(Message message) {
        if (!inited) {
            init();
        }
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //使用post提交
        request.setMethod(MethodType.POST);
        //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
        request.setPhoneNumbers(message.getPhoneNumber());
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(sign);
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(JsonUtils.toJson(message));
        //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        // 请求失败这里会抛ClientException异常
        SendSmsResponse sendSmsResponse;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if (sendSmsResponse.getCode() != null) {
                message.setStatus(ResponseCode.getResponseCodeMap().get(sendSmsResponse.getCode()));
            }
            return messageRepository.save(message);
        } catch (ClientException e) {
            throw new MessageServiceException(e.getLocalizedMessage());
        }
    }

}
