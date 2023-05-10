package io.wonjin.springreactivestudy.webflux.error;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.junit.jupiter.api.Test;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorHandleTest {
    @Test
    void whenErrorMonoThenReturnNumberTwo() {
        Mono<Object> mono = Mono.error(new RuntimeException());
        mono.log().onErrorReturn(2).doOnNext(System.out::println).subscribe();
    }

    @Test
    void whenErrorMonoThenReturnPublisher() {
        Mono<Object> mono = Mono.error(new RuntimeException());
        mono.log().onErrorResume(e -> Mono.just(2)).doOnNext(System.out::println).subscribe();
    }

    @Test
    void whenErrorFluxThenReturnSaulJesseUserFlux() {
        Flux<User> flux = Flux.error(new RuntimeException());
        flux.log().onErrorResume(e -> Flux.just(User.of("SAUL"), User.of("JESSE"))).doOnNext(user -> {
            System.out.println(user.getUsername());
        }).subscribe();
    }

    @Test
    void wrapCheckedExceptionUsingPropagateUtil() {
        Mono.just("hello")
                .log()
                .map(s -> {
                    try {
                        return Integer.parseInt(s);
                    } catch (Exception e) {
                        throw Exceptions.propagate(e);
                    }
                }).onErrorReturn(200)
                .doOnNext(System.out::println)
                .subscribe();
    }
}
