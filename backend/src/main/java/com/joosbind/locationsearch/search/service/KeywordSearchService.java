package com.joosbind.locationsearch.search.service;

import com.joosbind.locationsearch.search.entity.Account;
import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.model.ParamBuilder;
import com.joosbind.locationsearch.search.model.response.DocumentResponse;
import com.joosbind.locationsearch.search.model.response.Documents;
import com.joosbind.locationsearch.search.model.response.KakaoKeywordSearchResponse;
import com.joosbind.locationsearch.search.repository.AccountRepository;
import com.joosbind.locationsearch.search.repository.KeywordStaticsRepository;
import com.sun.jndi.toolkit.url.Uri;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class KeywordSearchService{

    @Autowired
    private final RestTemplate restTemplate;
    private final KeywordStaticsRepository keywordStaticsRepository;
    private final AccountRepository accountRepository;

    @Autowired
    private final AccountService accountService;

    private static final Integer defaultPaginationPage = 1;
    private static final Integer defaultPaginationSize  = 15;

    private static final int startIndex = 0;
    private static final int endIndex = 9;

    private static final String KAKAOAUTHORIZATION = "KakaoAK 1bc0118e6cf66b0dd51767f14dc33aec";


    public List<DocumentResponse> searchKeyword(ParamBuilder paramBuilder){


        return fetchKaKaoKeywordSearch(paramBuilder);
//        transaction으로 묶을 필요가 없다. 단지 fetchKakao부분만 되면 된다. (반드시 되어야하는 것이 아니니까)
    }

    public List<DocumentResponse> fetchKaKaoKeywordSearch(final ParamBuilder paramBuilder) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", KAKAOAUTHORIZATION);

        final URI uri =  UriComponentsBuilder.newInstance()
                .scheme("https").host("dapi.kakao.com")
                .path("/v2/local/search/keyword.json")
                .queryParam("query",paramBuilder.getSearchword())
                .queryParam("page", paramBuilder.getPage() != null ? paramBuilder.getPage().toString() : defaultPaginationPage.toString())
                .queryParam("size", paramBuilder.getSize() != null ? paramBuilder.getSize().toString() : defaultPaginationSize.toString())
                .build().encode().toUri();
        ResponseEntity<KakaoKeywordSearchResponse> response = restTemplate.exchange(
                uri
                , HttpMethod.GET
                , new HttpEntity(header)
                , KakaoKeywordSearchResponse.class);

        ArrayList<Documents> documents = response.getBody().getDocuments();

        String basicURL = "https://map.kakao.com/link/map/";

        List<DocumentResponse> documentResponses =  documents.stream().map( x ->
                DocumentResponse.builder().address_name(x.getAddress_name())
                        .id(x.getId())
                        .phone(x.getPhone())
                        .place_name(x.getPlace_name())
                        .road_address_name(x.getRoad_address_name())
                        .build()
        ).collect(Collectors.toList());
        return documentResponses;


    }

    @Transactional
    public void updateUserSearchHistoryAndCountOfKeyword(final ParamBuilder paramBuilder, final String user){
        updateUserSearchHistory(paramBuilder, user);
        addCountOfSearch(paramBuilder);

    }

    private void updateUserSearchHistory(final ParamBuilder paramBuilder, final String username){
         Account user = accountRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));
         user.addSearchHistories(SearchHistory.builder()
                .searchword(paramBuilder.getSearchword())
                .build());

        accountRepository.save(user);
    }

    private void addCountOfSearch(final ParamBuilder paramBuilder){
        Optional<KeywordStatistics> targetKeyword = keywordStaticsRepository.findBySearchword(paramBuilder.getSearchword());
        if (targetKeyword.isPresent()){//            있으면 count올리기
            keywordStaticsRepository.save(targetKeyword.get().increaseCount());
            return;
        }
//        없으면 그냥 하나 만들기
        keywordStaticsRepository.save(
                KeywordStatistics.builder()
                .searchword(paramBuilder.getSearchword())
                .countOfSearch(1)
                .build());
    }

    public List<KeywordStatistics> fetchPopularKeywords(){
        int lastenIndex = endIndex;

        List<KeywordStatistics> keywordStatistics = keywordStaticsRepository.findAll(Sort.by(Sort.Direction.DESC, "countOfSearch"));
        final int sizeOfkeywordStatics = keywordStatistics.size();

        if (sizeOfkeywordStatics == 0){
            return Collections.emptyList();
        } else if (sizeOfkeywordStatics < 9) {
            lastenIndex = sizeOfkeywordStatics;
        }
        return keywordStatistics.subList(startIndex, lastenIndex);

    }

    public List<SearchHistory> getUserSearchHistory(final String username) {

        Account targetAccount = accountRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(username));

        return targetAccount.getSearchHistories();


    }

}
