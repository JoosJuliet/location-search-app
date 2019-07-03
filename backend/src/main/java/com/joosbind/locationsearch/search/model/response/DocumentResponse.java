package com.joosbind.locationsearch.search.model.response;


import com.joosbind.locationsearch.search.entity.SearchHistory;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@ApiModel(value = "/search에 대한 entity")
public class DocumentResponse {

    private String daumMapURL;
//  Daum 지도 바로가기 URL만들기 위해 있는 id
    private String place_name;
//  위치 이름
    private String road_address_name;
//  도로명주소
    private String address_name;
//  그냥 주소
    private String phone;

    @Builder
    public DocumentResponse(
            @NotNull @Valid final String id,
            @NotNull @Valid final String place_name,
            @NotNull @Valid final String road_address_name,
            @NotNull @Valid final String address_name,
            @NotNull @Valid final String phone
    ){

        String basicURL = "https://map.kakao.com/link/map/";


        this.daumMapURL = UriComponentsBuilder.fromUriString(basicURL)
                .path(id)
                .build().encode()
                .toUriString();
        this.place_name = place_name;
        this.road_address_name = road_address_name;
        this.address_name = address_name;
        this.phone = phone;
    }



}
// {
//         "address_name": "서울 강남구 삼성동 159",
//         "category_group_code": "",
//         "category_group_name": "",
//         "category_name": "가정,생활 > 문구,사무용품 > 디자인문구 > 카카오프렌즈",
//         "distance": "418",
//         "id": "26338954",
//         "phone": "02-6002-1880",
//         "place_name": "카카오프렌즈 스타필드 코엑스몰점",
//         "place_url": "http://place.map.kakao.com/26338954",
//         "road_address_name": "서울 강남구 영동대로 513",
//         "x": "127.059028716089",
//         "y": "37.5120756277877"
//         },