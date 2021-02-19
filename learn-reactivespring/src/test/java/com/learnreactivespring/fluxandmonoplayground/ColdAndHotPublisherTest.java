package com.learnreactivespring.fluxandmonoplayground;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

//9
public class ColdAndHotPublisherTest {

    @Test
    public void coldPublisherTest() throws InterruptedException { //cada sub consome tudo de novo

        Flux<String> stringFlux = Flux.just("A","B","C","D","E","F")
                .delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(s -> System.out.println("Subscriber 1 : " + s)); //emits the value from beginning

        Thread.sleep(2000); //vai cair aqui depois do subscriber 1 terminar

        stringFlux.subscribe(s -> System.out.println("Subscriber 2 : " + s));//emits the value from beginning


        Thread.sleep(40000);
    }

    @Test
    public void hotPublisherTest() throws InterruptedException { //os subscribers vao dividir os valores

        Flux<String> stringFlux = Flux.just("A","B","C","D","E","F")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();
        connectableFlux.subscribe(s -> System.out.println("Subscriber 1 : " + s));
        Thread.sleep(3000);

        connectableFlux.subscribe(s -> System.out.println("Subscriber 2 : " + s)); // does not emit the values from beginning
        Thread.sleep(4000);

    }
}
