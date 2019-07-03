package com.joosbind.locationsearch.search.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ParamBuilder {
    private String searchword;
    private Integer page;
    private Integer size;
}
