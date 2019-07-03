package com.joosbind.locationsearch.search.configs;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import com.joosbind.locationsearch.search.service.AccountService;
import com.joosbind.locationsearch.search.common.AppProperties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthServerConfigTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    AppProperties appProperties;

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void setup(){
        accountRepository.deleteAll();  // 처음 시작할 때 넣기 때문에 setup시 db delete해야한다.

    }

    @After
    public void cleanDB(){
        accountRepository.deleteAll();
    }

    @Test
    public void getAuthToken() throws Exception {
        String clientId = appProperties.getClientId();
        String clientSecret = appProperties.getClientSecret();

        // given
        String username = appProperties.getUsername();
        String password = appProperties.getPassword();

        Account account = Account.builder()
                .username(username)
                .password(password)
                .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                .build();

        this.accountService.saveAccount(account);

        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());
    }
}