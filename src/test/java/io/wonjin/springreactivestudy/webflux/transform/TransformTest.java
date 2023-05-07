package io.wonjin.springreactivestudy.webflux.transform;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static reactor.core.scheduler.Schedulers.parallel;

public class TransformTest {
    @Test
    void capitalizeOne() {
        User user = new User("BigBossBaby", "Baby", "BigBoss");

        Mono<User> mono = Mono.just(user);

        Mono<User> transformMono = mono.map(u -> new User(u.getUsername().toUpperCase(), u.getFirstName().toUpperCase(), u.getLastName().toUpperCase()));

        StepVerifier.create(transformMono)
                .assertNext(u -> {
                    assertThat(u.getUsername()).isEqualTo("BIGBOSSBABY");
                    assertThat(u.getFirstName()).isEqualTo("BABY");
                    assertThat(u.getLastName()).isEqualTo("BIGBOSS");
                })
                .verifyComplete();
    }

    @Test
    void asyncCapitalizeOne() {
        Mono<User> mono = Mono.just(new User("BigBossBaby", "Baby", "BigBoss"));

        Mono<User> transformMono = mono.flatMap(this::asyncCapitalizeUser);

        StepVerifier.create(transformMono)
                .assertNext(u -> {
                    assertThat(u.getUsername()).isEqualTo("BIGBOSSBABY");
                    assertThat(u.getFirstName()).isEqualTo("BABY");
                    assertThat(u.getLastName()).isEqualTo("BIGBOSS");
                })
                .verifyComplete();
    }

    @Test
    void asyncTest() {
        Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i")
                .window(3)
                .flatMap(flux -> flux.map(this::toUpperCase).subscribeOn(parallel()))
                .doOnNext(System.out::println)
                .blockLast();
    }

    Mono<User> asyncCapitalizeUser(User user) {
        return Mono.just(new User(user.getUsername().toUpperCase(), user.getFirstName().toUpperCase(), user.getLastName().toUpperCase()));
    }

    private List<String> toUpperCase(String s) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return List.of(s.toUpperCase(), Thread.currentThread().getName());
    }
}
