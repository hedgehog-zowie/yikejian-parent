package com.yikejian.data.config;

import com.yikejian.data.api.v1.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

/**
 * <code>UserAuditorBean</code>.
 * ${DESCRIPTION}
 *
 * @author zweig
 * @version: 1.0-SNAPSHOT
 * date: 2018/1/16 14:13
 */
@Configuration
public class UserAuditor implements AuditorAware<String> {

    private OAuth2RestTemplate oAuth2RestTemplate;

    @Value("${yikejian.user.url}")
    private String userUrl;

    @Autowired
    public UserAuditor(OAuth2RestTemplate oAuth2RestTemplate) {
        this.oAuth2RestTemplate = oAuth2RestTemplate;
    }

    @Override
    public String getCurrentAuditor() {
        UserDto user = oAuth2RestTemplate.getForObject(userUrl, UserDto.class);
        if (user != null) {
            return user.getUserName();
        } else {
            return null;
        }
    }
}
