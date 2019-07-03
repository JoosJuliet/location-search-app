package com.joosbind.locationsearch.search.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@Entity
@Getter
@Setter
public class KeywordStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String searchword;


    private @NotNull @Positive int countOfSearch;
    //    무조건 이것이 만들어질 때는 1부터 시작이다.

    @Builder
    public KeywordStatistics(
            @NotNull @Valid final String searchword,
            final int countOfSearch
    ){
        this();
        this.searchword = searchword;
        this.countOfSearch = countOfSearch;

    }

    public KeywordStatistics increaseCount(){
        this.countOfSearch++;
        return this;
    }
}
