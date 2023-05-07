package io.wonjin.springreactivestudy.webflux.merge;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class MergeTest {
    @Test
    void mergeFluxWithInterleave() {
        Flux<Long> flux1 = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> flux2 = Flux.just(100l, 101l, 102l);

        flux1.mergeWith(flux2)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    void mergeFluxWithNoInterleave() {
        Flux<Long> flux1 = Flux.interval(Duration.ofMillis(100)).take(10);
        Flux<Long> flux2 = Flux.just(100l, 101l, 102l);

        flux1.concatWith(flux2)
                .doOnNext(System.out::println)
                .blockLast();
    }

    @Test
    void mergeMonoWithNoInterleave() {
        Mono<Integer> mono1 = Mono.just(1);
        Mono<Integer> mono2 = Mono.just(2);

        Flux.concat(mono1, mono2)
                .doOnNext(System.out::println)
                .blockLast();
    }
}
