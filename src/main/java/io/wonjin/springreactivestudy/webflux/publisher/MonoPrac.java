package io.wonjin.springreactivestudy.webflux.publisher;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class MonoPrac {
    void delayMono() {
        Mono.just(1L)
            .map(item -> item * 2)
            .or(Mono.delay(Duration.ofMillis(100)))
            .subscribe(System.out::println);
    }

    Mono<String> emptyMono() {
        return Mono.empty();
    }

    Mono<String> fooMono() {
        return Mono.just("foo");
    }

    Mono<String> errorMono() {
        return Mono.error(new IllegalStateException());
    }
}
