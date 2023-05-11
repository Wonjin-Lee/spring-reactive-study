package io.wonjin.springreactivestudy.webflux.adapter;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;

public class AdapterTest {
    @Test
    void fromFluxToFlowableAndFromFlowableToFlux() {
        var flowable = Flowable.fromPublisher(Flux.just(2));
        Flux.from(flowable);
    }

    @Test
    void fromObservableToFluxAndFromFluxToObservable() {
        var observable = Observable.just(2);
        var flux = Flux.from(observable.toFlowable(BackpressureStrategy.BUFFER));
        Observable.fromPublisher(flux);
    }

    @Test
    void fromMonoToSingle() {
        var mono = Mono.just(2);
        Single.fromPublisher(mono);
    }

    @Test
    void fromSingleToMono() {
        var single = Single.just(2);
        Mono.from(single.toFlowable());
    }

    @Test
    void fromMonoToCompletableFuture() {
        var mono = Mono.just("hello").log().map(s -> s.toUpperCase());
        var future = mono.toFuture();
    }

    @Test
    void fromCompletableFutureToMono() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello");

        var mono = Mono.fromFuture(future);
        mono.log()
                .doOnNext(System.out::println)
                .subscribe();
    }
}
