package com.joosbind.locationsearch.search.controller;

import com.joosbind.locationsearch.search.entity.KeywordStatistics;
import com.joosbind.locationsearch.search.entity.SearchHistory;
import com.joosbind.locationsearch.search.model.ParamBuilder;
import com.joosbind.locationsearch.search.model.response.DocumentResponse;
import com.joosbind.locationsearch.search.model.response.Documents;
import com.joosbind.locationsearch.search.model.response.KakaoKeywordSearchResponse;
import com.joosbind.locationsearch.search.service.KeywordSearchService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@AllArgsConstructor
public class SearchController {
    @Autowired
    private final KeywordSearchService keywordSearchService;


    @PostMapping("/search")
    public List<DocumentResponse> searchKeyword(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "searchword", required = true) final String searchword,
            final Principal principal
    ) {
        final ParamBuilder paramBuilder = ParamBuilder.builder()
                .searchword(searchword)
                .page(page)
                .size(size)
                .build();
        keywordSearchService.updateUserSearchHistoryAndCountOfKeyword(paramBuilder, principal.getName());
        return keywordSearchService.searchKeyword(paramBuilder);
    }

    @GetMapping("/search/pages")
    public List<DocumentResponse> getPages(
            @RequestParam(value = "page", required = false) final Integer page,
            @RequestParam(value = "size", required = false) final Integer size,
            @RequestParam(value = "searchword", required = true) final String searchword,
            final Principal principal
    ){
        final ParamBuilder paramBuilder = ParamBuilder.builder()
                .searchword(searchword)
                .page(page)
                .size(size)
                .build();

        return keywordSearchService.searchKeyword(paramBuilder);
    }


    @GetMapping("/popular-searchword")
    public List<KeywordStatistics> fetchPopularKeywords() {

        return keywordSearchService.fetchPopularKeywords();


    }


    @GetMapping("/search/history")
    public List<SearchHistory> fetchUserSearchHistory(
            Principal principal
    ) {
        return keywordSearchService.getUserSearchHistory(principal.getName());
    }




}
