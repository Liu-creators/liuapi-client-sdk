package com.liu.liuapiclientsdk;

import com.liu.liuapiclientsdk.client.LiuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("liuapi.client")
@ComponentScan
@Data
public class LiuApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public LiuApiClient liuApiClient() {
        return new LiuApiClient(accessKey,secretKey);
    }
}
