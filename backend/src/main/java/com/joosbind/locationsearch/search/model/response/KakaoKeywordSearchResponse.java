package com.joosbind.locationsearch.search.model.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Getter
class SameName{
    @JsonProperty("keyword")
    private String keyword;
    @JsonProperty("region")
    private ArrayList region;
    @JsonProperty("selected_region")
    private String selected_region;
}
@Getter
class Meta {

    @JsonProperty("is_end")
    private boolean is_end;
    @JsonProperty("pageable_count")
    private int pageable_count;
    @JsonProperty("same_name")
    private SameName same_name;
    @JsonProperty("total_count")
    private String total_count;
}


@Getter
@ApiModel(value = "KakaoKeywordSearchResponse ")
public class KakaoKeywordSearchResponse {
    @JsonProperty("documents")
    private ArrayList<Documents> documents;
    @JsonProperty("meta")
    private Meta meta; // 사원이름

}
