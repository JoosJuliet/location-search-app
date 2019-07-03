package com.joosbind.locationsearch.search.repository;


import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KeywordStaticsRepositoryTest {

    @Autowired
    private KeywordStaticsRepository keywordStaticsRepository;

    @Before
    public void setup() {

        KeywordStatistics keywordStatistics = KeywordStatistics.builder()
                .searchword("카카오프렌즈")
                .countOfSearch(1)
                .build();
        keywordStaticsRepository.save(keywordStatistics);
    }

    @After
    public void cleanDB() {

        keywordStaticsRepository.deleteAll();
    }

    @Test
    public void 키워드_잘_들어가는지_확인() throws Exception{
        KeywordStatistics keywordStatistics = KeywordStatistics.builder()
                .searchword("낙곱새")
                .countOfSearch(1)
                .build();
        keywordStaticsRepository.save(keywordStatistics);
        Assert.assertThat(keywordStaticsRepository.findBySearchword("낙곱새").get().getCountOfSearch(), is(1)) ;

    }

    @Test
    public void 이미_있는_키워드_카운트_올리기() throws Exception{

        KeywordStatistics keywordStatistics =  keywordStaticsRepository.findBySearchword("카카오프렌즈").get();

        keywordStaticsRepository.save(keywordStatistics.increaseCount());

        Assert.assertThat(keywordStaticsRepository.findBySearchword("카카오프렌즈").get().getCountOfSearch(), is(2));

    }

    @Test
    public void 처음나온_키워드_카운트_올리기() throws Exception{

        KeywordStatistics keywordStatistics =  KeywordStatistics.builder()
                .searchword("또보겠지떡볶이")
                .countOfSearch(1)
                .build();
        keywordStaticsRepository.save(keywordStatistics);

        Assert.assertThat(keywordStaticsRepository.findBySearchword("또보겠지떡볶이").get().getCountOfSearch(), is(1));


    }
}
