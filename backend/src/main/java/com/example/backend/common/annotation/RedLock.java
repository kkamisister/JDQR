package com.example.backend.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedLock {
    String key();
    long waitTime() default 10000L;
    long leaseTime() default 3000L;
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;
}
