package com.joosbind.locationsearch.search.controller;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import com.joosbind.locationsearch.search.repository.KeywordStaticsRepository;
import com.joosbind.locationsearch.search.service.AccountService;
import com.joosbind.locationsearch.search.common.AppProperties;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.repository.SearchHistoryRepository;
import lombok.var;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Autowired
    SearchHistoryRepository searchHistoryRepository;
    @Autowired
    KeywordStaticsRepository keywordStaticsRepository;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AppProperties appProperties;

    String token;

    @Before
    public void setUp() throws Exception {
        accountRepository.deleteAll();
        keywordStaticsRepository.save(
                KeywordStatistics.builder().searchword("카카오프렌즈")
                        .countOfSearch(1).build()
        );
        token = getAuthToken();
    }

    @After
    public void truncateDB() throws Exception {
        keywordStaticsRepository.deleteAll();
    }

    @Test
    public void 인기검색_확인() throws Exception {


        mockMvc.perform(get("/popular-searchword"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    public void 키워드검색_Pagination() throws Exception {
        mockMvc.perform(post("/search")
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                .param("searchword","카카오프렌즈")
                .param("page", "1")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 키워드검색_Without_Pagination() throws Exception {
        mockMvc.perform(post("/search")
                .header(HttpHeaders.AUTHORIZATION, "Bearer" + token)
                .param("searchword","카카오프렌즈"))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void 유저의_검색기록_반환() throws Exception {
        mockMvc.perform(get("/search/history")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    public String getAuthToken() throws Exception {
        String clientId = appProperties.getClientId();
        String clientSecret = appProperties.getClientSecret();

        // given
        String username = appProperties.getUsername();
        String password = appProperties.getPassword();

        SearchHistory searchHistory = SearchHistory.builder()
                .searchword("keyword1")
                .build();

        Account account = Account.builder()
                .username(username)
                .password(password)
                .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                .build();

        account.addSearchHistories(searchHistory);

        this.accountService.saveAccount(account);

        ResultActions actions = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(clientId, clientSecret))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());

        var responseBody = actions.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(responseBody).get("access_token").toString();
    }
}