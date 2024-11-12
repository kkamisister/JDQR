package com.example.backend.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JsonUtil {
  private static final String EXCEPTION_MESSAGE_PREFIX = "Json read error : ";
  private static ObjectMapper mapper;
  private static ObjectMapper mapperWrapRootValueEnable;
  private static ObjectMapper mapperUnWrapRootValueEnable;
  private static ObjectMapper mapperIgnoreJsonPropertyAnnotation;
  private static ObjectMapper mapperKafkaMessagePayload;
  private static ObjectMapper mapperWithOutRoot;

  private static ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new ObjectMapper();
      mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      // mapper.registerModule(new JavaTimeModule());  // Java 8 날짜 및 시간 지원
    }
    return mapper;
  }

  private static ObjectMapper getMapperWrapRootValueEnable() {
    if (mapperWrapRootValueEnable == null) {
      mapperWrapRootValueEnable = new ObjectMapper();
      mapperWrapRootValueEnable.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapperWrapRootValueEnable.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
      mapperWrapRootValueEnable.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }
    return mapperWrapRootValueEnable;
  }

  private static ObjectMapper getMapperUnWrapRootValueEnable() {
    if (mapperUnWrapRootValueEnable == null) {
      mapperUnWrapRootValueEnable = new ObjectMapper();
      mapperUnWrapRootValueEnable.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapperUnWrapRootValueEnable.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
      mapperUnWrapRootValueEnable.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }
    return mapperUnWrapRootValueEnable;
  }

  private static ObjectMapper getMapperIgnoreJsonPropertyAnnotation() {
    if (mapperIgnoreJsonPropertyAnnotation == null) {
      mapperIgnoreJsonPropertyAnnotation = new ObjectMapper();
      mapperIgnoreJsonPropertyAnnotation.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapperIgnoreJsonPropertyAnnotation.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
      mapperIgnoreJsonPropertyAnnotation.configure(MapperFeature.USE_ANNOTATIONS, false);
    }
    return mapperIgnoreJsonPropertyAnnotation;
  }

  private static ObjectMapper getArgoKafkaMessagePayloadMapper() {
    if (mapperKafkaMessagePayload == null) {
      mapperKafkaMessagePayload = new ObjectMapper();
      mapperKafkaMessagePayload.configure(SerializationFeature.INDENT_OUTPUT, false);
      mapperKafkaMessagePayload.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      mapperKafkaMessagePayload.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    return mapperKafkaMessagePayload;
  }

  private static ObjectMapper getMapperWithOutRoot() {
    if (mapperWithOutRoot == null) {
      mapperWithOutRoot = new ObjectMapper();
      mapperWithOutRoot.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
      mapperWithOutRoot.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
      mapperWithOutRoot.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
    }
    return mapperWithOutRoot;
  }

  public static <T> T read(final String json, final Class<T> clazz) {
    try {
      return getMapper().readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <T> T readUnwrapRootValue(final String json, final Class<T> clazz) {
    try {
      return getMapperUnWrapRootValueEnable().readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <T> T read(final String json, final TypeReference valueTypeRef) {
    try {
      return getMapper().readValue(json, (TypeReference<T>) valueTypeRef);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <T> T read(final InputStream json, final Class<T> clazz) {
    try {
      return getMapper().readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <T> List<T> readList(final String json, final Class<T> clazz) {
    try {
      JavaType type = getMapper().getTypeFactory().constructCollectionType(List.class, clazz);
      return getMapper().readValue(json, type);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <K, V> Map<K, V> readMap(final String json, TypeReference<Map<K, V>> typeReference) {
    try {
      return getMapper().readValue(json, typeReference);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static <T> T readWithOutRoot(final String json, final Class<T> clazz) {
    try {
      return getMapperWithOutRoot().readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static String write(Object value) {
    try {
      return getMapper().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json write error : ", e);
    }
  }


  public static String writeRootValueInclude(Object value) {
    try {
      return getMapperWrapRootValueEnable().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json write error : ", e);
    }
  }

  public static String writeKafkaMessagePayload(Object value) {
    try {
      return getArgoKafkaMessagePayloadMapper().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json write error : ", e);
    }
  }

  public static String writeIgnoreJsonPropertyAnn(Object value) {
    try {
      return getMapperIgnoreJsonPropertyAnnotation().writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json write error : ", e);
    }
  }

  public static <T> T readIgnoreJsonPropertyAnn(final String json, final Class<T> clazz) {
    try {
      return getMapperIgnoreJsonPropertyAnnotation().readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(EXCEPTION_MESSAGE_PREFIX, e);
    }
  }

  public static String objectToString(Object cachedData) {
    try {
      return getMapper().writeValueAsString(cachedData);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Json write error : ", e);
    }
  }

  private void __assertNotNull(Object obj) throws AssertionError {
    if (obj == null) {
      throw new AssertionError("Passed argument is null.");
    }
  }

}

