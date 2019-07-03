package com.joosbind.locationsearch.search.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.joosbind.locationsearch.search.model.type.AccountRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter @EqualsAndHashCode
@Builder @NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "account", fetch = FetchType.EAGER)
    @OrderBy("searchAt desc")
    @JsonManagedReference
    @Builder.Default
    private List<SearchHistory> searchHistories = new ArrayList<>();
//    DTO와 같이 별도의 전달 객체를 활용하여 Mapper를 이용한다면 위와 같은 문제가 없겠지만 경우에 따라 필요한 옵션이 있을 수 있어 찾아보았다.
//    	@JsonDeserialize(using = ManualJsonConverter.LocalDateTimeDeserializer.class)


    @ElementCollection(fetch= FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<@NotNull @Valid AccountRole> roles;

    public void addSearchHistories(SearchHistory searchHistory){
        this.searchHistories.add(searchHistory);
        searchHistory.setAccount(this);
    }

}
