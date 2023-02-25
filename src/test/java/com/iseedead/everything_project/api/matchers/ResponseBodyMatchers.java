package com.iseedead.everything_project.api.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.is;

public class ResponseBodyMatchers {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static ResponseBodyMatchers body() {
        return new ResponseBodyMatchers();
    }

    public <T> ResultMatcher isEqualTo(T expected) {
        return mvcResult -> {
            var json = mvcResult.getResponse()
                    .getContentAsString();
            var actualObject = objectMapper.readValue(json, expected.getClass());
            assertThat(actualObject, is(equalToObject(expected)));
        };
    }
}
