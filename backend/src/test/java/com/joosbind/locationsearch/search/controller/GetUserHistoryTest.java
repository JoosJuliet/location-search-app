package com.joosbind.locationsearch.search.controller;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.repository.SearchHistoryRepository;
import com.joosbind.locationsearch.search.service.KeywordSearchService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
public class GetUserHistoryTest {


    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;
    @Autowired
    KeywordSearchService keywordSearchService;
    @Before
    public void setup() {

        Account account = Account.builder()
                .username("아이유")
                .password("이지금")
                .build();

        account.addSearchHistories(SearchHistory.builder()
                .searchword("서울")
                .build());
        accountRepository.save(account);

    }

    @Test
    public void User의_SearchHistory_가져오기() throws Exception {

        List<SearchHistory> histories = keywordSearchService.getUserSearchHistory("아이유");
        assertTrue(histories.size() == 1);

    }
}
