package io.wonjin.springreactivestudy.webflux.block;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class BlockTest {
    @Test
    void monoToValue() {
        User user = User.of("Tube");
        Mono<User> mono = Mono.just(user);
        User monoToUser = mono.block();

        System.out.println(monoToUser.getUsername());
    }

    @Test
    void fluxToValues() {
        Flux<User> flux = Flux.just(User.of("Muzi"), User.of("Niniz"));
        Iterable<User> iterable = flux.toIterable();

        iterable.forEach(user -> {
            System.out.println(user.getUsername());
        });
    }

    @Test
    void blockingRepositoryToFlux() throws InterruptedException {
        UserRepository userRepository = new UserRepository();
        Flux.defer(() -> Flux.fromIterable(userRepository.findAll()))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(System.out::println)
                .subscribe();

        Thread.sleep(5000L);
    }

    public static class UserRepository {
        public List<User> findAll() {
            return List.of(User.of("Iron Man"), User.of("Ant Man"));
        }
    }
}
