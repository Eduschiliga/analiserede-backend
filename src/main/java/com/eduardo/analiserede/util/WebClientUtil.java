package com.eduardo.analiserede.util;

import com.eduardo.analiserede.exception.customizadas.WebClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@Component
public class WebClientUtil {
    public static <T, U> Mono<T> enviarRequisicaoAsync(HttpHeaders httpHeaders, HttpMethod httpMethod, String uri, Object body, Class<T> responseType, Class<U> errorType) {
        try {
            HttpClient httpClient = HttpClient.create();

            return WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build()
                    .method(httpMethod)
                    .uri(uri)
                    .headers(headers -> headers.putAll(httpHeaders))
                    .bodyValue(body)
                    .exchangeToMono(response -> {
                        if (response.statusCode().isError()) {
                            return response.createError().flatMap((errorBody) -> Mono.error(new WebClientException(errorBody.toString(), HttpStatus.BAD_REQUEST)));
                        } else {
                            return response.bodyToMono(responseType);
                        }
                    });

        } catch (WebClientResponseException ex) {
            log.info("Erro de Resposta: Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            return Mono.error(new WebClientException(ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }


    public static <T, U> T enviarRequisicao(HttpHeaders httpHeaders, HttpMethod httpMethod, String uri, Object body, Class<T> responseType, Class<U> errorType) {
        try {
            HttpClient httpClient = HttpClient.create();

            return WebClient.builder()
                    .clientConnector(new ReactorClientHttpConnector(httpClient))
                    .build()
                    .method(httpMethod)
                    .uri(uri)
                    .headers(headers -> headers.putAll(httpHeaders))
                    .bodyValue(body)
                    .exchangeToMono(response -> {
                        if (response.statusCode().isError()) {
                            return response.bodyToMono(errorType).flatMap((errorBody) -> Mono.error(new WebClientException(errorBody.toString(), HttpStatus.BAD_REQUEST)));
                        } else {
                            return response.bodyToMono(responseType);
                        }
                    })
                    .block();

        } catch (WebClientResponseException ex) {
            log.info("Erro de Resposta: Status: {}, Body: {}", ex.getStatusCode(), ex.getResponseBodyAsString());
            throw new WebClientException(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
