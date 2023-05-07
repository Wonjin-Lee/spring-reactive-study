package io.wonjin.springreactivestudy.webflux.transform;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

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

    Mono<User> asyncCapitalizeUser(User user) {
        return Mono.just(new User(user.getUsername().toUpperCase(), user.getFirstName().toUpperCase(), user.getLastName().toUpperCase()));
    }
}
