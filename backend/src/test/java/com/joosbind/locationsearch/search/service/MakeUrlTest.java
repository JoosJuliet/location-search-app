package com.joosbind.locationsearch.search.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
public class MakeUrlTest {


    @Test
    public void url생성되는지_확인() throws Exception {


        String url = "https://map.kakao.com/link/map?urlX=37.402056&urlY=127.108212";
        String basicURL = "https://map.kakao.com/link/map";

        String urlX = "37.402056";
        String urlY = "127.108212";
        String mapURL = UriComponentsBuilder.fromUriString(basicURL)
                .queryParam("urlX", urlX)
                .queryParam("urlY", urlY)
                .toUriString();
        Assert.assertThat(mapURL, is(url));

    }
}
