package io.wonjin.springreactivestudy.webflux.publisher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MonoPracTest {
    @Test
    void run() {
        MonoPrac monoPrac = new MonoPrac();
        monoPrac.delayMono();
    }
}