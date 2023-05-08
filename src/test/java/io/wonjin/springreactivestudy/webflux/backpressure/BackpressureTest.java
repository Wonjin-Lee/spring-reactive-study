package io.wonjin.springreactivestudy.webflux.backpressure;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class BackpressureTest {
    @Test
    void checkBackpressureUsingStepVerifier() {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4, 5, 6, 7).log();

        StepVerifier.create(flux, 1)
                .expectNext(1)
                .thenRequest(2)
                .expectNextCount(2)
                .thenRequest(1)
                .expectNext(4)
                .thenRequest(3)
                .expectNextCount(3)
                .thenCancel()
                .verify();
    }
}
