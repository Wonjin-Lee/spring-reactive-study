package io.wonjin.springreactivestudy.webflux.verifier;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

public class StepVerifierTest {
    @Test
    void expectFooBarComplete() {
        Flux<String> flux = Flux.just("foo", "bar");

        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyComplete();
    }

    @Test
    void expectFooBarError() {
        Flux<String> flux = Flux.just("foo", "bar");

        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyError(RuntimeException.class);
    }

    @Test
    void expectObjectAssertion() {
        Flux<User> flux = Flux.just(User.of("swhite"), User.of("jpinkman"));

        StepVerifier.create(flux)
                .assertNext(user -> assertThat(user.getUsername()).isEqualTo("swhite"))
                .assertNext(user -> assertThat(user.getUsername()).isEqualTo("jpinkman"))
                .verifyComplete();
    }

    @Test
    void expect10Elements() {
        Flux<Long> take10 = Flux.interval(Duration.ofMillis(100))
                .take(10);

        StepVerifier.create(take10)
                .expectNextCount(10)
                .verifyComplete();
    }

    @Test
    void expect3600Elements() {
        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofMillis(1000))
                .take(3600))
                .thenAwait(Duration.ofHours(1))
                .expectNextCount(3600)
                .verifyComplete();
    }
}
