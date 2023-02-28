package com.iseedead.everything_project.api.matchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iseedead.everything_project.api.ApiError;
import com.iseedead.everything_project.api.HttpException;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.iseedead.everything_project.Application.getObjectMapper;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToObject;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SuppressWarnings("unused")
public class ResponseBodyMatchers {
    private final ObjectMapper objectMapper = getObjectMapper();

    public static ResponseBodyMatchers body() {
        return new ResponseBodyMatchers();
    }

    public <T> ResultMatcher is(T expected) {
        return mvcResult -> {
            var json = mvcResult.getResponse()
                    .getContentAsString();
            if (json.isBlank() || json.isEmpty()) {
                fail("Empty Response Content");
            }
            var actualObject = objectMapper.readValue(json, expected.getClass());
            assertThat(actualObject, Matchers.is(equalToObject(expected)));
        };
    }

    public ResultMatcher isError(HttpStatus status, String message) {
        return is(new ApiError(status, message));
    }

    public ResultMatcher isError(HttpException exception) {
        return is(new ApiError(exception));
    }

    public ResultMatcher isNotFound(String message) {
        return isError(HttpStatus.NOT_FOUND, message);
    }

    public ResultMatcher isBadRequest(String message) {
        return isError(HttpStatus.BAD_REQUEST, message);
    }

    public ResultMatcher isInternalServerError(String message) {
        return isError(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public ResultMatcher isUnsupportedMediaType(String message) {
        return isError(HttpStatus.UNSUPPORTED_MEDIA_TYPE, message);
    }

    public ResultMatcher isEmpty() {
        return mvcResult -> {
            var content = mvcResult.getResponse()
                    .getContentAsString();
            assertTrue(content.isEmpty());
        };
    }

    public ResultMatcher isNotEmpty() {
        return mvcResult -> {
            var content = mvcResult.getResponse()
                    .getContentAsString();
            assertTrue(!content.isEmpty() && !content.isBlank());
        };
    }
}
