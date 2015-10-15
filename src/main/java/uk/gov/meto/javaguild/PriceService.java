package uk.gov.meto.javaguild;

import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class PriceService {

    private final int maxDelay;
    private final int minDelay;
    private final Random random = new Random();

    public PriceService() {
        maxDelay = 500;
        minDelay = 200;
    }

    private int delayMillis() {
        return (int) (Math.random() * (maxDelay - minDelay) + minDelay);
    }

    public Observable<Integer> prices(Observable<Long> trigger) {
        return trigger.flatMap(ignored ->
                Observable.just(random.nextInt(1000)))
                .delay(delayMillis(), TimeUnit.MILLISECONDS);
    }

}
