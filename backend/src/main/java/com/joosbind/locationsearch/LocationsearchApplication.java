package com.joosbind.locationsearch;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import com.joosbind.locationsearch.search.service.AccountService;
import com.joosbind.locationsearch.search.common.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;

@SpringBootApplication
public class LocationsearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocationsearchApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(){
        return new ApplicationRunner() {
            @Autowired AccountService accountService;
            @Autowired AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account user = Account.builder()
                        .username(appProperties.getUsername())
                        .password(appProperties.getPassword())
                        .roles(new HashSet<>(Arrays.asList(AccountRole.USER, AccountRole.ADMIN)))
                        .build();

                Account user2 = Account.builder()
                        .username(appProperties.getUsername2())
                        .password(appProperties.getPassword2())
                        .roles(new HashSet<>(Arrays.asList(AccountRole.USER, AccountRole.ADMIN)))
                        .build();

                accountService.saveAccount(user);
                accountService.saveAccount(user2);
            }
        };
    }
}
