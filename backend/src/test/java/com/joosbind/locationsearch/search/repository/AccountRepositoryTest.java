package com.joosbind.locationsearch.search.repository;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private SearchHistoryRepository searchHistoryRepository;


    @Before
    public void setup() {
        searchHistoryRepository.deleteAll();
        accountRepository.deleteAll();
        Account account = Account.builder()
                .username("아이유")
                .password("이지금")
                .build();
        accountRepository.save(account);

        SearchHistory searchHistory = SearchHistory.builder()
                .searchword("서울")
                .build();
        searchHistoryRepository.save(searchHistory);

    }

    @After
    public void cleanAll() {
        accountRepository.deleteAll();
        searchHistoryRepository.deleteAll();
    }


    @Test
    public void 고객_생성되는지_확인() throws Exception {

        Account iu = accountRepository.findAll().get(0);
        Assert.assertThat(iu.getUsername(), is("아이유"));

    }
    @Test
    public void 히스토리_생성되는지_확인하기() throws Exception {

        SearchHistory history = searchHistoryRepository.findAll().get(0);
        Assert.assertThat(history.getSearchword(), is("서울"));


    }

}
