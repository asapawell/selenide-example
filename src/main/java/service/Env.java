package service;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ExtendWith(EnvAnnotationProcessor.class)
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Env {

    Browser[] value() default {Browser.chrome, Browser.firefox};

    enum Browser {
        chrome, firefox
    }
}
