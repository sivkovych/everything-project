package com.sivkovych.everything_project.api.v1;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RestController
@Target(ElementType.TYPE)
@RequestMapping("/api/v1")
@Retention(RetentionPolicy.RUNTIME)
public @interface RestControllerV1 {
    @AliasFor(annotation = RestController.class) String value() default "";

    @AliasFor(annotation = RequestMapping.class,
              attribute = "consumes") String[] consumes() default {};

    @AliasFor(annotation = RequestMapping.class,
              attribute = "produces") String[] produces() default {};
}
