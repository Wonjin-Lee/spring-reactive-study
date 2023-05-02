package io.wonjin.springreactivestudy.webflux.publisher;

import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Consumer;

public class FluxPrac {
    Flux<String> emptyFlux() {
        return Flux.empty();
    }

    Flux<String> fooBarFluxFromValues() {
        return Flux.just("foo", "bar");
    }

    Flux<String> fooBarFluxFromList() {
        return Flux.fromIterable(Arrays.asList("foo", "bar"));
    }

    Flux<String> errorFlux() {
        return Flux.error(new IllegalStateException());
    }

    Flux<Long> counter() {
        return Flux.interval(Duration.ofMillis(100))
                .take(10);
    }

    Flux<String> flux() throws InterruptedException {
        return Flux.fromIterable(Arrays.asList("Seoul", "Busan", "Daegu", "Changwon"))
            .doOnNext(item -> System.out.println("City Name : " + item))
            .map(item -> item += "Station")
            .take(2);
    }
}
