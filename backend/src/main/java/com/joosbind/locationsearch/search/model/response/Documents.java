package com.joosbind.locationsearch.search.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class Documents {
    @JsonProperty("address_name")
    private String address_name;
    @JsonProperty("category_group_code")
    private String category_group_code;
    @JsonProperty("category_group_name")
    private String category_group_name;
    @JsonProperty("category_name")
    private String category_name;
    @JsonProperty("distance")
    private String distance;
    @JsonProperty("id")
    private String id;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("place_name")
    private String place_name;
    @JsonProperty("place_url")
    private String place_url;
    @JsonProperty("road_address_name")
    private String road_address_name;
    @JsonProperty("x")
    private float x;
    @JsonProperty("y")
    private float y;


}
