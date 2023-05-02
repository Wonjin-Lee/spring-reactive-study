package io.wonjin.springreactivestudy.webflux.publisher;

import org.junit.jupiter.api.Test;

class FluxPracTest {
    @Test
    void run() throws InterruptedException {
        FluxPrac fluxPrac = new FluxPrac();
        fluxPrac.flux().subscribe(System.out::println);
    }
}