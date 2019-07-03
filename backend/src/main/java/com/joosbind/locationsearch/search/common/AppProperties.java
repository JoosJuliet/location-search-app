package com.joosbind.locationsearch.search.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "location-app")
@Getter @Setter
public class AppProperties {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    @NotEmpty
    private String username2;
    @NotEmpty
    private String password2;

    @NotEmpty
    private String clientId;
    @NotEmpty
    private String clientSecret;
}
