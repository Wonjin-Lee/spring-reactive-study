package io.wonjin.springreactivestudy.webflux.backpressure;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
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

    @Test
    void request10DataTest() {
        Flux.range(1, 100)
                .log()
                .doOnNext(System.out::println)
                .subscribe(new Subscriber<Integer>() {
                    private Subscription subscription;
                    private int count;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        this.subscription.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        count++;
                        if (count % 10 == 0) {
                            this.subscription.request(10);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
