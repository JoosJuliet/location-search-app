package com.joosbind.locationsearch.search.service;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.repository.SearchHistoryRepository;
import com.joosbind.locationsearch.search.service.AccountService;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    @Before
    public void setUp(){
        searchHistoryRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @After
    public void dbTruncate(){
        searchHistoryRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Test
    public void findByUsername() {
        String password = "test";
        String username = "joos";

        // given
        Account account = Account.builder()
                .username(username)
                .password(password)
                .roles(new HashSet<>(Arrays.asList(AccountRole.ADMIN, AccountRole.USER)))
                .build();

        // when
        accountService.saveAccount(account);

        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(passwordEncoder.matches(password, userDetails.getPassword()), is(true));
    }

    @Test
    public void findByUsernameFail() {
        expectedException.expect(UsernameNotFoundException.class);
        String username = "temp";
        accountService.loadUserByUsername(username);
    }

}