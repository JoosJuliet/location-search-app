package com.joosbind.locationsearch.search.service;


import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.model.ParamBuilder;
import com.joosbind.locationsearch.search.model.response.KakaoKeywordSearchResponse;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.repository.KeywordStaticsRepository;
import javassist.compiler.ast.Keyword;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FetchSearchOutputListTest {

    @Autowired
    KeywordStaticsRepository keywordStaticsRepository;

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    KeywordSearchService keywordSearchService;

    @Autowired
    AccountService accountService;
    @Before
    public void 키워드_넣기 () {
        KeywordStatistics keyword1 = KeywordStatistics.builder()
                .searchword("또보겠지떡볶이")
                .countOfSearch(2)
                .build();
        KeywordStatistics keyword2 = KeywordStatistics.builder()
                .searchword("스타벅스")
                .countOfSearch(1)
                .build();

        keywordStaticsRepository.saveAll(Arrays.asList(keyword1,keyword2));

    }

    @After
    public void db초기화 () {
        keywordStaticsRepository.deleteAll();
    }

    @Test
    public void 인기키워드_검색_결과들_가져오기() throws Exception {

        List<KeywordStatistics> popularKeywords = keywordSearchService.fetchPopularKeywords();
        assertTrue(popularKeywords.size() == 2);

    }

    @Test
    public void 인기키워드를_많이_검색한_순으로_가져오기() throws Exception {

        List<KeywordStatistics> popularKeywords = keywordSearchService.fetchPopularKeywords();
        assertTrue(popularKeywords.get(0).getCountOfSearch() == 2);

    }


}
