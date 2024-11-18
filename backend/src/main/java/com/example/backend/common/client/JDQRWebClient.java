package com.example.backend.common.client;

import com.example.backend.common.client.toss.dto.TossErrorResponse;
import com.example.backend.common.exception.ErrorCode;
import com.example.backend.common.exception.JDQRException;
import io.netty.channel.ChannelOption;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientRequest;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class JDQRWebClient {

    private static final Integer CONNECT_TIMEOUT_MILLIS = 5000;
    private static final Integer RESPONSE_TIMEOUT_MILLIS = 8000;

    // timeOut 지정을 위한 client
    private final HttpClient httpClient = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CONNECT_TIMEOUT_MILLIS)
        .responseTimeout(Duration.of(RESPONSE_TIMEOUT_MILLIS, ChronoUnit.MILLIS));

    // header가 application_json_value인 client
    private final WebClient webClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs(configurer ->
                    configurer.defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024)   //  2MB
                )
                .build()
        )
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();

    // header가 application_form_urlencoded_value인 httpClient
    private final WebClient urlFormWebClient = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .exchangeStrategies(
            ExchangeStrategies.builder()
                .codecs(configurer ->
                    configurer.defaultCodecs()
                        .maxInMemorySize(2 * 1024 * 1024)   //  2MB
                )
                .build()
        )
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();

    public WebClient.ResponseSpec get(String uri) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        return this.get(uri, headers);
    }

    public WebClient.ResponseSpec getFromMap(String uri, Map<String, String> headers) {
        MultiValueMap<String, String> headerMulti = new LinkedMultiValueMap<>();
        headerMulti.setAll(headers);

        return this.get(uri, headerMulti);
    }

    public WebClient.ResponseSpec get(String uri, MultiValueMap<String, String> headers) {
        return webClient.get()
            .uri(uri)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_400_ERROR))))
            .onStatus(HttpStatusCode::is5xxServerError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_500_ERROR))));
    }

    public WebClient.ResponseSpec post(String uri, Object body) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        return this.post(uri, body, headers);
    }

    public WebClient.ResponseSpec post(String uri, Object body, long timeoutMillis) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

        return this.post(uri, body, headers, timeoutMillis);
    }

    public WebClient.ResponseSpec postFromMap(String uri, Object body, Map<String, String> headers) {
        MultiValueMap<String, String> headerMulti = new LinkedMultiValueMap<>();
        headerMulti.setAll(headers);

        return this.post(uri, body, headerMulti);
    }

//    public WebClient.ResponseSpec post(String uri, Object body, MultiValueMap<String, String> headers) {
//        return webClient.post()
//            .uri(uri)
//            .headers(httpHeaders -> httpHeaders.addAll(headers))
//            .bodyValue(body)
//            .retrieve()
//            .onStatus(HttpStatusCode::is4xxClientError, response
//                -> response.bodyToMono(String.class)
//                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_400_ERROR))))
//            .onStatus(HttpStatusCode::is5xxServerError, response
//                -> response.bodyToMono(String.class)
//                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_500_ERROR))));
//    }

    public WebClient.ResponseSpec post(String uri, Object body, MultiValueMap<String, String> headers) {
        return webClient.post()
            .uri(uri)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response ->
                response.bodyToMono(TossErrorResponse.class)
                    .flatMap(asdf -> {
                        System.err.println("Error Body: " + asdf);
                        return Mono.error(new JDQRException(ErrorCode.WEBCLIENT_400_ERROR));
                    }))
            .onStatus(HttpStatusCode::is5xxServerError, response ->
                response.createException()
                    .flatMap(originalException -> {
                        originalException.printStackTrace(); // 원래 예외의 스택 트레이스를 출력
                        return Mono.error(new JDQRException(ErrorCode.WEBCLIENT_500_ERROR));
                    }));
    }

    public WebClient.ResponseSpec post(String uri, Object body, MultiValueMap<String, String> headers, long timeoutMillis) {
        return webClient.post()
            .uri(uri)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
            .httpRequest(consumer -> {
                //  httpClient 에서 설정한 responseTimeout 값을 override 하는 과정
                HttpClientRequest req = consumer.getNativeRequest();
                req.responseTimeout(Duration.ofMillis(timeoutMillis));
            })
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_400_ERROR))))
            .onStatus(HttpStatusCode::is5xxServerError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_500_ERROR))));
    }

    public WebClient.ResponseSpec putFromMap(String uri, Object body, Map<String, String> headers) {
        MultiValueMap<String, String> headerMulti = new LinkedMultiValueMap<>();
        headerMulti.setAll(headers);

        return this.put(uri, body, headerMulti);
    }

    public WebClient.ResponseSpec put(String uri, Object body, MultiValueMap<String, String> headers) {
        return webClient.put()
            .uri(uri)
            .headers(httpHeaders -> httpHeaders.addAll(headers))
//      .httpRequest(consumer -> {
//        //  httpClient 에서 설정한 responseTimeout 값을 override 하는 과정
//        HttpClientRequest req = consumer.getNativeRequest();
//      })
            .bodyValue(body)
            .retrieve()
            .onStatus(HttpStatusCode::is4xxClientError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_400_ERROR))))
            .onStatus(HttpStatusCode::is5xxServerError, response
                -> response.bodyToMono(String.class)
                .flatMap(r -> Mono.error(new JDQRException(ErrorCode.WEBCLIENT_500_ERROR))));
    }
}
