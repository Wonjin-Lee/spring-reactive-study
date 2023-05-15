package io.wonjin.springreactivestudy.webflux.etc;

import io.wonjin.springreactivestudy.webflux.domain.User;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class EtcOperationTest {
    @Test
    void userFluxFromStringFlux() {
        Flux<String> usernameFlux = Flux.just("Marco", "Bob", "Coy");
        Flux<String> firstnameFlux = Flux.just("A", "B", "C");
        Flux<String> lastnameFlux = Flux.just("G", "H", "I");

        Flux.zip(usernameFlux, firstnameFlux, lastnameFlux)
                .map(tuple -> new User(tuple.getT1(), tuple.getT2(), tuple.getT3()))
                .log().doOnNext(user -> {
                    System.out.println(user.getUsername());
                    System.out.println(user.getLastName());
                    System.out.println(user.getFirstName());
                })
                .subscribe();
    }

    @Test
    void useFastestMono() {
        Mono.firstWithSignal(Mono.just(1), Mono.just(2))
                .log()
                .doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    void fluxCompletion() {
        Flux flux = Flux.just(1, 2, 3);
        flux.then().log().doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    void nullAwareUserToMono() {
        User user = null;
        Mono.justOrEmpty(user)
                .log()
                .doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    void emptyToKakao() {
        User user = null;
        Mono.justOrEmpty(user).defaultIfEmpty(User.of("Kakao"))
                .log().doOnNext(u -> System.out.println(u.getUsername())).subscribe();
    }
}
